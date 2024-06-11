package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "resource_subnet", schema = "cspm")
public class SubnetEntity {

    @Id
    @Column(nullable = false, name = "subnet_id")
    private String subnetId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "availability_zone", length = 20)
    private String availabilityZone;

    @Column(name = "availability_zone_id", length = 20)
    private String availabilityZoneId;

    @Column(name = "available_ip_address_count")
    private Integer AvailableIpAddressCount;

    @Column(name = "cidr_block", length = 20)
    private String CidrBlock;

    @Column(name = "default_for_az")
    private Boolean defaultForAz;

    @Column(name = "map_public_ip_on_launch")
    private Boolean mapPublicIpOnLaunch;

    @Column(name = "map_customer_owned_ip_on_launch")
    private Boolean mapCustomerOwnedIpOnLaunch;

    @Column(name = "state", length = 20)
    private String state;

    @Column(name = "owner_id", length = 30)
    private String ownerId;

    @Column(name = "assign_ipv6_address_on_creation")
    private Boolean assignIpv6AddressOnCreation;

    @Column(name = "ipv6_cidr_block_association_set")
    private String Ipv6CidrBlockAssociationSet;

    @Column(name = "subnet_arn", columnDefinition = "TEXT")
    private String SubnetArn;

    @Column(name = "enable_dns64")
    private Boolean enableDns64;

    @Column(name = "ipv6_native")
    private Boolean ipv6Native;

    @Column(name = "private_dns_name_options_on_launch", columnDefinition = "TEXT")
    private String privateDnsNameOptionsOnLaunch;

    @Transient
    private String vpcId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vpc_id")
    private VpcEntity vpcEntity;

    @OneToMany(mappedBy = "subnetEntity", cascade = CascadeType.ALL)
    private List<InstanceEntity> instanceEntity = new ArrayList<>();
}