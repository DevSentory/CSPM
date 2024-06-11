package kyobo.cspm.describe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPolicyEntity is a Querydsl query type for PolicyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPolicyEntity extends EntityPathBase<PolicyEntity> {

    private static final long serialVersionUID = 816489246L;

    public static final QPolicyEntity policyEntity = new QPolicyEntity("policyEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath policyCompliance = createString("policyCompliance");

    public final StringPath policyDescription = createString("policyDescription");

    public final StringPath policyPattern = createString("policyPattern");

    public final StringPath policyResponse = createString("policyResponse");

    public final StringPath policySeverity = createString("policySeverity");

    public final StringPath policyTitle = createString("policyTitle");

    public final StringPath policyType = createString("policyType");

    public QPolicyEntity(String variable) {
        super(PolicyEntity.class, forVariable(variable));
    }

    public QPolicyEntity(Path<? extends PolicyEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPolicyEntity(PathMetadata metadata) {
        super(PolicyEntity.class, metadata);
    }

}

