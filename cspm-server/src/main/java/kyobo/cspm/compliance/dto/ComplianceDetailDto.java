package kyobo.cspm.compliance.dto;

import kyobo.cspm.compliance.entity.ComplianceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComplianceDetailDto {

    private String rid;
    private String scanTime;
    private String policyCompliance;
    private String policyType;  // 취약점 분류
    private String policySeverity; //취약점 등급
    private String policyTitle; // 취약점 이름
    private String accountName;
    private String accountId;
    private String service;
    private String resourceId;
    private String policyDescription;
    private String policyResponse;

    public static ComplianceDetailDto of(ComplianceEntity compliance){
        return new ComplianceDetailDto(
                compliance.getRid(),
                compliance.getScanTime(),
                compliance.getPolicyCompliance(),
                compliance.getPolicyType(),
                compliance.getPolicySeverity(),
                compliance.getPolicyTitle(),
                compliance.getAccountName(),
                compliance.getAccountId(),
                compliance.getService(),
                compliance.getResourceId(),
                compliance.getPolicyDescription(),
                compliance.getPolicyResponse()
        );
    }
}
