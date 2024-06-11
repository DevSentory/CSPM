package kyobo.cspm.describe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "error log 전체 반환 리스트 DTO")
public class ErrorLogDto {

    @Schema(description = "오류 코드", nullable = false, example = "FAIL_AUTH_FAILURE")
    private List<String> exceptionCodes;

    @Schema(description = "오류 메시지", nullable = true, example = "when calling the DescribeSubnets operation: AWS was not able to validate the provided access credentials")
    private List<String> exceptionMessages;

    @Schema(description = "오류 서비스", nullable = true, example = "VPC")
    private List<String> exceptionService;

    public ErrorLogDto() {
        this.exceptionCodes = new ArrayList<>();
        this.exceptionMessages = new ArrayList<>();
        this.exceptionService = new ArrayList<>();
    }

    public ErrorLogDto(List<String> exceptionCodes, List<String> exceptionService, List<String> exceptionMessages) {
        this.exceptionCodes = exceptionCodes;
        this.exceptionMessages = exceptionMessages;
        this.exceptionService = exceptionService;
    }

    // 추가된 생성자
    public ErrorLogDto(String exceptionCode, String exceptionMessage, String resource) {
        this.exceptionCodes = new ArrayList<>();
        this.exceptionMessages = new ArrayList<>();
        this.exceptionService = new ArrayList<>();
        this.exceptionCodes.add(exceptionCode);
        this.exceptionMessages.add(exceptionMessage);
        this.exceptionService.add(resource);
    }
}
