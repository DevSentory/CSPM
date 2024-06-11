package kyobo.cspm.describe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DescribeAccount {

    @Schema(description = "client", nullable = false, example = "test")
    private String client;

    @Schema(description = "accountId", nullable = false, example = "339712736815")
    private String accountId;

    @Schema(description = "accountName", nullable = false, example = "test")
    private String accountName;
}