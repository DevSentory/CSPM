package kyobo.cspm.service.descirbe_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobo.cspm.describe.dto.DescribeAccount;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.describe.S3Entity;
import kyobo.cspm.describe.repository.S3Repository;
import kyobo.cspm.enums.DescribeType;
import kyobo.cspm.service.aws.CredentialsManager;
import kyobo.cspm.service.components.DescribeSaveComponent;
import kyobo.cspm.utils.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class S3Group {

    private final DescribeSaveComponent describeSaveComponent;
    private final S3Repository s3Repository;

    private final CredentialsManager credentialsManager;
    private final DescribeType.Group serviceGroup = DescribeType.S3.getGroup();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Setter
    private DescribeAccount describeAccount;

    @Getter
    List<DescribeEntity> describeEntityList = new ArrayList<>();

    public List<DescribeEntity> bucketInfoDescribe() {
        S3Client s3Client = credentialsManager.getS3Client();
        List<S3Entity> S3EntityList = new ArrayList<>();

        // S3 자원 스캔
        s3Repository.deleteAll();
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);

        // S3 자원을 순회하면서 정보를 가져온다.
        LocalDateTime scanTime = LocalDateTime.now();
        for (Bucket bucket : listBucketsResponse.buckets()) {
            if (null == bucket) continue;

            S3Entity S3Entity = new S3Entity();
            S3Entity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            S3Entity.setS3Id(bucket.name());

            // 버킷에 대한 공개 엑세스를 관리하는 설정
            S3Entity.setPublicAccessBlockConfiguration(
                    Optional.ofNullable(s3Client.getPublicAccessBlock(GetPublicAccessBlockRequest.builder().bucket(bucket.name()).build()))
                            .map(GetPublicAccessBlockResponse::publicAccessBlockConfiguration)
                            .map(PublicAccessBlockConfiguration::toString)
                            .orElse(null));

            // 버킷의 서버측 암호화 설정
            S3Entity.setServerSideEncryptionConfiguration(
                    Optional.ofNullable(s3Client.getBucketEncryption(GetBucketEncryptionRequest.builder().bucket(bucket.name()).build()))
                            .map(GetBucketEncryptionResponse::serverSideEncryptionConfiguration)
                            .map(ServerSideEncryptionConfiguration::toString)
                            .orElse(null));

            // 'resource_s3' 테이블 저장용 리스트
            S3EntityList.add(S3Entity);
            try {
                String S3EntityJson = objectMapper.writeValueAsString(S3Entity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), S3Entity.getS3Id(), "-", S3EntityJson));

            }catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }

        // resource_s3 리스트를 s3Repository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.S3, S3EntityList, s3Repository);
        return describeEntityList;
    }
}