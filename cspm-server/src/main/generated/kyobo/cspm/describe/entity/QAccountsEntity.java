package kyobo.cspm.describe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountsEntity is a Querydsl query type for AccountsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountsEntity extends EntityPathBase<AccountsEntity> {

    private static final long serialVersionUID = 397061266L;

    public static final QAccountsEntity accountsEntity = new QAccountsEntity("accountsEntity");

    public final StringPath accessKey = createString("accessKey");

    public final StringPath accountId = createString("accountId");

    public final StringPath accountName = createString("accountName");

    public final StringPath client = createString("client");

    public final StringPath code = createString("code");

    public final StringPath comment = createString("comment");

    public final StringPath describeResult = createString("describeResult");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastUpdateDescribeTime = createString("lastUpdateDescribeTime");

    public final StringPath region = createString("region");

    public final StringPath registerTime = createString("registerTime");

    public final StringPath secretKey = createString("secretKey");

    public QAccountsEntity(String variable) {
        super(AccountsEntity.class, forVariable(variable));
    }

    public QAccountsEntity(Path<? extends AccountsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountsEntity(PathMetadata metadata) {
        super(AccountsEntity.class, metadata);
    }

}

