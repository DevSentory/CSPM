package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.ENIEntity;
import kyobo.cspm.describe.entity.describe.InstanceEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ENIDto {
    private String eniId;
    private String scanTime;
    private String attachment;
    private String availabilityZone;
    private String description;
    private String groupsgr;
    private String interfaceType;
    private String Ipv6Addresses;
    private String macAddress;
    private String ownerId;
    private String PrivateDnsName;
    private String PrivateIpAddress;
    private String privateIpAddresses;
    private Boolean requesterManaged;
    private Boolean sourceDestCheck;
    private String status;
    private String subnetId;
    private String tagSet;
    private String vpcId;
    private String attachmentTime;
    private String instanceId;

    public static ENIDto of(ENIEntity ENIEntity) {
        return new ENIDto(
                ENIEntity.getEniId(),
                ENIEntity.getScanTime(),
                ENIEntity.getAttachment(),
                ENIEntity.getAvailabilityZone(),
                ENIEntity.getDescription(),
                ENIEntity.getGroupsgr(),
                ENIEntity.getInterfaceType(),
                ENIEntity.getIpv6Addresses(),
                ENIEntity.getMacAddress(),
                ENIEntity.getOwnerId(),
                ENIEntity.getPrivateDnsName(),
                ENIEntity.getPrivateIpAddress(),
                ENIEntity.getPrivateIpAddresses(),
                ENIEntity.getRequesterManaged(),
                ENIEntity.getSourceDestCheck(),
                ENIEntity.getStatus(),
                ENIEntity.getSubnetId(),
                ENIEntity.getTagSet(),
                ENIEntity.getVpcId(),
                ENIEntity.getAttachmentTime(),
                Optional.ofNullable(ENIEntity.getInstanceEntity())
                        .map(InstanceEntity::getInstanceId)
                        .orElse(null)
        );
    }
}
