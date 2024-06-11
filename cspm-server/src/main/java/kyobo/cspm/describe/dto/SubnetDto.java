package kyobo.cspm.describe.dto;


import kyobo.cspm.describe.entity.describe.SubnetEntity;
import kyobo.cspm.describe.entity.describe.VpcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class SubnetDto {

    private String SubnetId;
    private String scanTime;
    private String availabilityZone;
    private String availabilityZoneId;
    private Integer AvailableIpAddressCount;
    private String CidrBlock;
    private Boolean defaultForAz;
    private Boolean mapPublicIpOnLaunch;
    private Boolean MapCustomerOwnedIpOnLaunch;
    private String state;

    private String ownerId;
    private Boolean assignIpv6AddressOnCreation;
    private String Ipv6CidrBlockAssociationSet;
    private String SubnetArn;
    private Boolean enableDns64;
    private Boolean ipv6Native;
    private String privateDnsNameOptionsOnLaunch;

    private String vpcId;


    public static SubnetDto of(SubnetEntity subnetEntity) {
        return new SubnetDto(
                subnetEntity.getSubnetId(),
                subnetEntity.getScanTime(),
                subnetEntity.getAvailabilityZone(),
                subnetEntity.getAvailabilityZoneId(),
                subnetEntity.getAvailableIpAddressCount(),
                subnetEntity.getCidrBlock(),
                subnetEntity.getDefaultForAz(),
                subnetEntity.getMapPublicIpOnLaunch(),
                subnetEntity.getMapCustomerOwnedIpOnLaunch(),
                subnetEntity.getState(),
                subnetEntity.getOwnerId(),
                subnetEntity.getAssignIpv6AddressOnCreation(),
                subnetEntity.getIpv6CidrBlockAssociationSet(),
                subnetEntity.getSubnetArn(),
                subnetEntity.getEnableDns64(),
                subnetEntity.getIpv6Native(),
                subnetEntity.getPrivateDnsNameOptionsOnLaunch(),
                Optional.ofNullable(subnetEntity.getVpcEntity())
                        .map(VpcEntity::getVpcId)
                        .orElse(null)
        );
    }
}
