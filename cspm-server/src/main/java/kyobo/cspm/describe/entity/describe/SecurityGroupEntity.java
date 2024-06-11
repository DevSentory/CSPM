package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "resource_security_group", schema = "cspm")
public class SecurityGroupEntity {

    @Id
    @Column(nullable = false, name = "group_id")
    private String groupId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "group_name", length = 30)
    private String groupName;

    @Column(name = "ip_permissions", columnDefinition = "TEXT")
    private String ipPermissions;

    @Column(name = "owner_id", length = 30)
    private String ownerId;

    @Column(name = "ip_permissions_egress", columnDefinition = "TEXT")
    private String ipPermissionsEgress;

    @OneToMany(mappedBy = "securityGroupEntity", cascade = CascadeType.ALL)
    List<InstanceEntity> instanceEntityList = new ArrayList<>();
}