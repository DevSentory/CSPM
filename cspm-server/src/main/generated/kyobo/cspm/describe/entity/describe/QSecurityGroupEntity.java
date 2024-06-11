package kyobo.cspm.describe.entity.describe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSecurityGroupEntity is a Querydsl query type for SecurityGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecurityGroupEntity extends EntityPathBase<SecurityGroupEntity> {

    private static final long serialVersionUID = 616618344L;

    public static final QSecurityGroupEntity securityGroupEntity = new QSecurityGroupEntity("securityGroupEntity");

    public final StringPath description = createString("description");

    public final StringPath groupId = createString("groupId");

    public final StringPath groupName = createString("groupName");

    public final ListPath<InstanceEntity, QInstanceEntity> instanceEntityList = this.<InstanceEntity, QInstanceEntity>createList("instanceEntityList", InstanceEntity.class, QInstanceEntity.class, PathInits.DIRECT2);

    public final StringPath ipPermissions = createString("ipPermissions");

    public final StringPath ipPermissionsEgress = createString("ipPermissionsEgress");

    public final StringPath ownerId = createString("ownerId");

    public final StringPath scanTime = createString("scanTime");

    public QSecurityGroupEntity(String variable) {
        super(SecurityGroupEntity.class, forVariable(variable));
    }

    public QSecurityGroupEntity(Path<? extends SecurityGroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSecurityGroupEntity(PathMetadata metadata) {
        super(SecurityGroupEntity.class, metadata);
    }

}

