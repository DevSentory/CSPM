package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QS3Entity is a Querydsl query type for S3Entity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QS3Entity extends EntityPathBase<S3Entity> {

    private static final long serialVersionUID = 922613853L;

    public static final QS3Entity s3Entity = new QS3Entity("s3Entity");

    public final StringPath publicAccessBlockConfiguration = createString("publicAccessBlockConfiguration");

    public final StringPath s3Id = createString("s3Id");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath serverSideEncryptionConfiguration = createString("serverSideEncryptionConfiguration");

    public QS3Entity(String variable) {
        super(S3Entity.class, forVariable(variable));
    }

    public QS3Entity(Path<? extends S3Entity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QS3Entity(PathMetadata metadata) {
        super(S3Entity.class, metadata);
    }

}

