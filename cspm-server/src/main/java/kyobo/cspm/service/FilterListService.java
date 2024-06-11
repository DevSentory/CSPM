package kyobo.cspm.service;
import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.describe.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilterListService {

    private final AccountsRepository accountsRepository;

    public List<String> getClientList(){
        List<AccountsEntity> entityClientsList = accountsRepository.findAll();

        List<String> clientList = entityClientsList.stream()
                .map(AccountsEntity::getClient)
                .distinct() // 중복 제거
                .collect(Collectors.toList());
       return clientList;
    }

    public List<String> getAccountName(){
        List<AccountsEntity> entityClientsList = accountsRepository.findAll();
        List<String> accountNameList = entityClientsList.stream()
                .map(AccountsEntity::getAccountName)
                .distinct() // 중복 제거
                .collect(Collectors.toList());
        return accountNameList;
    }

    public List<String> getAccountId(){
        List<AccountsEntity> entityClientsList = accountsRepository.findAll();

        List<String> acconutIdList = entityClientsList.stream()
                .map(AccountsEntity::getAccountId)
                .distinct() // 중복 제거
                .collect(Collectors.toList());
        return acconutIdList;
    }
}
