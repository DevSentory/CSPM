package kyobo.cspm.common.dto;

import kyobo.cspm.compliance.entity.ComplianceEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplianceXlsxDto implements BaseXlsx {
    private String scanTime;
    private String rid;
    private String vulnerabilityStatus;
    private String accountName;
    private String accountId;
    private String resourceId;
    private String client;
    private String policyType;
    private String policySeverity;
    private String policyCompliance;
    private String policyTitle;
    private String policyDescription;
    private String policyResponse;

    public static ComplianceXlsxDto of(ComplianceEntity complianceEntity){
        return new ComplianceXlsxDto(
                complianceEntity.getScanTime(),
                complianceEntity.getRid(),
                complianceEntity.getVulnerabilityStatus(),//취약점상태
                complianceEntity.getAccountName(),
                complianceEntity.getAccountId(),
                complianceEntity.getResourceId(),
                complianceEntity.getClient(),
                complianceEntity.getPolicyType(),  //분류
                complianceEntity.getPolicySeverity(), //등급
                complianceEntity.getPolicyCompliance(),
                complianceEntity.getPolicyTitle(),
                complianceEntity.getPolicyDescription(),
                complianceEntity.getPolicyResponse()
        );
    }
}