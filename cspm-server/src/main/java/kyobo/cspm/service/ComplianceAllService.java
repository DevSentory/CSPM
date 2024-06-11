package kyobo.cspm.service;

import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.compliance.dto.ComplianceDescribeDto;
import kyobo.cspm.compliance.dto.ComplianceRequestDto;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.repository.ComplianceRepository;
import kyobo.cspm.describe.policyDto.ComplianceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ComplianceAllService {

    private final ComplianceRepository complianceRepository;

    /**
     * Compliance 테이블의 전체 데이터 반환
     * Compliance Data를 꺼내 ComplianceDescribeDtoList에 담아서 반환
     * isEmpty -> Compliance 테이블에 값이 존재하지 않을 때 EMPTY_RESOURCE를 반환
     *
     * @return -> ComplianceDescribeDtoList
     */
    @Transactional("complianceTransactionManager")
    public BaseResponse<List<ComplianceDescribeDto>> getAllCompliance() {
        List<ComplianceDescribeDto> ComplianceDescribeDtoList = complianceRepository.findAll().stream()
                .map(ComplianceDescribeDto::of)
                .collect(Collectors.toList());

        if (ComplianceDescribeDtoList.isEmpty()) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE); // 빈 리스트 반환
        }
        return BaseResponse.success(ComplianceDescribeDtoList);
    }

    @Transactional("complianceTransactionManager")
    public BaseResponse<ConcurrentHashMap<String, List<?>>> complianceFilterList(ComplianceRequestDto complianceRequestDto) {

        List<ComplianceEntity> complianceEntityList = complianceRepository.findQueryCompliance(complianceRequestDto);
        if (complianceEntityList.isEmpty()) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }

        List<String> clientList = complianceEntityList.stream()
                .map(ComplianceEntity::getClient)
                .distinct()
                .toList();

        List<String> accountName = complianceEntityList.stream()
                .map(ComplianceEntity::getAccountName)
                .distinct()
                .toList();

        List<String> accountId = complianceEntityList.stream()
                .map(ComplianceEntity::getAccountId)
                .distinct()
                .toList();

        List<ComplianceDescribeDto> complianceDescribeDtoList = complianceEntityList.stream().
                map(ComplianceDescribeDto::of).toList();

        ConcurrentHashMap<String, List<?>> complianceFilterHashMap = new ConcurrentHashMap<>();
        complianceFilterHashMap.put("client", clientList);
        complianceFilterHashMap.put("accountName", accountName);
        complianceFilterHashMap.put("accountId", accountId);
        complianceFilterHashMap.put("data", complianceDescribeDtoList);

        return BaseResponse.success(complianceFilterHashMap);
    }

    @Transactional("complianceTransactionManager")
    public void complianceSave(List<ComplianceDto> complianceDtoList, String accountId, String accountName, String newScanTime) {
        // 기존 ComplianceEntity 목록을 Map으로 저장
        Map<String, ComplianceEntity> complianceEntityMap =
                complianceRepository.findByVulnerabilityStatusNotAndAccountIdAndAccountName("Close", accountId, accountName)
                        .stream()
                        .collect(Collectors.toMap(
                                entity -> entity.getResourceId() + "_" + entity.getPolicyTitle() + "_" + entity.getAccountId() + "_" + entity.getAccountName(),
                                Function.identity()
                        ));

        // complianceDtoList를 기반으로 새로운 ComplianceEntity 생성 및 업데이트
        List<ComplianceEntity> entitiesToSave = complianceDtoList.stream()
                .map(dto -> {
                    String key = dto.getResourceId() + "_" + dto.getPolicyTitle() + "_" + dto.getAccountId() + "_" + dto.getAccountName();
                    if (complianceEntityMap.containsKey(key)) {
                        ComplianceEntity entity = complianceEntityMap.get(key);
                        entity.setScanTime(dto.getScanTime());
                        complianceEntityMap.remove(key);
                        return entity;
                    } else {
                        ComplianceEntity entity = new ComplianceEntity();
                        entity.setResourceId(dto.getResourceId());
                        entity.setRid(dto.getRid());
                        entity.setScanTime(dto.getScanTime());
                        entity.setVulnerabilityStatus("Open");
                        entity.setPolicyType(dto.getPolicyType());
                        entity.setAccountName(dto.getAccountName());
                        entity.setClient(dto.getClient());
                        entity.setPolicySeverity(dto.getPolicySeverity());
                        entity.setPolicyCompliance(dto.getPolicyCompliance());
                        entity.setPolicyTitle(dto.getPolicyTitle());
                        entity.setAccountId(dto.getAccountId());
                        entity.setService(dto.getService());
                        entity.setPolicyDescription(dto.getPolicyDescription());
                        entity.setPolicyResponse(dto.getPolicyResponse());
                        return entity;
                    }
                })
                .collect(Collectors.toList());

        // 기존 ComplianceEntity 중 "Open" 또는 "Exception" 상태이며 newScanTime과 다른 경우 "Close" 상태로 변경

        entitiesToSave.addAll(complianceEntityMap.values().stream()
                .filter(entity -> ("Open".equals(entity.getVulnerabilityStatus()) || "Exception".equals(entity.getVulnerabilityStatus()))
                        && !newScanTime.equals(entity.getScanTime())
                        && accountId.contains(entity.getAccountId())
                && accountName.contains(entity.getAccountName()))
                .peek(entity -> entity.setVulnerabilityStatus("Close"))
                .toList());

        // 모든 변경 사항 저장
        complianceRepository.saveAll(entitiesToSave);
    }
}