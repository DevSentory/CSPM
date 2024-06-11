package kyobo.cspm.compliance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComplianceExceptionEntity is a Querydsl query type for ComplianceExceptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComplianceExceptionEntity extends EntityPathBase<ComplianceExceptionEntity> {

    private static final long serialVersionUID = -1373650018L;

    public static final QComplianceExceptionEntity complianceExceptionEntity = new QComplianceExceptionEntity("complianceExceptionEntity");

    public final StringPath accountId = createString("accountId");

    public final StringPath accountName = createString("accountName");

    public final StringPath exceptionContent = createString("exceptionContent");

    public final StringPath exceptionHandler = createString("exceptionHandler");

    public final StringPath exceptionTime = createString("exceptionTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath policyTitle = createString("policyTitle");

    public final StringPath resourceId = createString("resourceId");

    public QComplianceExceptionEntity(String variable) {
        super(ComplianceExceptionEntity.class, forVariable(variable));
    }

    public QComplianceExceptionEntity(Path<? extends ComplianceExceptionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComplianceExceptionEntity(PathMetadata metadata) {
        super(ComplianceExceptionEntity.class, metadata);
    }

}

