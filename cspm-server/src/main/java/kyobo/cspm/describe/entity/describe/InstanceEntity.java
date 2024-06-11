package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "resource_instance", schema = "cspm")
public class InstanceEntity {

    @Id
    @Column(nullable = false, name = "instance_id")
    private String instanceId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "ami_launch_index")
    private Integer amiLaunchIndex;

    @Column(name = "image_id", length = 30)
    private String imageId;

    @Column(name = "instance_type", length = 20)
    private String instanceType;

    @Column(name = "launch_time", length = 30)
    private String launchTime;

    @Column(name = "monitoring", length = 10)
    private String monitoring;

    @Column(name = "placement", length = 80)
    private String placement;

    @Column(name = "private_dns_name", length = 50)
    private String privateDnsName;

    @Column(name = "private_ip_address", length = 20)
    private String privateIpAddress;

    @Column(name = "product_codes")
    private String productCodes;

    @Column(name = "public_dns_name")
    private String publicDnsName;

    @Column(name = "public_address")
    private String publicAddress;

    @Column(name = "state", length = 40)
    private String state;

    @Column(name = "state_transition_reason", length = 50)
    private String stateTransitionReason;

    @Column(name = "vpc_id", length = 30)
    private String vpcId;

    @Column(name = "architecture", length = 10)
    private String architecture;

    @Column(name = "block_device_mappings", columnDefinition = "TEXT")
    private String blockDeviceMappings;

    @Column(name = "client_token", length = 40)
    private String clientToken;

    @Column(name = "ebs_optimized")
    private Boolean ebsOptimized;

    @Column(name = "ena_support")
    private Boolean enaSupport;

    @Column(name = "hypervisor", length = 10)
    private String hypervisor;

    @Column(name = "network_interfaces", columnDefinition = "TEXT")
    private String networkInterfaces;

    @Column(name = "root_device_name", length = 20)
    private String rootDeviceName;

    @Column(name = "root_device_type", length = 10)
    private String rootDeviceType;

    @Column(name = "security_groups", length = 100)
    private String securityGroups;

    @Column(name = "source_dest_check", length = 10)
    private Boolean sourceDestCheck;

    @Column(name = "tags")
    private String tags;

    @Column(name = "virtualization_type", length = 10)
    private String virtualizationType;

    @Column(name = "cpu_options", length = 50)
    private String cpuOptions;

    @Column(name = "capacity_reservation_specification", length = 100)
    private String capacityReservationSpecification;

    @Column(name = "hibernation_options", length = 50)
    private String hibernationOptions;

    @Column(name = "metadata_options", columnDefinition = "TEXT")
    private String metadataOptions;

    @Column(name = "http_put_response_hop_limit")
    private Integer httpPutResponseHopLimit;

    @Column(name = "http_endpoint", length = 10)
    private String httpEndpoint;

    @Column(name = "http_protocol_ipv6")
    private String httpProtocolIpv6;

    @Column(name = "instance_metadata_tags", length = 10)
    private String instanceMetadataTags;

    @Column(name = "enclave_options", length = 30)
    private String enclaveOptions;

    @Column(name = "boot_mode", length = 20)
    private String bootMode;

    @Column(name = "platform_details", length = 20)
    private String platformDetails;

    @Column(name = "usageOperation", length = 20)
    private String usageOperation;

    @Column(name = "usage_operation_update_time")
    private String usageOperationUpdateTime;

    @Column(name = "private_dns_name_options", columnDefinition = "TEXT")
    private String privateDnsNameOptions;

    @Column(name = "maintenance_options", length = 60)
    private String maintenanceOptions;

    @Column(name = "current_instance_boot_mode", length = 20)
    private String CurrentInstanceBootMode;

    @Transient
    private String subnetId;

    @Transient
    private String securityGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subnet_id")
    private SubnetEntity subnetEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private SecurityGroupEntity securityGroupEntity;

    @OneToOne(mappedBy = "instanceEntity", cascade = CascadeType.ALL)
    private EBSEntity EBSEntity;

    @OneToOne(mappedBy = "instanceEntity", cascade = CascadeType.ALL)
    private ENIEntity ENIEntity;
}