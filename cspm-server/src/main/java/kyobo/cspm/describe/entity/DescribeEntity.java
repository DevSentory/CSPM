package kyobo.cspm.describe.entity;

import jakarta.persistence.*;
import kyobo.cspm.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "resources_describe", schema = "cspm")
public class DescribeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scan_time", length = Constants.LENGTH_SMALL, nullable = false)
    private String scanTime;

    @Column(name = "create_time", length = Constants.LENGTH_SMALL, nullable = false)
    private String createTime;

    @Column(name = "client", length = Constants.LENGTH_SMALL, nullable = false)
    private String client;

    @Column(name = "account_id", length = Constants.LENGTH_SMALL, nullable = false)
    private String accountId;

    @Column(name = "account_name", length = Constants.LENGTH_SMALL, nullable = false)
    private String accountName;

    @Column(name = "service_group", length = Constants.LENGTH_SMALL, nullable = false)
    private String serviceGroup;

    @Column(name = "resource_id", length = Constants.LENGTH_SMALL, nullable = false)
    private String resourceId;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "describe_result")
    private String describeResult;

    @Column(name = "resource_json", columnDefinition = "Text")
    private String resourceJson;

    // task : 스캔 결과 후 테이블에 추가 할 데이터
    public static DescribeEntity newEntityDescribe(
            String createTime, String serviceGroup, String resourceId, String tag, String resourceJson) {

        DescribeEntity describeEntity = new DescribeEntity();

        // 스캔 데이터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        describeEntity.setScanTime(LocalDateTime.now().format(formatter));
        try {
            OffsetDateTime odt = OffsetDateTime.parse(createTime);
            describeEntity.setCreateTime(odt.format(formatter));
        } catch (DateTimeParseException e) {
            describeEntity.setCreateTime("-");
        }
        describeEntity.setServiceGroup(serviceGroup);
        describeEntity.setResourceId(resourceId);
        describeEntity.setTag(tag);
        describeEntity.setResourceJson(resourceJson);

        return describeEntity;
    }

    public void setSuccess(boolean isSuccess) {
        this.describeResult = isSuccess ? Constants.SUCCESS : Constants.FAIL;
    }
}