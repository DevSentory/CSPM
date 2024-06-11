package kyobo.cspm.service;

import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.describe.dto.*;
import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.describe.repository.AccountsRepository;
import kyobo.cspm.utils.AES256;
import kyobo.cspm.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AccessKeyMetadata;
import software.amazon.awssdk.services.iam.model.ListAccessKeysRequest;
import software.amazon.awssdk.services.iam.model.ListAccessKeysResponse;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountsRepository accountsRepository;
    private final AES256 aes256Util;

    // task : accessKey 값을 사용하여 accountId 반환
    public String getAwsAccountId(Map<String, String> awsInfo) {
        String region = awsInfo.get("region");
        String accessKeyId = awsInfo.get("access-key");
        String secretAccessKey = awsInfo.get("secret-access-key");

        // Access Key ID와 Secret Access Key로 인증 정보를 생성합니다.
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        try {
            // 'accessKey'와 'secretAccessKey'를 가지고 AWS에 접근하기 위한 인증 정보를 설정한다.
            StsClient stsClient = StsClient.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .region(Region.of(region))
                    .build();
            GetCallerIdentityResponse callerIdentity = stsClient.getCallerIdentity(GetCallerIdentityRequest.builder().build());

            // 'accessKey'와 'secretAccessKey'를 이용하여 인증 정보 제공자를 설정한다.
            // 2024.05.31 기획 변경으로 IAM이 아닌, 수동 입력으로 변경
            /*
            IamClient iamClient = IamClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).region(Region.AWS_GLOBAL).build();
            ListAccessKeysResponse listAccessKeysResponse = iamClient.listAccessKeys();
            List<AccessKeyMetadata> accessKeyMetadataList = listAccessKeysResponse.accessKeyMetadata();
            String accountName = CollectionUtils.isEmpty(accessKeyMetadataList) ? "" : accessKeyMetadataList.get(0).userName();
            */

            return String.format("""
                    {
                      "success":%b,
                      "id":"%s"
                    }""", true, callerIdentity.account());

        } catch (AwsServiceException | SdkClientException e) {
            return String.format("""
                    {
                      "success":%b,
                      "id":"",
                      "message":"%s"
                    }""", false, e.getMessage());
        }
    }

    // task : 고객사 정보를 'account' 테이블에 저장 후 반환
    public BaseResponse<AccountsEntity> accountSaveToReturn(AccountDto accountDto) {
        if (null != accountsRepository.findByAccountName(accountDto.getAccountName()))
            return BaseResponse.nonVoidError(BaseResponseStatus.ACCOUNT_NAME_DUPLICATED);

        String accessKeyEncrypt = aes256Util.encrypt(accountDto.getAccessKey());
        String secretKeyEncrypt = aes256Util.encrypt(accountDto.getSecretKey());
        accountDto.setAccessKey(accessKeyEncrypt);
        accountDto.setSecretKey(secretKeyEncrypt);

        // 등록
        AccountsEntity savedEntity = accountsRepository.save(new AccountsEntity(
                accountDto, StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm")));

        return BaseResponse.success(savedEntity);
    }

    // task : 고객사 전체 리스트 반환
    public BaseResponse<ConcurrentHashMap<String, List<?>>> accountAllList(ScanConfigFilterDto scanConfigFilterDto) {
        List<AccountsEntity> accountsEntityList = accountsRepository.findAllQueryDescription(scanConfigFilterDto);
        if (CollectionUtils.isEmpty(accountsEntityList)) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }

        // disticnt를 통해 각 list에 값을 넣을때 마다 중복을 제거해줌
        List<String> clientList = accountsEntityList.stream()
                .map(AccountsEntity::getClient)
                .distinct()
                .collect(Collectors.toList());

        List<String> accountNameList = accountsEntityList.stream()
                .map(AccountsEntity::getAccountName)
                .distinct()
                .collect(Collectors.toList());

        List<AccountDescribeDto> accountDtoList = accountsEntityList.stream()
                .map(AccountDescribeDto::of)
                .collect(Collectors.toList());

        ConcurrentHashMap<String, List<?>> accountDataMap = new ConcurrentHashMap<>();
        accountDataMap.put("client", clientList);
        accountDataMap.put("accountName", accountNameList);
        accountDataMap.put("data", accountDtoList);

        // '스캔 설정' 페이지에 반환할 데이터 생성
        return BaseResponse.success(accountDataMap);
    }

    // task : 고객사 업데이트
    public BaseResponse<Void> accountUpdate(String accountId, String accountName, AccountDto accountDto) {
        AccountsEntity updateTargetAccount = accountsRepository.findByAccountIdAndAccountName(accountId, accountName);
        // 암호화
        String accessKeyEncrypt = aes256Util.encrypt(accountDto.getAccessKey());
        String secretKeyEncrypt = aes256Util.encrypt(accountDto.getSecretKey());
        if (updateTargetAccount == null)
            return BaseResponse.error(BaseResponseStatus.EMPTY_RESOURCE);

        // 갱신
        updateTargetAccount.setClient(accountDto.getClient());
        updateTargetAccount.setCode(accountDto.getCode().toUpperCase());
        updateTargetAccount.setAccountName(accountDto.getAccountName());
        updateTargetAccount.setAccessKey(accessKeyEncrypt);
        updateTargetAccount.setSecretKey(secretKeyEncrypt);
        updateTargetAccount.setRegion(accountDto.getRegion());
        updateTargetAccount.setComment(accountDto.getComment());
        accountsRepository.save(updateTargetAccount);

        return BaseResponse.success();
    }

    // task : 고객사 삭제
    public BaseResponse<Void> accountDelete(List<DescribeAccount> describeAccountList) {
        for (DescribeAccount describeAccount : describeAccountList) {
            AccountsEntity entityClients = accountsRepository.findByAccountIdAndAccountNameAndClient(describeAccount.getAccountId(), describeAccount.getAccountName(), describeAccount.getClient());

            if (entityClients == null) {
                return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
            } else {
                accountsRepository.delete(entityClients);
            }
        }

        // 쿼리문을 사용한다면? (선택사항)
        // repositoryAccounts.deleteByAccountIds(accountIdList);

        return BaseResponse.success();
    }

    // task : 엡데이트할 고객사 반환
    public AccountDto accountGet(String accountId, String accountName) {
        AccountsEntity accountsEntity = accountsRepository.findByAccountIdAndAccountName(accountId, accountName);
        AccountDto accountDto = AccountDto.of(accountsEntity);
        String accessKeyEncrypt = aes256Util.decrypt(accountDto.getAccessKey());
        String secretKeyEncrypt = aes256Util.decrypt(accountDto.getSecretKey());
        accountDto.setAccessKey(accessKeyEncrypt);
        accountDto.setSecretKey(secretKeyEncrypt);
        return accountDto;
    }

    public ErrorLogDto accountGetErrorLogs(String accountId, String scanTime, String accountName) {
        Optional<ErrorLogDto> errorLogDtoList = accountsRepository.findErrorLogList(accountId, scanTime, accountName);
        return errorLogDtoList.orElse(new ErrorLogDto(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    }

    public String getCurrAccountName(AwsBasicCredentials credentials, String accountName) {
        IamClient iamClient = IamClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.AWS_GLOBAL)
                .build();
        ListAccessKeysResponse response = iamClient.listAccessKeys(ListAccessKeysRequest.builder().build());
        List<AccessKeyMetadata> accessKeyMetadataList = response.accessKeyMetadata();
        return CollectionUtils.isEmpty(accessKeyMetadataList) ? accountName : accessKeyMetadataList.get(0).userName();
    }
}