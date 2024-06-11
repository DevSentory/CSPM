package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubnetEntity is a Querydsl query type for SubnetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubnetEntity extends EntityPathBase<SubnetEntity> {

    private static final long serialVersionUID = 180543194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubnetEntity subnetEntity = new QSubnetEntity("subnetEntity");

    public final BooleanPath assignIpv6AddressOnCreation = createBoolean("assignIpv6AddressOnCreation");

    public final StringPath availabilityZone = createString("availabilityZone");

    public final StringPath availabilityZoneId = createString("availabilityZoneId");

    public final NumberPath<Integer> AvailableIpAddressCount = createNumber("AvailableIpAddressCount", Integer.class);

    public final StringPath CidrBlock = createString("CidrBlock");

    public final BooleanPath defaultForAz = createBoolean("defaultForAz");

    public final BooleanPath enableDns64 = createBoolean("enableDns64");

    public final ListPath<InstanceEntity, QInstanceEntity> instanceEntity = this.<InstanceEntity, QInstanceEntity>createList("instanceEntity", InstanceEntity.class, QInstanceEntity.class, PathInits.DIRECT2);

    public final StringPath Ipv6CidrBlockAssociationSet = createString("Ipv6CidrBlockAssociationSet");

    public final BooleanPath ipv6Native = createBoolean("ipv6Native");

    public final BooleanPath mapCustomerOwnedIpOnLaunch = createBoolean("mapCustomerOwnedIpOnLaunch");

    public final BooleanPath mapPublicIpOnLaunch = createBoolean("mapPublicIpOnLaunch");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath privateDnsNameOptionsOnLaunch = createString("privateDnsNameOptionsOnLaunch");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath state = createString("state");

    public final StringPath SubnetArn = createString("SubnetArn");

    public final StringPath subnetId = createString("subnetId");

    public final QVpcEntity vpcEntity;

    public QSubnetEntity(String variable) {
        this(SubnetEntity.class, forVariable(variable), INITS);
    }

    public QSubnetEntity(Path<? extends SubnetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubnetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubnetEntity(PathMetadata metadata, PathInits inits) {
        this(SubnetEntity.class, metadata, inits);
    }

    public QSubnetEntity(Class<? extends SubnetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vpcEntity = inits.isInitialized("vpcEntity") ? new QVpcEntity(forProperty("vpcEntity"), inits.get("vpcEntity")) : null;
    }

}

