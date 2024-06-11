package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.VpcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class VpcDto {
    private String vpcId;
    private String scanTime;
    private String cidrBlock;
    private String dhcpOptionsId;
    private String state;

    private String ownerId;
    private String instanceTenancy;
    private String cidrBlockAssociationSet;
    private String cidrBlockAssociationSetAssociationId;
    private String cidrBlockAssociationCidrBlock;
    private String cidrBlockAssociationCidrBlockState;
    private Boolean isDefault;
    private String tags;


    public static VpcDto of(VpcEntity vpcEntity) {
        return new VpcDto(
                vpcEntity.getVpcId(),
                vpcEntity.getScanTime(),
                vpcEntity.getCidrBlock(),
                vpcEntity.getDhcpOptionsId(),
                vpcEntity.getState(),
                vpcEntity.getOwnerId(),
                vpcEntity.getInstanceTenancy(),
                vpcEntity.getCidrBlockAssociationSet(),
                vpcEntity.getCidrBlockAssociationSetAssociationId(),
                vpcEntity.getCidrBlockAssociationCidrBlock(),
                vpcEntity.getCidrBlockAssociationCidrBlockState(),
                vpcEntity.getIsDefault(),
                vpcEntity.getTags()
        );
    }
}
