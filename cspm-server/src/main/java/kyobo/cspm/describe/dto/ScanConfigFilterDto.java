package kyobo.cspm.describe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "스캔 설정 페이지 전체 리스트 및 필터 값 DTO")
public class ScanConfigFilterDto {

    @Schema(description = "client", nullable = false, example = "test")
    private String client;

    @Schema(description = "accountName", nullable = false, example = "test")
    private String accountName;

    @Schema(description = "searchData", nullable = true, example = " ")
    private String searchData;
}
