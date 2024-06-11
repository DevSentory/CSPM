package kyobo.cspm.describe.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.QAccountsEntity;
import kyobo.cspm.describe.entity.QDescribeEntity;
import kyobo.cspm.describe.entity.QPolicyEntity;
import kyobo.cspm.describe.policyDto.ComplianceDto;
import kyobo.cspm.describe.policyDto.PolicyDto;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DescribeRepositoryCustomImpl implements DescribeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QDescribeEntity qDescribeEntity = QDescribeEntity.describeEntity;
    private final QAccountsEntity qAccountsEntity = QAccountsEntity.accountsEntity;
    private final QPolicyEntity qPolicyEntity = QPolicyEntity.policyEntity;


    @Override
    public List<DescribeEntity> findAllQueryDescription(DescribeFilterDto describeFilterDto) {

        if (describeFilterDto == null) {
            return queryFactory.selectFrom(qDescribeEntity).fetch();
        }


        BooleanBuilder builder = new BooleanBuilder();

        if (describeFilterDto.getClient() != null && !describeFilterDto.getClient().isEmpty())
            builder.and(qDescribeEntity.client.eq(describeFilterDto.getClient()));
        if (describeFilterDto.getAccountName() != null && !describeFilterDto.getAccountName().isEmpty())
            builder.and(qDescribeEntity.accountName.eq(describeFilterDto.getAccountName()));
        if (describeFilterDto.getAccountId() != null && !describeFilterDto.getAccountId().isEmpty())
            builder.and(qDescribeEntity.accountId.eq(describeFilterDto.getAccountId()));
        if (describeFilterDto.getService() != null && !describeFilterDto.getService().isEmpty())
            builder.and(qDescribeEntity.serviceGroup.eq(describeFilterDto.getService()));
        if(describeFilterDto.getResource() != null && !describeFilterDto.getResource().isEmpty()){
            if(describeFilterDto.getResource().equals("bucket")){
                builder.and(qDescribeEntity.resourceId.contains(describeFilterDto.getResource()));
            } else  {
                builder.and(qDescribeEntity.resourceId.startsWith(describeFilterDto.getResource()));
            }

        }

        String fromDate = describeFilterDto.getFromDate();
        String toDate = describeFilterDto.getToDate();
        if (fromDate != null && toDate != null && !toDate.isEmpty() && !fromDate.isEmpty()) {
            builder.and(qDescribeEntity.scanTime.between(fromDate, toDate));
        } else if (fromDate != null && !fromDate.isEmpty()) {
            builder.and(qDescribeEntity.scanTime.goe(fromDate));
        } else if (toDate != null && !toDate.isEmpty()) {
            builder.and(qDescribeEntity.scanTime.loe(toDate));
        }

        if (describeFilterDto.getSearchDate() != null && !describeFilterDto.getSearchDate().isEmpty()) {
            String searchData = describeFilterDto.getSearchDate();
            BooleanExpression likeExpression = qDescribeEntity.client.like("%" + searchData + "%")
                    .or(qDescribeEntity.accountName.contains(searchData))
                    .or(qDescribeEntity.accountId.contains(searchData))
                    .or(qDescribeEntity.serviceGroup.contains(searchData))
                    .or(qDescribeEntity.scanTime.contains(searchData))
                    .or(qDescribeEntity.client.contains(searchData))
                    .or(qDescribeEntity.createTime.contains(searchData))
                    .or(qDescribeEntity.describeResult.contains(searchData))
                    .or(qDescribeEntity.tag.contains(searchData))
                    .or(qDescribeEntity.resourceId.contains(searchData));
            builder.and(likeExpression);
        }
        return queryFactory.selectFrom(qDescribeEntity).where(builder).orderBy(qDescribeEntity.scanTime.desc()).fetch();
    }

    /**
     * [2024-05-22 : 승근 작성]
     * task : policyDtoList의 담긴 resourceId, scanTime, policyId 값을 가지고 complianceDto list로 반환
     *
     * @param policyDtoList : 취약점 조회 후 나온 결과의 resourceId, scanTime, policyId를 가지고 있는 리스트
     * @return : complianceDto에 담아서 List로 반환
     */
    @Override
    public List<ComplianceDto> findPolicyCompliance(List<PolicyDto> policyDtoList, String accountId, String accountName) {
        List<ComplianceDto> complianceDtoList = new ArrayList<>();
        if (policyDtoList == null) {
            return complianceDtoList;
        }

        for (PolicyDto policyDtoIndex : policyDtoList) {
            List<ComplianceDto> complianceDtoListResult = queryFactory.select(
                            Projections.constructor(ComplianceDto.class,
                                    qDescribeEntity.resourceId,
                                    qDescribeEntity.scanTime,
                                    qDescribeEntity.serviceGroup,
                                    qPolicyEntity.policyType,
                                    qPolicyEntity.policyTitle,
                                    qPolicyEntity.policySeverity,
                                    qPolicyEntity.policyDescription,
                                    qPolicyEntity.policyResponse,
                                    qPolicyEntity.policyCompliance,
                                    qAccountsEntity.code,
                                    qAccountsEntity.accountName,
                                    qAccountsEntity.client,
                                    qAccountsEntity.accountId))
                    .distinct().from(qDescribeEntity)
                    .leftJoin(qAccountsEntity).on(qDescribeEntity.accountId.eq(qAccountsEntity.accountId).and(qDescribeEntity.accountName.eq(accountName)))
                    .join(qPolicyEntity).on(qPolicyEntity.id.eq(policyDtoIndex.getPolicyId()))
                    .where(qDescribeEntity.scanTime.eq(policyDtoIndex.getScanTime()).and(qDescribeEntity.resourceId.eq(policyDtoIndex.getResourceId())))
                    .fetch();

            complianceDtoList.addAll(complianceDtoListResult);
        }
        return complianceDtoList;
    }

    @Override
    public Optional<List<String>> findServiceGroup() {
        List<String> serviceGroupList = queryFactory.select(qDescribeEntity.serviceGroup).distinct().from(qDescribeEntity).fetch();
        return Optional.ofNullable(serviceGroupList);
    }

    @Override
    public Optional<List<String>> findResourceJson(String resourceId, String scanTime, String accountId, String accountName) {
        if (resourceId == null || scanTime == null || accountId == null || accountName == null) {
            return Optional.empty();
        }
        List<String> resourceJson = queryFactory.select(qDescribeEntity.resourceJson).from(qDescribeEntity)
                .where(qDescribeEntity.resourceId.eq(resourceId).and(qDescribeEntity.scanTime.eq(scanTime)).and(qDescribeEntity.accountId.eq(accountId)).and(qDescribeEntity.accountName.eq(accountName))).fetch();

        return resourceJson.isEmpty() ? Optional.empty() : Optional.ofNullable(resourceJson);
    }
}