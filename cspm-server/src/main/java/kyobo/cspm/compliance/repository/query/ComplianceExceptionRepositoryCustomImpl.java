package kyobo.cspm.compliance.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import kyobo.cspm.compliance.entity.QComplianceExceptionEntity;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

public class ComplianceExceptionRepositoryCustomImpl implements ComplianceExceptionRepositoryCustom{
    private  final JPAQueryFactory queryFactory;
    private final QComplianceExceptionEntity qComplianceExceptionEntity = QComplianceExceptionEntity.complianceExceptionEntity;

    public ComplianceExceptionRepositoryCustomImpl(@Qualifier("complianceJpaQueryFactory") JPAQueryFactory jpaQueryFactory) {
        queryFactory = jpaQueryFactory;
    }
    public Optional<ComplianceExceptionEntity> findByExceptionId(String resourceId, String policyTitle, String accountId, String accountName){
        if (resourceId == null || policyTitle == null || accountId == null || accountName == null) {
            return Optional.empty();
        }
        List<ComplianceExceptionEntity> complianceExceptionEntityList = queryFactory.selectFrom(qComplianceExceptionEntity)
                .where(qComplianceExceptionEntity.resourceId.eq(resourceId)
                        .and(qComplianceExceptionEntity.policyTitle.eq(policyTitle))
                        .and(qComplianceExceptionEntity.accountId.eq(accountId))
                        .and(qComplianceExceptionEntity.accountName.eq(accountName))
                )
                .orderBy(qComplianceExceptionEntity.exceptionTime.desc())
                .fetch();
        return complianceExceptionEntityList.isEmpty() ? Optional.empty() : Optional.of(complianceExceptionEntityList.get(0));
    }
}
