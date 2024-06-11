package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIGWEntity is a Querydsl query type for IGWEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIGWEntity extends EntityPathBase<IGWEntity> {

    private static final long serialVersionUID = 1838547298L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIGWEntity iGWEntity = new QIGWEntity("iGWEntity");

    public final StringPath attachments = createString("attachments");

    public final StringPath igwId = createString("igwId");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath tags = createString("tags");

    public final QVpcEntity vpcEntity;

    public QIGWEntity(String variable) {
        this(IGWEntity.class, forVariable(variable), INITS);
    }

    public QIGWEntity(Path<? extends IGWEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIGWEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIGWEntity(PathMetadata metadata, PathInits inits) {
        this(IGWEntity.class, metadata, inits);
    }

    public QIGWEntity(Class<? extends IGWEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vpcEntity = inits.isInitialized("vpcEntity") ? new QVpcEntity(forProperty("vpcEntity"), inits.get("vpcEntity")) : null;
    }

}

