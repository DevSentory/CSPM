package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVpcEntity is a Querydsl query type for VpcEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVpcEntity extends EntityPathBase<VpcEntity> {

    private static final long serialVersionUID = 317365042L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVpcEntity vpcEntity = new QVpcEntity("vpcEntity");

    public final StringPath cidrBlock = createString("cidrBlock");

    public final StringPath cidrBlockAssociationCidrBlock = createString("cidrBlockAssociationCidrBlock");

    public final StringPath cidrBlockAssociationCidrBlockState = createString("cidrBlockAssociationCidrBlockState");

    public final StringPath cidrBlockAssociationSet = createString("cidrBlockAssociationSet");

    public final StringPath cidrBlockAssociationSetAssociationId = createString("cidrBlockAssociationSetAssociationId");

    public final StringPath dhcpOptionsId = createString("dhcpOptionsId");

    public final QIGWEntity IGWEntity;

    public final StringPath instanceTenancy = createString("instanceTenancy");

    public final BooleanPath isDefault = createBoolean("isDefault");

    public final StringPath ownerId = createString("ownerId");

    public final QRouteEntity routeEntity;

    public final StringPath scanTime = createString("scanTime");

    public final StringPath state = createString("state");

    public final ListPath<SubnetEntity, QSubnetEntity> subnetEntityList = this.<SubnetEntity, QSubnetEntity>createList("subnetEntityList", SubnetEntity.class, QSubnetEntity.class, PathInits.DIRECT2);

    public final StringPath tags = createString("tags");

    public final StringPath vpcId = createString("vpcId");

    public QVpcEntity(String variable) {
        this(VpcEntity.class, forVariable(variable), INITS);
    }

    public QVpcEntity(Path<? extends VpcEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVpcEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVpcEntity(PathMetadata metadata, PathInits inits) {
        this(VpcEntity.class, metadata, inits);
    }

    public QVpcEntity(Class<? extends VpcEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.IGWEntity = inits.isInitialized("IGWEntity") ? new QIGWEntity(forProperty("IGWEntity"), inits.get("IGWEntity")) : null;
        this.routeEntity = inits.isInitialized("routeEntity") ? new QRouteEntity(forProperty("routeEntity"), inits.get("routeEntity")) : null;
    }

}

