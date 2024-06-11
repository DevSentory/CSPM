package kyobo.cspm.common.dto;

import kyobo.cspm.describe.entity.AccountsEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountXlsxDto implements BaseXlsx {
    private String client;
    private String accountName;
    private String accountId;
    private String registerTime;
    private String lastUpdateDescribeTime;
    private String describeResultType;

    public static AccountXlsxDto of(AccountsEntity accountsEntity){
        return new AccountXlsxDto(
                accountsEntity.getClient(),
                accountsEntity.getAccountName(),
                accountsEntity.getAccountId(),
                accountsEntity.getRegisterTime(),
                accountsEntity.getLastUpdateDescribeTime(),
                accountsEntity.getDescribeResult()
        );
    }
}