package kyobo.cspm.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComplianceExceptionReqDto {
    @Schema(description = "resourceId", nullable = false, example = "sg-0139482984dae9310")
    private String resourceId;

    @Schema(description = "accountId", nullable = false, example = "339712736815")
    private String accountId;

    @Schema(description = "accountName", nullable = false, example = "test")
    private String accountName;

    @Schema(description = "policyTitle", nullable = false, example = "보안그룹 Inbound Any open(0.0.0.0) 점검\n")
    private String policyTitle;

    @Schema(description = "exceptionContent", nullable = false, example = " ")
    private String exceptionContent;

    @Schema(description = "selectedOption", nullable = false, example = "Exception")
    private String selectedOption;
}
