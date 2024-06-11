package kyobo.cspm.common.dto;

import kyobo.cspm.describe.entity.DescribeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DescribeXlsxDto implements BaseXlsx{
    private String scanTime;
    private String resourceRegisterTime;
    private String client;
    private String accountName;
    private String accountId;
    private String service;
    private String resourceId;
    private String tag;
    private String resourceJson;

    public static DescribeXlsxDto of(DescribeEntity describeEntity){
        return new DescribeXlsxDto(
                describeEntity.getScanTime(),
                describeEntity.getCreateTime(),
                describeEntity.getClient(),
                describeEntity.getAccountName(),
                describeEntity.getAccountId(),
                describeEntity.getServiceGroup(),
                describeEntity.getResourceId(),
                describeEntity.getTag(),
                describeEntity.getResourceJson()
        );
    }
}
