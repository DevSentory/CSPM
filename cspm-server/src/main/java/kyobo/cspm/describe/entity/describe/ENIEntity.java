package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "resource_eni", schema = "cspm")
public class ENIEntity {

    @Id
    @Column(name = "eni_id")
    private String eniId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "attachment", columnDefinition = "TEXT")
    private String attachment;

    @Column(name = "availability_zone", length = 20)
    private String availabilityZone;

    @Column(name = "description")
    private String description;

    @Column(name = "groupsgr", length = 100)
    private String groupsgr;

    @Column(name = "interface_type", length = 10)
    private String interfaceType;

    @Column(name = "ipv6_addresses", length = 20)
    private String Ipv6Addresses;

    @Column(name = "mac_address", length = 20)
    private String macAddress;

    @Column(name = "owner_id", length = 20)
    private String ownerId;

    @Column(name = "private_dns_name", length = 50)
    private String privateDnsName;

    @Column(name = "private_ip_address", length = 20)
    private String privateIpAddress;

    @Column(name = "private_ip_addresses", columnDefinition = "TEXT")
    private String privateIpAddresses;

    @Column(name = "requester_managed", length = 10)
    private Boolean requesterManaged;

    @Column(name = "source_dest_check", length = 10)
    private Boolean sourceDestCheck;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "subnet_id", length = 30)
    private String subnetId;

    @Column(name = "tag_set")
    private String tagSet;

    @Column(name = "vpc_id", length = 30)
    private String vpcId;

    @Column(name = "attachment_time", length = 30)
    private String attachmentTime;

    @Transient
    private String instanceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instance_id")
    private InstanceEntity instanceEntity;
}