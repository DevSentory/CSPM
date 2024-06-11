package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInstanceEntity is a Querydsl query type for InstanceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInstanceEntity extends EntityPathBase<InstanceEntity> {

    private static final long serialVersionUID = -1022558318L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInstanceEntity instanceEntity = new QInstanceEntity("instanceEntity");

    public final NumberPath<Integer> amiLaunchIndex = createNumber("amiLaunchIndex", Integer.class);

    public final StringPath architecture = createString("architecture");

    public final StringPath blockDeviceMappings = createString("blockDeviceMappings");

    public final StringPath bootMode = createString("bootMode");

    public final StringPath capacityReservationSpecification = createString("capacityReservationSpecification");

    public final StringPath clientToken = createString("clientToken");

    public final StringPath cpuOptions = createString("cpuOptions");

    public final StringPath CurrentInstanceBootMode = createString("CurrentInstanceBootMode");

    public final QEBSEntity EBSEntity;

    public final BooleanPath ebsOptimized = createBoolean("ebsOptimized");

    public final BooleanPath enaSupport = createBoolean("enaSupport");

    public final StringPath enclaveOptions = createString("enclaveOptions");

    public final QENIEntity ENIEntity;

    public final StringPath hibernationOptions = createString("hibernationOptions");

    public final StringPath httpEndpoint = createString("httpEndpoint");

    public final StringPath httpProtocolIpv6 = createString("httpProtocolIpv6");

    public final NumberPath<Integer> httpPutResponseHopLimit = createNumber("httpPutResponseHopLimit", Integer.class);

    public final StringPath hypervisor = createString("hypervisor");

    public final StringPath imageId = createString("imageId");

    public final StringPath instanceId = createString("instanceId");

    public final StringPath instanceMetadataTags = createString("instanceMetadataTags");

    public final StringPath instanceType = createString("instanceType");

    public final StringPath keyName = createString("keyName");

    public final StringPath launchTime = createString("launchTime");

    public final StringPath maintenanceOptions = createString("maintenanceOptions");

    public final StringPath metadataOptions = createString("metadataOptions");

    public final StringPath monitoring = createString("monitoring");

    public final StringPath networkInterfaces = createString("networkInterfaces");

    public final StringPath placement = createString("placement");

    public final StringPath platformDetails = createString("platformDetails");

    public final StringPath privateDnsName = createString("privateDnsName");

    public final StringPath privateDnsNameOptions = createString("privateDnsNameOptions");

    public final StringPath privateIpAddress = createString("privateIpAddress");

    public final StringPath productCodes = createString("productCodes");

    public final StringPath publicAddress = createString("publicAddress");

    public final StringPath publicDnsName = createString("publicDnsName");

    public final StringPath rootDeviceName = createString("rootDeviceName");

    public final StringPath rootDeviceType = createString("rootDeviceType");

    public final StringPath scanTime = createString("scanTime");

    public final QSecurityGroupEntity securityGroupEntity;

    public final StringPath securityGroups = createString("securityGroups");

    public final BooleanPath sourceDestCheck = createBoolean("sourceDestCheck");

    public final StringPath state = createString("state");

    public final StringPath stateTransitionReason = createString("stateTransitionReason");

    public final QSubnetEntity subnetEntity;

    public final StringPath tags = createString("tags");

    public final StringPath usageOperation = createString("usageOperation");

    public final StringPath usageOperationUpdateTime = createString("usageOperationUpdateTime");

    public final StringPath virtualizationType = createString("virtualizationType");

    public final StringPath vpcId = createString("vpcId");

    public QInstanceEntity(String variable) {
        this(InstanceEntity.class, forVariable(variable), INITS);
    }

    public QInstanceEntity(Path<? extends InstanceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInstanceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInstanceEntity(PathMetadata metadata, PathInits inits) {
        this(InstanceEntity.class, metadata, inits);
    }

    public QInstanceEntity(Class<? extends InstanceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.EBSEntity = inits.isInitialized("EBSEntity") ? new QEBSEntity(forProperty("EBSEntity"), inits.get("EBSEntity")) : null;
        this.ENIEntity = inits.isInitialized("ENIEntity") ? new QENIEntity(forProperty("ENIEntity"), inits.get("ENIEntity")) : null;
        this.securityGroupEntity = inits.isInitialized("securityGroupEntity") ? new QSecurityGroupEntity(forProperty("securityGroupEntity")) : null;
        this.subnetEntity = inits.isInitialized("subnetEntity") ? new QSubnetEntity(forProperty("subnetEntity"), inits.get("subnetEntity")) : null;
    }

}

