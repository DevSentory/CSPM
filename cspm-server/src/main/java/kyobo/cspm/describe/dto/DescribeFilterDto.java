package kyobo.cspm.describe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescribeFilterDto {
    @Schema(description = "client", nullable = false, example = "test")
    private String client;

    @Schema(description = "accountName", nullable = false, example = "test")
    private String accountName;

    @Schema(description = "accountId", nullable = false, example = "339712736815")
    private String accountId;

    @Schema(description = "service", nullable = false, example = "VPC")
    private String service;

    @Schema(description = "fromDate", nullable = false, example = " ")
    private String fromDate;

    @Schema(description = "toDate", nullable = false, example = " ")
    private String toDate;

    @Schema(description = "searchDate", nullable = false, example = " ")
    private String searchDate;

    @Schema(description = "resource", nullable = false, example = "vpc-")
    private String resource;
}