package kyobo.cspm.describe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kyobo.cspm.describe.entity.AccountsEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "고객사 생성, 수정 DTO")
public class AccountDto {
    @Schema(description = "고객사", nullable = false, example = "test")
    private String client;

    @Schema(description = "CODE", nullable = false, example = "TEST")
    private String code;

    @Schema(description = "accountName", nullable = false, example = "-")
    private String accountName;

    @Schema(description = "accessKey", nullable = false, example = "-")
    private String accessKey;

    @Schema(description = "secretKey", nullable = false, example = "-")
    private String secretKey;

    @Schema(description = "region", nullable = false, example = "ap-northeast-2")
    private String region;

    @Schema(description = "comment", nullable = false, example = "test")
    private String comment;

    @Schema(description = "accountId", nullable = false, example = "339712736815")
    private String accountId;

    public static AccountDto of(AccountsEntity accountsEntity) {
        return AccountDto.builder()
                .client(accountsEntity.getClient())
                .code(accountsEntity.getCode())
                .region(accountsEntity.getRegion())
                .accessKey(accountsEntity.getAccessKey())
                .secretKey(accountsEntity.getSecretKey())
                .accountId(accountsEntity.getAccountId())
                .accountName(accountsEntity.getAccountName())
                .comment(accountsEntity.getComment())
                .build();
    }
}