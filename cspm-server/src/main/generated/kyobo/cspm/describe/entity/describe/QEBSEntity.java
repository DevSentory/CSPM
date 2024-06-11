package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEBSEntity is a Querydsl query type for EBSEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEBSEntity extends EntityPathBase<EBSEntity> {

    private static final long serialVersionUID = 1099266047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEBSEntity eBSEntity = new QEBSEntity("eBSEntity");

    public final StringPath attachments = createString("attachments");

    public final StringPath availabilityZone = createString("availabilityZone");

    public final StringPath createTime = createString("createTime");

    public final StringPath ebsId = createString("ebsId");

    public final BooleanPath encrypted = createBoolean("encrypted");

    public final QInstanceEntity instanceEntity;

    public final NumberPath<Integer> iops = createNumber("iops", Integer.class);

    public final BooleanPath multiAttachEnabled = createBoolean("multiAttachEnabled");

    public final StringPath scanTime = createString("scanTime");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final StringPath snapshotId = createString("snapshotId");

    public final StringPath state = createString("state");

    public final NumberPath<Integer> throughput = createNumber("throughput", Integer.class);

    public final StringPath volumeType = createString("volumeType");

    public QEBSEntity(String variable) {
        this(EBSEntity.class, forVariable(variable), INITS);
    }

    public QEBSEntity(Path<? extends EBSEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEBSEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEBSEntity(PathMetadata metadata, PathInits inits) {
        this(EBSEntity.class, metadata, inits);
    }

    public QEBSEntity(Class<? extends EBSEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.instanceEntity = inits.isInitialized("instanceEntity") ? new QInstanceEntity(forProperty("instanceEntity"), inits.get("instanceEntity")) : null;
    }

}

