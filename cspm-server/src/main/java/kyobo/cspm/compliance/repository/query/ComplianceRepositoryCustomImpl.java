package kyobo.cspm.compliance.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kyobo.cspm.compliance.dto.ComplianceRequestDto;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.entity.QComplianceEntity;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

public class ComplianceRepositoryCustomImpl implements ComplianceRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QComplianceEntity qComplianceEntity = QComplianceEntity.complianceEntity;

    public ComplianceRepositoryCustomImpl(@Qualifier("complianceJpaQueryFactory") JPAQueryFactory jpaQueryFactory) {
        queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ComplianceEntity> findQueryCompliance(ComplianceRequestDto complianceDescribeDto) {

        if (complianceDescribeDto == null) {
            return queryFactory.select(qComplianceEntity).from(qComplianceEntity).fetch();
        }

        BooleanBuilder builder = new BooleanBuilder();

        if (complianceDescribeDto.getClient() != null && !complianceDescribeDto.getClient().isEmpty())
            builder.and(qComplianceEntity.client.eq(complianceDescribeDto.getClient()));

        if (complianceDescribeDto.getAccountName() != null && !complianceDescribeDto.getAccountName().isEmpty())
            builder.and(qComplianceEntity.accountName.eq(complianceDescribeDto.getAccountName()));
        if (complianceDescribeDto.getAccountId() != null && !complianceDescribeDto.getAccountId().isEmpty())
            builder.and(qComplianceEntity.accountId.eq(complianceDescribeDto.getAccountId()));
        if (complianceDescribeDto.getPolicySeverity() != null && !complianceDescribeDto.getPolicySeverity().isEmpty())
            builder.and(qComplianceEntity.policySeverity.contains(complianceDescribeDto.getPolicySeverity()));
        if (complianceDescribeDto.getVulnerabilityStatus() != null && !complianceDescribeDto.getVulnerabilityStatus().isEmpty())
            builder.and(qComplianceEntity.vulnerabilityStatus.contains(complianceDescribeDto.getVulnerabilityStatus()));

        String fromDate = complianceDescribeDto.getFromDate();
        String toDate = complianceDescribeDto.getToDate();
        if (fromDate != null && toDate != null && !toDate.isEmpty() && !fromDate.isEmpty()) {
            builder.and(qComplianceEntity.scanTime.between(fromDate, toDate));
        } else if (fromDate != null && !fromDate.isEmpty()) {
            builder.and(qComplianceEntity.scanTime.goe(fromDate));
        } else if (toDate != null && !toDate.isEmpty()) {
            builder.and(qComplianceEntity.scanTime.loe(toDate));
        }

        if (complianceDescribeDto.getSearchData() != null && !complianceDescribeDto.getSearchData().isEmpty()) {
            String searchData = complianceDescribeDto.getSearchData();
            BooleanExpression likeExpression =
                    qComplianceEntity.rid.contains(searchData)
                            .or(qComplianceEntity.scanTime.contains(searchData))
                            .or(qComplianceEntity.vulnerabilityStatus.contains(searchData))
                            .or(qComplianceEntity.accountName.contains(searchData))
                            .or(qComplianceEntity.accountId.contains(searchData))
                            .or(qComplianceEntity.resourceId.contains(searchData))
                            .or(qComplianceEntity.policySeverity.contains(searchData))
                            .or(qComplianceEntity.policyTitle.contains(searchData))
                            .or(qComplianceEntity.service.contains(searchData))
                            .or(qComplianceEntity.policyCompliance.contains(searchData));
            builder.and(likeExpression);
        }
        return queryFactory.select(qComplianceEntity).from(qComplianceEntity).where(builder).orderBy(qComplianceEntity.scanTime.desc()).fetch();
    }

    @Override
    public Optional<ComplianceEntity> findByDetailResourceId(String resourceId, String scanTime, String accountId, String accountName) {
        if (resourceId == null || scanTime == null || accountId == null || accountName == null) {
            return Optional.empty();
        }

        List<ComplianceEntity> complianceEntityList = queryFactory.selectFrom(qComplianceEntity)
                .where(qComplianceEntity.resourceId.eq(resourceId)
                        .and(qComplianceEntity.scanTime.eq(scanTime))
                        .and(qComplianceEntity.accountId.eq(accountId))
                        .and(qComplianceEntity.accountName.eq(accountName)))
                .fetch();

        // 첫 번째 요소를 Optional로 감싸서 반환
        return complianceEntityList.isEmpty() ? Optional.empty() : Optional.of(complianceEntityList.get(0));
    }

    @Override
    public Optional<ComplianceEntity> findByExceptionSoruce(String resourceId, String policyTitle, String accountId, String accountName){
        if (resourceId == null || policyTitle == null || accountId == null || accountName == null) {
            return Optional.empty();
        }
        List<ComplianceEntity> complianceEntityList = queryFactory.selectFrom(qComplianceEntity)
                .where(qComplianceEntity.resourceId.eq(resourceId)
                        .and(qComplianceEntity.policyTitle.eq(policyTitle))
                        .and(qComplianceEntity.accountId.eq(accountId))
                        .and(qComplianceEntity.accountName.eq(accountName)))
                .fetch();
        return complianceEntityList.isEmpty() ? Optional.empty() : Optional.of(complianceEntityList.get(0));
    }

}
