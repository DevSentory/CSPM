package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.DescribeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DescribeDto extends BaseDto {

    private String scanTime;
    private String createTime;
    private String client;
    private String accountId;
    private String accountName;
    private String serviceGroup;
    private String resourceId;
    private String tag;

    /**
     * task : DescribeEntity 데이터를 Dto 객체로 변환
     */
    public static DescribeDto of(DescribeEntity describeEntity) {
        return new DescribeDto(
                describeEntity.getScanTime(),
                describeEntity.getCreateTime(),
                describeEntity.getClient(),
                describeEntity.getAccountId(),
                describeEntity.getAccountName(),
                describeEntity.getServiceGroup(),
                describeEntity.getResourceId(),
                describeEntity.getTag()
                );
    }
}
