package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteEntity is a Querydsl query type for RouteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouteEntity extends EntityPathBase<RouteEntity> {

    private static final long serialVersionUID = 234214162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteEntity routeEntity = new QRouteEntity("routeEntity");

    public final StringPath associations = createString("associations");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath propagatingVgws = createString("propagatingVgws");

    public final StringPath routeId = createString("routeId");

    public final StringPath routes = createString("routes");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath tags = createString("tags");

    public final QVpcEntity vpcEntity;

    public QRouteEntity(String variable) {
        this(RouteEntity.class, forVariable(variable), INITS);
    }

    public QRouteEntity(Path<? extends RouteEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteEntity(PathMetadata metadata, PathInits inits) {
        this(RouteEntity.class, metadata, inits);
    }

    public QRouteEntity(Class<? extends RouteEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vpcEntity = inits.isInitialized("vpcEntity") ? new QVpcEntity(forProperty("vpcEntity"), inits.get("vpcEntity")) : null;
    }

}

