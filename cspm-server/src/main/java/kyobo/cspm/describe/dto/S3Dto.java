package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.S3Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class S3Dto {
    private String s3Id;
    private String ServerSideEncryptionConfiguration;
    private String PublicAccessBlockConfiguration;

    public static S3Dto of(S3Entity s3Entity){
        return new S3Dto(
                s3Entity.getS3Id(),
                s3Entity.getServerSideEncryptionConfiguration(),
                s3Entity.getPublicAccessBlockConfiguration()
        );
    }
}
