package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resource_igw", schema = "cspm")
public class IGWEntity {

    @Id
    @Column(nullable = false, name = "igw_id")
    private String igwId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "attachments", columnDefinition = "TEXT")
    private String attachments;

    @Column(name = "owner_id", length = 20)
    private String ownerId;

    @Column(name = "tags")
    private String tags;

    @Transient
    private String vpcId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vpc_id")
    private VpcEntity vpcEntity;
}