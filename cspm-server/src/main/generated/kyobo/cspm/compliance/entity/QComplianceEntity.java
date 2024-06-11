package kyobo.cspm.compliance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComplianceEntity is a Querydsl query type for ComplianceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComplianceEntity extends EntityPathBase<ComplianceEntity> {

    private static final long serialVersionUID = 1094892503L;

    public static final QComplianceEntity complianceEntity = new QComplianceEntity("complianceEntity");

    public final StringPath accountId = createString("accountId");

    public final StringPath accountName = createString("accountName");

    public final StringPath client = createString("client");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath policyCompliance = createString("policyCompliance");

    public final StringPath policyDescription = createString("policyDescription");

    public final StringPath policyResponse = createString("policyResponse");

    public final StringPath policySeverity = createString("policySeverity");

    public final StringPath policyTitle = createString("policyTitle");

    public final StringPath policyType = createString("policyType");

    public final StringPath resourceId = createString("resourceId");

    public final StringPath rid = createString("rid");

    public final StringPath scanTime = createString("scanTime");

    public final StringPath service = createString("service");

    public final StringPath vulnerabilityStatus = createString("vulnerabilityStatus");

    public QComplianceEntity(String variable) {
        super(ComplianceEntity.class, forVariable(variable));
    }

    public QComplianceEntity(Path<? extends ComplianceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComplianceEntity(PathMetadata metadata) {
        super(ComplianceEntity.class, metadata);
    }

}

