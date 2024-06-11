package kyobo.cspm.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "취약점 페이지 리스트 반환 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplianceDescribeDto {

    @Schema(description = "스캔 시간", nullable = false, example = "2024-05-20 18:00")
    private String scanTime;

    @Schema(description = "rid", nullable = false, example = "KYBO-00001")
    private String rid;

    @Schema(description = "취약점 상태", nullable = false, example = "Open")
    private String vulnerabilityStatus;

    @Schema(description = "Account Name", nullable = true, example = "kybo")
    private String accountName;

    @Schema(description = "Account Id", nullable = false, example = "339712736815")
    private String accountId;

    @Schema(description = "resourceId", nullable = false, example = "eni-04fc0731bc8df29fa")
    private String resourceId;

    @Schema(description = "client", nullable = false, example = "kybo")
    private String client;

    @Schema(description = "취약 등급", nullable = true, example = "Low")
    private String policySeverity;

    @Schema(description = "취약점 이름", nullable = true, example = "미사용 ENI 점검")
    private String policyTitle;

    @Schema(description = "취약점", nullable = true, example = "ISO/IEC 27001:2013 A.12.6.1, CIS AWS Foundations Benchmark 2.8")
    private String policyCompliance;


    public static ComplianceDescribeDto of(ComplianceEntity complianceEntity) {
        return new ComplianceDescribeDto(
                complianceEntity.getScanTime(),
                complianceEntity.getRid(),
                complianceEntity.getVulnerabilityStatus(),
                complianceEntity.getAccountName(),
                complianceEntity.getAccountId(),
                complianceEntity.getResourceId(),
                complianceEntity.getClient(),
                complianceEntity.getPolicySeverity(),
                complianceEntity.getPolicyTitle(),
                complianceEntity.getPolicyCompliance()
        );
    }
}
