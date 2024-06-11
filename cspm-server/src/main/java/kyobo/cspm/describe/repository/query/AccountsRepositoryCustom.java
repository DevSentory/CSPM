package kyobo.cspm.describe.repository.query;

import kyobo.cspm.describe.dto.ErrorLogDto;
import kyobo.cspm.describe.dto.ScanConfigFilterDto;
import kyobo.cspm.describe.entity.AccountsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepositoryCustom {

    List<AccountsEntity> findAllQueryDescription(ScanConfigFilterDto scanConfigFilterDto);

    Optional<ErrorLogDto> findErrorLogList(String accountId, String scamTime, String accountName);
}
