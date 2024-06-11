package kyobo.cspm.service;

import com.mysema.commons.lang.Pair;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.describe.dto.DescribeAccount;
import kyobo.cspm.describe.dto.DescribeDto;
import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.PolicyEntity;
import kyobo.cspm.describe.policyDto.ComplianceDto;
import kyobo.cspm.describe.policyDto.PolicyDto;
import kyobo.cspm.describe.repository.AccountsRepository;
import kyobo.cspm.describe.repository.DescribeRepository;
import kyobo.cspm.describe.repository.PolicyRepository;
import kyobo.cspm.service.aws.CredentialsManager;
import kyobo.cspm.service.components.DescribeSaveComponent;
import kyobo.cspm.service.descirbe_group.GroupHandler;
import kyobo.cspm.utils.AES256;
import kyobo.cspm.utils.Constants;
import kyobo.cspm.utils.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.regions.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
class ResourceResultData {
    String accountId;
    String accountName;
    Boolean isAllSuccess;
    List<DescribeEntity> describeEntityList;

    public static ResourceResultData of(String accountId, String accountName, Boolean isSuccess, List<DescribeEntity> describeEntityList) {
        return ResourceResultData
                .builder()
                .accountId(accountId)
                .accountName(accountName)
                .isAllSuccess(isSuccess)
                .describeEntityList(describeEntityList)
                .build();
    }
}

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final AES256 aes256Util;
    private final GroupHandler describeGroup;
    private final AccountService accountService;
    private final ComplianceAllService complianceAllService;
    private final AccountsRepository accountsRepository;
    private final DescribeRepository describeRepository;
    private final PolicyRepository policyRepository;
    private final EntityManager entityManager;
    private final CredentialsManager credentialsManager;
    private final DescribeSaveComponent describeSaveComponent;

    /**
     * [2024.05.09 작업 완료 - 동규]
     * [2024.05.17 수정 완료 - 승주]
     * - 수정 내역 : HashMap을 이용해 clientList, accountIdList, accountNameList, describeFilterDtoList를 반환합니다.
     * [2024.05.18 수정 완료 - 승근]
     * -수정 내역 : fromDate(), toDate()의 형식을 String으로 변환하고 조회에서 fromDate()만 있으면 fromDate으로 부터 나오도록 변경, toDate()또한 마찬가지
     * task : DescribeEntity 테이블 데이터 전체 조회
     */
    public BaseResponse<ConcurrentHashMap<String, List<?>>> getAwsDescribeResultList(DescribeFilterDto describeFilterDto) {
        List<DescribeEntity> describeEntityList = describeRepository.findAllQueryDescription(describeFilterDto);
        if (CollectionUtils.isEmpty(describeEntityList))
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);

        List<String> clientList = describeEntityList.stream()
                .map(DescribeEntity::getClient)
                .distinct()
                .collect(Collectors.toList());

        List<String> accountNameList = describeEntityList.stream()
                .map(DescribeEntity::getAccountName)
                .distinct()
                .collect(Collectors.toList());

        List<String> accountIdList = describeEntityList.stream()
                .map(DescribeEntity::getAccountId)
                .distinct()
                .collect(Collectors.toList());

        List<DescribeDto> describeDtoList = describeEntityList.stream()
                .map(DescribeDto::of)
                .distinct()
                .collect(Collectors.toList());

        ConcurrentHashMap<String, List<?>> resultHashMap = new ConcurrentHashMap<>();
        resultHashMap.put("client", clientList);
        resultHashMap.put("accountName", accountNameList);
        resultHashMap.put("accountId", accountIdList);
        resultHashMap.put("data", describeDtoList);

        return BaseResponse.success(resultHashMap);
    }

    // task : aws 자원 조회 비동기 처리, 갱신, 반환
    public BaseResponse<Void> startDescribe(List<DescribeAccount> describeAccountList) {
        List<ResourceResultData> describeResultDataList = new ArrayList<>();
        long startTime = System.nanoTime();

        for (DescribeAccount describeAccount : describeAccountList) {

            String accountId = describeAccount.getAccountId();
            String accountName = describeAccount.getAccountName();

            // Credentials
            AccountsEntity client = accountsRepository.findByAccountIdAndAccountName(accountId, accountName);
            if (client.getAccountId() == null)
                continue;
            String accessKey = aes256Util.decrypt(client.getAccessKey());
            String secretKey = aes256Util.decrypt(client.getSecretKey());
            credentialsManager.createCredentials(Region.of(client.getRegion()), accessKey, secretKey);

            // 현재 aws userName(=AccountName)
            // 2024.04.31 기획 롤백
            // AwsBasicCredentials credentials = credentialsManager.getCredentialsInfo().getCredentials();
            // curAccountName = accountService.getCurrAccountName(credentials, accountName);

            // =================================================================================================
            // ▶ CompletableFuture : 비동기 작업의 결과를 처리하고, 추가적인 작업을 수행할 수 있도록 도와주는 클래스
            // ▶ Supplier : 매개변수를 받지 않고 값을 반환하는 함수형 인터페이스, 지연된 값 계산에 유용하게 사용
            // ▶ Pair : 두 개의 객체를 하나의 객체로 묶어 관리하는 클래스
            // =================================================================================================



            // 비동기 자원 스캔
            CompletableFuture<Pair<Boolean, List<DescribeEntity>>> pairCompletableFuture =
                    describeGroup.serviceGroupDescribe(describeAccount);



            try {
                Pair<Boolean, List<DescribeEntity>> pair = pairCompletableFuture.get();
                Boolean isAllSuccess = pair.getFirst();
                List<DescribeEntity> describeEntityList = pair.getSecond();

                // 자원 스캔 결과가 전부 'Fail'인 경우
                if (!isAllSuccess && CollectionUtils.isEmpty(describeEntityList))
                    return updateAccountDescribeResultToFail(accountId, accountName);

                describeEntityList.forEach(describeEntity -> {
                    describeEntity.setClient(describeAccount.getClient());
                    describeEntity.setAccountId(accountId);
                    describeEntity.setAccountName(accountName);
                });
                describeResultDataList.add(
                        ResourceResultData.of(accountId, accountName, isAllSuccess, describeEntityList));
            } catch (InterruptedException | ExecutionException e) {
                e.fillInStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration / 1_000_000_000.0 + " seconds");

        String lastUpdateDescribeTime;
        List<Boolean> isAllSuccessList = new ArrayList<>();
        for (ResourceResultData resultData : describeResultDataList) {

            // 단일 자원 스캔 저장
            describeSaveComponent.saveAllAwsResources();
            // describeSaveComponent.initializeEntityConnections();

            // 전체 스캔 저장
            describeRepository.saveAll(resultData.describeEntityList);

            // 스캔 결과 데이터 갱신
            lastUpdateDescribeTime = StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm");
            updateAccountDescribeResult(resultData, lastUpdateDescribeTime);
            startCompliance(lastUpdateDescribeTime, resultData.getAccountId(), resultData.getAccountName());

            isAllSuccessList.add(resultData.isAllSuccess);
        }

        // 취약점 검사 시작
        return !isAllSuccessList.contains(false) ? BaseResponse.success() : BaseResponse.error(BaseResponseStatus.FAILURE_PARTIAL_SUCCESS);
    }

    // task : aws 자원 조회 결과 업데이트
    private void updateAccountDescribeResult(ResourceResultData resultData, String lastUpdateDescribeTime) {
        AccountsEntity accountsEntity = accountsRepository.findByAccountIdAndAccountName(resultData.accountId, resultData.accountName);
        if (null != accountsEntity) {
            String isSuccess = resultData.isAllSuccess ? Constants.SUCCESS : Constants.FAIL;
            accountsEntity.UpdateDescribeData(lastUpdateDescribeTime, isSuccess);
            accountsRepository.save(accountsEntity);
        }
    }

    // task : aws 자원 조회 결과 '실패' 업데이트
    private BaseResponse<Void> updateAccountDescribeResultToFail(String accountId, String accountName) {
        AccountsEntity accountsEntity = accountsRepository.findByAccountIdAndAccountName(accountId, accountName);
        if (null != accountsEntity) {
            accountsEntity.UpdateDescribeData(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"), Constants.FAIL);
            accountsRepository.save(accountsEntity);
        }

        return BaseResponse.error(BaseResponseStatus.EMPTY_RESOURCE);
    }

    // task : 취약점 검사 시작
    private void startCompliance(String lastUpdateDescribeTime, String accountId, String accountName) {
        List<ComplianceDto> complianceDtoList = findComplianceByPolicy(accountId, accountName);
        List<ComplianceDto> policyDtoList = complianceDtoList.stream()
                .filter(complianceDto -> accountId.equals(complianceDto.getAccountId()) && accountName.equals(complianceDto.getAccountName()))
                .collect(Collectors.toList());
        complianceAllService.complianceSave(policyDtoList, accountId, accountName, lastUpdateDescribeTime);
    }

    /**
     * [2024-05-22 : 승근 작성]
     * task : policyDB에서 PolicyPattern을 꺼내서 개별 자원들에대해서 취약점을 조회한 후 findPolicyCompliance 쿼리DSL를 사용해 complianceDto List로 반환
     */
    public List<ComplianceDto> findComplianceByPolicy(String accountId, String accountName) {
        List<PolicyEntity> policyEntities = policyRepository.findAll();
        List<PolicyDto> policyDtoList = policyEntities.stream()
                .map((policyEntity) -> {
                    String policyPattern = policyEntity.getPolicyPattern();
                    Long policyId = policyEntity.getId();
                    return queryPatternAndReturnValue(policyId, policyPattern);
                })
                .flatMap(List::stream)
                .toList();

        return describeRepository.findPolicyCompliance(policyDtoList, accountId, accountName);
    }

    // task : 취약점 패턴 탐지
    private List<PolicyDto> queryPatternAndReturnValue(Long policyId, String policyPattern) {
        // Native Query 실행
        Query query = entityManager.createNativeQuery(policyPattern);
        List<?> tempResults = query.getResultList();
        List<Object[]> results = tempResults.stream()
                .map(result -> (Object[]) result)
                .toList();

        // 결과를 PolicyDto로 변환
        return results.stream().map((result) -> {
            String resourceId = (String) result[0];
            String scanTime = (String) result[1];
            return new PolicyDto(resourceId, scanTime, policyId);
        }).toList();
    }
}