package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.AccountsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterListDto {
    private String accountId;
    private String accountName;
    private String client;

    public static FilterListDto of(AccountsEntity entityClients) {
        return new FilterListDto(
                entityClients.getAccountId(),
                entityClients.getAccountName(),
                entityClients.getClient()
        );
    }
}
