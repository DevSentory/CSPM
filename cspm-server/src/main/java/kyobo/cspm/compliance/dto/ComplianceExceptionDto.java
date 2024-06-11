package kyobo.cspm.compliance.dto;

import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplianceExceptionDto {
    private Long id;
    private String exceptionTime;
    private String exceptionContent;
    private String exceptionHandler;
    private String policyTitle;
    private String resourceId;
    private String accountId;
    private String accountName;

    public static ComplianceExceptionDto of(ComplianceExceptionEntity complianceExceptionEntity){
        return new ComplianceExceptionDto(
                complianceExceptionEntity.getId(),
                complianceExceptionEntity.getExceptionTime(),
                complianceExceptionEntity.getExceptionContent(),
                complianceExceptionEntity.getExceptionHandler(),
                complianceExceptionEntity.getPolicyTitle(),
                complianceExceptionEntity.getResourceId(),
                complianceExceptionEntity.getAccountId(),
                complianceExceptionEntity.getAccountName()
        );
    }

}
