package kyobo.cspm.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "취약점 페이지 필터에 값 받는 DTO")
public class ComplianceRequestDto {

    @Schema(description = "client", nullable = true, example = "test")
    private String client;

    @Schema(description = "accountName", nullable = true, example = "test")
    private String accountName;

    @Schema(description = "accountId", nullable = true, example = "339712736815")
    private String accountId;

    @Schema(description = "searchData", nullable = true, example = " ")
    private String searchData;

    @Schema(description = "fromDate", nullable = true, example = " ")
    private String fromDate;

    @Schema(description = "toDate", nullable = true, example = " ")
    private String toDate;

    @Schema(description = "policySeverity", nullable = true, example = " ")
    private String policySeverity;

    @Schema(description = "VulnerabilityStatus", nullable = true, example = " ")
    private String vulnerabilityStatus;

}
