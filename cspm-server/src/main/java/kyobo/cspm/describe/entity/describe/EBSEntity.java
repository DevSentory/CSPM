package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resource_ebs", schema = "cspm")
public class EBSEntity {

    @Id
    @Column(nullable = false, name = "ebs_id")
    private String ebsId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "attachments")
    private String attachments;

    @Column(name = "availability_zone", length = 20)
    private String availabilityZone;

    @Column(name = "create_time", length = 30)
    private String createTime;

    @Column(name = "encrypted")
    private Boolean encrypted;

    @Column(name = "size")
    private Integer size;

    @Column(name = "snapshot_id", length = 30)
    private String snapshotId;

    @Column(name = "state", length = 10)
    private String state;

    @Column(name = "iops")
    private Integer iops;

    @Column(name = "volume_type", length = 10)
    private String volumeType;

    @Column(name = "multi_attach_enabled")
    private Boolean multiAttachEnabled;

    @Column(name = "throughput")
    private Integer throughput;

    @Transient
    private String instanceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instance_id")
    private InstanceEntity instanceEntity;
}