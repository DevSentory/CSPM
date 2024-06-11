package kyobo.cspm.describe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorLogEntity is a Querydsl query type for ErrorLogEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLogEntity extends EntityPathBase<ErrorLogEntity> {

    private static final long serialVersionUID = -65786808L;

    public static final QErrorLogEntity errorLogEntity = new QErrorLogEntity("errorLogEntity");

    public final StringPath accountId = createString("accountId");

    public final StringPath accountName = createString("accountName");

    public final StringPath describeTime = createString("describeTime");

    public final StringPath exceptionCode = createString("exceptionCode");

    public final StringPath exceptionMsg = createString("exceptionMsg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath resource = createString("resource");

    public final StringPath serviceGroup = createString("serviceGroup");

    public QErrorLogEntity(String variable) {
        super(ErrorLogEntity.class, forVariable(variable));
    }

    public QErrorLogEntity(Path<? extends ErrorLogEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorLogEntity(PathMetadata metadata) {
        super(ErrorLogEntity.class, metadata);
    }

}

