package kyobo.cspm.service.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

@Getter
@Setter
@AllArgsConstructor
public class CredentialsInfo {

    /*
     * AwsBasicCredentials : AWS 서비스를 사용하기 위한 사용자의 인증 정보
     * (실제 AWS 자원에 접근하기 위해 AwsBasicCredentials 객체를 사용한다.)
     */

    private AwsBasicCredentials credentials;
    private Region region;
}