package kyobo.cspm.describe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDescribeEntity is a Querydsl query type for DescribeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDescribeEntity extends EntityPathBase<DescribeEntity> {

    private static final long serialVersionUID = 638396343L;

    public static final QDescribeEntity describeEntity = new QDescribeEntity("describeEntity");

    public final StringPath accountId = createString("accountId");

    public final StringPath accountName = createString("accountName");

    public final StringPath client = createString("client");

    public final StringPath createTime = createString("createTime");

    public final StringPath describeResult = createString("describeResult");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath resourceId = createString("resourceId");

    public final StringPath resourceJson = createString("resourceJson");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath serviceGroup = createString("serviceGroup");

    public final StringPath tag = createString("tag");

    public QDescribeEntity(String variable) {
        super(DescribeEntity.class, forVariable(variable));
    }

    public QDescribeEntity(Path<? extends DescribeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDescribeEntity(PathMetadata metadata) {
        super(DescribeEntity.class, metadata);
    }

}

