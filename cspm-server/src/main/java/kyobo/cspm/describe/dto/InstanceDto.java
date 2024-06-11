package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.InstanceEntity;
import kyobo.cspm.describe.entity.describe.SecurityGroupEntity;
import kyobo.cspm.describe.entity.describe.SubnetEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InstanceDto {
    private String instanceId;
    private String scanTime;
    private String keyName;
    private Integer amiLaunchIndex;
    private String imageId;
    private String instanceType;
    private String launchTime;
    private String monitoring;
    private String placement;
    private String privateDnsName;
    private String privateIpAddress;
    private String productCodes;
    private String publicDnsName;
    private String publicAddress;
    private String state;
    private String stateTransitionReason;
    private String vpcId;
    private String architecture;
    private String blockDeviceMappings;
    private String clientToken;
    private Boolean ebsOptimized;
    private Boolean enaSupport;
    private String hypervisor;
    private String networkInterfaces;
    private String rootDeviceName;
    private String rootDeviceType;
    private String securityGroups;
    private Boolean sourceDestCheck;
    private String tags;
    private String virtualizationType;
    private String cpuOptions;
    private String capacityReservationSpecification;
    private String hibernationOptions;
    private String metadataOptions;
    private Integer httpPutResponseHopLimit;
    private String httpEndpoint;
    private String httpProtocolIpv6;
    private String instanceMetadataTags;
    private String enclaveOptions;
    private String bootMode;
    private String platformDetails;
    private String usageOperation;
    private String usageOperationUpdateTime;
    private String privateDnsNameOptions;
    private String maintenanceOptions;
    private String CurrentInstanceBootMode;
    private String subnetId;
    private String groupId;

    public static InstanceDto of(InstanceEntity instanceEntity) {
        return new InstanceDto(
                instanceEntity.getInstanceId(),
                instanceEntity.getScanTime(),
                instanceEntity.getKeyName(),
                instanceEntity.getAmiLaunchIndex(),
                instanceEntity.getImageId(),
                instanceEntity.getInstanceType(),
                instanceEntity.getLaunchTime(),
                instanceEntity.getMonitoring(),
                instanceEntity.getPlacement(),
                instanceEntity.getPrivateDnsName(),
                instanceEntity.getPrivateIpAddress(),
                instanceEntity.getProductCodes(),
                instanceEntity.getPublicDnsName(),
                instanceEntity.getPublicAddress(),
                instanceEntity.getState(),
                instanceEntity.getStateTransitionReason(),
                instanceEntity.getVpcId(),
                instanceEntity.getArchitecture(),
                instanceEntity.getBlockDeviceMappings(),
                instanceEntity.getClientToken(),
                instanceEntity.getEbsOptimized(),
                instanceEntity.getEnaSupport(),
                instanceEntity.getHypervisor(),
                instanceEntity.getNetworkInterfaces(),
                instanceEntity.getRootDeviceName(),
                instanceEntity.getRootDeviceType(),
                instanceEntity.getSecurityGroups(),
                instanceEntity.getSourceDestCheck(),
                instanceEntity.getTags(),
                instanceEntity.getVirtualizationType(),
                instanceEntity.getCpuOptions(),
                instanceEntity.getCapacityReservationSpecification(),
                instanceEntity.getHibernationOptions(),
                instanceEntity.getMetadataOptions(),
                instanceEntity.getHttpPutResponseHopLimit(),
                instanceEntity.getHttpEndpoint(),
                instanceEntity.getHttpProtocolIpv6(),
                instanceEntity.getInstanceMetadataTags(),
                instanceEntity.getEnclaveOptions(),
                instanceEntity.getBootMode(),
                instanceEntity.getPlatformDetails(),
                instanceEntity.getUsageOperation(),
                instanceEntity.getUsageOperationUpdateTime(),
                instanceEntity.getPrivateDnsNameOptions(),
                instanceEntity.getMaintenanceOptions(),
                instanceEntity.getCurrentInstanceBootMode(),
                Optional.ofNullable(instanceEntity.getSubnetEntity())
                        .map(SubnetEntity::getSubnetId)
                        .orElse(null),
                Optional.ofNullable(instanceEntity.getSecurityGroupEntity())
                        .map(SecurityGroupEntity::getGroupId)
                        .orElse(null)
        );
    }
}
