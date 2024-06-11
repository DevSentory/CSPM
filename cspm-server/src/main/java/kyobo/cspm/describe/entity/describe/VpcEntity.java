package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import kyobo.cspm.describe.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "resource_vpc", schema = "cspm")
public class VpcEntity extends BaseDto {

    @Id
    @Column(nullable = false, name = "vpc_id")
    private String vpcId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "cidr_block", length = 20)
    private String cidrBlock;

    @Column(name = "dhcp_options_id", length = 30)
    private String dhcpOptionsId;

    @Column(name = "state", length = 20)
    private String state;

    @Column(name = "ownerId")
    private String ownerId;

    @Column(name = "instance_tenancy", length = 30)
    private String instanceTenancy;

    @Column(name = "cidr_block_association_set", columnDefinition = "TEXT")
    private String cidrBlockAssociationSet;

    @Column(name = "cidr_block_association_set_association_id", length = 50)
    private String cidrBlockAssociationSetAssociationId;

    @Column(name = "cidr_block_association_cidr_block", length = 20)
    private String cidrBlockAssociationCidrBlock;

    @Column(name = "cidr_block_association_cidr_block_state", length = 50)
    private String cidrBlockAssociationCidrBlockState;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "tags")
    private String tags;

    @OneToMany(mappedBy = "vpcEntity", cascade = CascadeType.ALL)
    List<SubnetEntity> subnetEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "vpcEntity", cascade = CascadeType.ALL)
    private RouteEntity routeEntity;

    @OneToOne(mappedBy = "vpcEntity", cascade = CascadeType.ALL)
    private IGWEntity IGWEntity;
}