package kyobo.cspm.describe.policyDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ComplianceDto {

    private String resourceId;
    private String scanTime;
    private String service;

    //policy 영역
    private String policyType;
    private String policyTitle;
    private String policySeverity;
    private String policyDescription;
    private String policyResponse;
    private String policyCompliance;


    // accounts 영역
    private String rid;
    private String accountName;
    private String client;
    private String accountId;

    public ComplianceDto(String resourceId, String scanTime, String serviceGroup, String policyType, String policyTitle,
                         String policySeverity, String policyDescription, String policyResponse, String policyCompliance, String code, String accountName, String client, String accountId) {

        this.resourceId = resourceId;
        this.scanTime = scanTime;
        this.service = serviceGroup;
        this.policyType = policyType;
        this.policyTitle = policyTitle;
        this.policySeverity = policySeverity;
        this.policyDescription = policyDescription;
        this.policyResponse = policyResponse;
        this.policyCompliance = policyCompliance;
        this.rid = code;
        this.accountName = accountName;
        this.client = client;
        this.accountId = accountId;

    }
}
