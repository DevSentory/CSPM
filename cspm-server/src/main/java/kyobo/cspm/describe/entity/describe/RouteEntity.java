package kyobo.cspm.describe.entity.describe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resource_route", schema = "cspm")
public class RouteEntity {

    @Id
    @Column(nullable = false, name = "route_id")
    private String routeId;

    @Column(name = "scan_time")
    private String scanTime;

    @Column(name = "associations", columnDefinition = "TEXT")
    private String associations;

    @Column(name = "propagating_vgws")
    private String propagatingVgws;

    @Column(name = "routes", columnDefinition = "TEXT")
    private String routes;

    @Column(name = "tags")
    private String tags;

    @Column(name = "owner_id", length = 30)
    private String ownerId;

    @Transient
    private String vpcId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vpc_id")
    private VpcEntity vpcEntity;
}