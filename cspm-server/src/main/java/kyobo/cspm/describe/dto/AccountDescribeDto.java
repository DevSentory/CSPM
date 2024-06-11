package kyobo.cspm.describe.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import kyobo.cspm.describe.entity.AccountsEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "고객사 전체 리스트 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDescribeDto {
    @Schema(description = "고객사", nullable = false, example = "test")
    private String client;

    @Schema(description = "accountName", nullable = false, example = "-")
    private String accountName;

    @Schema(description = "accountId", nullable = false, example = "339712736815")
    private String accountId;

    @Schema(description = "registerTime", nullable = false, example = "2024-05-14 13:53")
    private String registerTime;

    @Schema(description = "lastUpdateDescribeTime", nullable = false, example = "2024-05-14 13:53")
    private String lastUpdateDescribeTime;

    @Schema(description = "describeResult", nullable = false, example = "SUCCESS")
    private String describeResult;


    public static AccountDescribeDto of(AccountsEntity accountsEntity) {
        return new AccountDescribeDto(
                accountsEntity.getClient(),
                accountsEntity.getAccountName(),
                accountsEntity.getAccountId(),
                accountsEntity.getRegisterTime(),
                accountsEntity.getLastUpdateDescribeTime(),
                accountsEntity.getDescribeResult()
        );
    }
}
