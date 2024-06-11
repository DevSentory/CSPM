package kyobo.cspm.service;

import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.compliance.dto.ComplianceDetailDto;
import kyobo.cspm.compliance.dto.ComplianceExceptionDto;
import kyobo.cspm.compliance.dto.ComplianceExceptionReqDto;
import kyobo.cspm.compliance.dto.ComplianceExceptionResDto;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import kyobo.cspm.compliance.repository.ComplianceExceptionRepository;
import kyobo.cspm.compliance.repository.ComplianceRepository;
import kyobo.cspm.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional("complianceTransactionManager")
@RequiredArgsConstructor
public class ComplianceDetailService {

    private final ComplianceRepository complianceRepository;
    private final ComplianceExceptionRepository complianceExceptionRepository;


    /**
     * Compliance 테이블의 상세 데이터 반환
     * Compliance Data를 꺼내 ComplianceDetailDto에 담아서 반환
     * isEmpty -> Compliance 테이블에 값이 존재하지 않을 때 EMPTY_RESOURCE를 반환
     * @return ->  BaseResponse<ComplianceDetailDto>
     */
    public BaseResponse<ComplianceDetailDto> findDetailCompliance(String resourceId, String scanTime, String accountId, String accountName) {
        Optional<ComplianceEntity> complianceEntity = complianceRepository.findByDetailResourceId(resourceId, scanTime, accountId, accountName);
        if (complianceEntity.isEmpty()) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }
        ComplianceDetailDto complianceDetailDto = ComplianceDetailDto.of(complianceEntity.get());
        return BaseResponse.success(complianceDetailDto);
    }

    /**
     * Compliance complianceExceptionDto
     * isEmpty -> Compliance 테이블에 값이 존재하지 않을 때 EMPTY_RESOURCE를 반환
     * @return ->  BaseResponse<"성공">
     * 수정 완료: 5/24 - 이루다
     */
    public BaseResponse<String> createException(ComplianceExceptionReqDto complianceExceptionReqDto) {

        String resourceId = complianceExceptionReqDto.getResourceId();
        String policyTitle = complianceExceptionReqDto.getPolicyTitle();
        String accountId = complianceExceptionReqDto.getAccountId();
        String accountName = complianceExceptionReqDto.getAccountName();
        String exceptionContent = complianceExceptionReqDto.getExceptionContent();
        String selectedOption = complianceExceptionReqDto.getSelectedOption();

        ComplianceExceptionEntity complianceExceptionEntity = new ComplianceExceptionEntity();
        complianceExceptionEntity.setExceptionTime(StringUtils.localTimeToFormat("yyy-MM-dd HH:mm"));
        complianceExceptionEntity.setExceptionContent(exceptionContent);
        complianceExceptionEntity.setExceptionHandler("admin");
        complianceExceptionEntity.setPolicyTitle(policyTitle);
        complianceExceptionEntity.setResourceId(resourceId);
        complianceExceptionEntity.setAccountId(accountId);
        complianceExceptionEntity.setAccountName(accountName);

        Optional<ComplianceEntity> complianceEntityOptional = complianceRepository.findByExceptionSoruce(resourceId, policyTitle, accountId, accountName);

        if (complianceEntityOptional.isPresent()) {
            ComplianceEntity complianceEntity = complianceEntityOptional.get();
            complianceEntity.setVulnerabilityStatus(selectedOption);
            complianceRepository.save(complianceEntity);
        } else {
            // Optional이 비어있는 경우에 대한 처리
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }

        complianceExceptionRepository.save(complianceExceptionEntity);

        return BaseResponse.success("성공");
    }


    /**
     * ComplianceExceptionEntity의 전체 데이터 반환
     * ComplianceExceptionEntity를 꺼내 ComplianceExceptionResDto에 담아서 반환
     * isEmpty -> ComplianceExceptionEntity 테이블에 값이 존재하지 않을 때 EMPTY_RESOURCE를 반환
     * @return ->  BaseResponse<"ComplianceExceptionResDto">
     */
    public BaseResponse<ComplianceExceptionDto> getException(String resourceId, String policyTitle, String accountId, String accountName) {

        Optional<ComplianceExceptionEntity> optionalComplianceExceptionEntity = complianceExceptionRepository.findByExceptionId(resourceId, policyTitle, accountId, accountName);

        if(optionalComplianceExceptionEntity.isEmpty()){
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }

        ComplianceExceptionDto complianceExceptionDto = ComplianceExceptionDto.of(optionalComplianceExceptionEntity.get());
        return BaseResponse.success(complianceExceptionDto);
    }
}
