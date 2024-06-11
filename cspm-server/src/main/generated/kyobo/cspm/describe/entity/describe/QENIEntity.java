package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QENIEntity is a Querydsl query type for ENIEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QENIEntity extends EntityPathBase<ENIEntity> {

    private static final long serialVersionUID = 253051369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QENIEntity eNIEntity = new QENIEntity("eNIEntity");

    public final StringPath attachment = createString("attachment");

    public final StringPath attachmentTime = createString("attachmentTime");

    public final StringPath availabilityZone = createString("availabilityZone");

    public final StringPath description = createString("description");

    public final StringPath eniId = createString("eniId");

    public final StringPath groupsgr = createString("groupsgr");

    public final QInstanceEntity instanceEntity;

    public final StringPath interfaceType = createString("interfaceType");

    public final StringPath Ipv6Addresses = createString("Ipv6Addresses");

    public final StringPath macAddress = createString("macAddress");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath privateDnsName = createString("privateDnsName");

    public final StringPath privateIpAddress = createString("privateIpAddress");

    public final StringPath privateIpAddresses = createString("privateIpAddresses");

    public final BooleanPath requesterManaged = createBoolean("requesterManaged");

    public final StringPath scanTime = createString("scanTime");

    public final BooleanPath sourceDestCheck = createBoolean("sourceDestCheck");

    public final StringPath status = createString("status");

    public final StringPath subnetId = createString("subnetId");

    public final StringPath tagSet = createString("tagSet");

    public final StringPath vpcId = createString("vpcId");

    public QENIEntity(String variable) {
        this(ENIEntity.class, forVariable(variable), INITS);
    }

    public QENIEntity(Path<? extends ENIEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QENIEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QENIEntity(PathMetadata metadata, PathInits inits) {
        this(ENIEntity.class, metadata, inits);
    }

    public QENIEntity(Class<? extends ENIEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.instanceEntity = inits.isInitialized("instanceEntity") ? new QInstanceEntity(forProperty("instanceEntity"), inits.get("instanceEntity")) : null;
    }

}

