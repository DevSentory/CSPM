package kyobo.cspm.describe.entity;

import jakarta.persistence.*;
import kyobo.cspm.describe.dto.AccountDto;
import kyobo.cspm.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts", schema = "cspm")
public class AccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 고객사 등록
     */
    @Column(name = " account_id", length = Constants.LENGTH_SMALL, nullable = false)
    private String accountId;

    @Column(name = "client", length = Constants.LENGTH_SMALL, nullable = false)
    private String client;

    @Column(name = "code", length = Constants.LENGTH_SMALL, nullable = false)
    private String code;

    @Column(name = "account_name", length = Constants.LENGTH_SMALL, nullable = false)
    private String accountName;

    @Column(name = "access_key", length = Constants.LENGTH_MIDDLE, nullable = false)
    private String accessKey;

    @Column(name = "secret_key", length = Constants.LENGTH_MIDDLE, nullable = false)
    private String secretKey;

    @Column(name = "region", length = Constants.LENGTH_SMALL, nullable = false)
    private String region;

    // 비고
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    /*
     * '스캔 설정' 페이지
     */
    // Account 등록 시간
    @Column(name = "register_time", length = Constants.LENGTH_SMALL, nullable = false)
    private String registerTime;

    // 최근 리소스 스캔 시간
    @Column(name = "last_update_describe_time", length = Constants.LENGTH_SMALL, nullable = false)
    private String lastUpdateDescribeTime;

    // 스캔 결과
    @Column(name = "describe_result", length = Constants.LENGTH_SMALL, nullable = false)
    private String describeResult;

    public AccountsEntity(AccountDto accountDto, String registerTime) {
        accountId = accountDto.getAccountId();
        client = accountDto.getClient();
        code = accountDto.getCode();
        code = code.toUpperCase();
        accountName = accountDto.getAccountName();
        accessKey = accountDto.getAccessKey();
        secretKey = accountDto.getSecretKey();
        region = accountDto.getRegion();
        comment = accountDto.getComment();
        this.registerTime = registerTime;

        // 스캔 관련 데이터 값 초기화
        lastUpdateDescribeTime = describeResult = "-";
    }

    // task : 자원 스캔 후 스캔 설정 페이지에 결과 및 최근 업데이트 시간 갱신
    public void UpdateDescribeData(String lastUpdateDescribeTime, String describeResult) {
        this.lastUpdateDescribeTime = lastUpdateDescribeTime;
        this.describeResult = describeResult;
    }
}