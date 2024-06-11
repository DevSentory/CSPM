package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.SecurityGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SecurityGroupDto {
    private String groupId;
    private String scanTime;
    private String description;
    private String groupName;
    private String ipPermissions;
    private String ownerId;
    private String ipPermissionsEgress;

    public static SecurityGroupDto of(SecurityGroupEntity securityGroupEntity) {
        return new SecurityGroupDto(
                securityGroupEntity.getGroupId(),
                securityGroupEntity.getScanTime(),
                securityGroupEntity.getDescription(),
                securityGroupEntity.getGroupName(),
                securityGroupEntity.getIpPermissions(),
                securityGroupEntity.getOwnerId(),
                securityGroupEntity.getIpPermissionsEgress()
        );
    }
}
