package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.describe.repository.query.AccountsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsEntity, Long>, AccountsRepositoryCustom {

    AccountsEntity findByAccountId(@Param("accountId") String accountId);

    AccountsEntity findByAccountName(@Param("accountName") String accountName);

    AccountsEntity findByAccountIdAndAccountName(String accountId, String accountName);

    AccountsEntity findByAccountIdAndAccountNameAndClient(String accountId, String accountName, String client);

    // AccountsEntity 테이블에서 code 필드를 찾는데 LIKE 필드에 입력된 값을 기준으로 찾아 내림차순으로 정렬한다.
    @Query("SELECT a.code FROM AccountsEntity a WHERE a.code LIKE :code% ORDER BY a.code DESC")
    List<String> findTopByCodeOrder(@Param("code") String code);

    // 다수의 accoundId를 가진 고객사를 삭제할 때 사용할 수 있는 쿼리문 (선택사항)
    @Query("DELETE FROM AccountsEntity a WHERE a.accountId IN :accountIds")
    void deleteByAccountIds(@Param("accountIds") List<String> accountIds);
}