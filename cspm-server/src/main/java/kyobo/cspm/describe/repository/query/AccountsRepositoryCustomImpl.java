package kyobo.cspm.describe.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kyobo.cspm.describe.dto.ErrorLogDto;
import kyobo.cspm.describe.dto.ScanConfigFilterDto;
import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.describe.entity.QAccountsEntity;
import kyobo.cspm.describe.entity.QErrorLogEntity;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountsRepositoryCustomImpl implements AccountsRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QAccountsEntity qAccountsEntity = QAccountsEntity.accountsEntity;
    private final QErrorLogEntity qErrorLogEntity = QErrorLogEntity.errorLogEntity;


    @Override
    public List<AccountsEntity> findAllQueryDescription(ScanConfigFilterDto scanConfigFilterDto) {

        if (scanConfigFilterDto == null) {
            return queryFactory.selectFrom(qAccountsEntity).fetch();
        }

        BooleanBuilder builder = new BooleanBuilder();

        if(scanConfigFilterDto.getAccountName() != null && !scanConfigFilterDto.getAccountName().isEmpty()) {
            builder.and(qAccountsEntity.accountName.eq(scanConfigFilterDto.getAccountName()));
        }
        if(scanConfigFilterDto.getClient() != null && !scanConfigFilterDto.getClient().isEmpty()) {
            builder.and(qAccountsEntity.client.eq(scanConfigFilterDto.getClient()));
        }

        if(scanConfigFilterDto.getSearchData() != null && !scanConfigFilterDto.getSearchData().isEmpty()) {
            String searchData = scanConfigFilterDto.getSearchData();
            BooleanExpression likeExpression = qAccountsEntity.accountName.like("%" + searchData + "%")
                    .or(qAccountsEntity.client.like("%" + searchData + "%"))
                    .or(qAccountsEntity.accountId.like("%" + searchData + "%"))
                    .or(qAccountsEntity.describeResult.like("%" + searchData + "%"))
                    .or(qAccountsEntity.registerTime.like("%" + searchData + "%"))
                    .or(qAccountsEntity.lastUpdateDescribeTime.like("%" + searchData + "%"));
            builder.and(likeExpression);
        }

        return queryFactory.selectFrom(qAccountsEntity).where(builder).fetch();
    }

    @Override
    public Optional<ErrorLogDto> findErrorLogList(String accountId, String scamTime, String accountName) {
        List<ErrorLogDto> errorLogDtos = queryFactory
                .select(Projections.constructor(ErrorLogDto.class, qErrorLogEntity.exceptionCode, qErrorLogEntity.exceptionMsg, qErrorLogEntity.resource))
                .from(qErrorLogEntity)
                .where(qErrorLogEntity.accountId.eq(accountId).and(qErrorLogEntity.describeTime.eq(scamTime).and(qErrorLogEntity.accountName.eq(accountName))))
                .fetch();

        List<String> uniqueExceptionCodes = errorLogDtos.stream()
                .flatMap(dto -> dto.getExceptionCodes().stream())
                .distinct()
                .collect(Collectors.toList());

        List<String> uniqueExceptionMessages = errorLogDtos.stream()
                .flatMap(dto -> dto.getExceptionMessages().stream())
                .distinct()
                .collect(Collectors.toList());

        List<String> uniqueExceptionService = errorLogDtos.stream()
                .flatMap(dto -> dto.getExceptionService().stream())
                .distinct()
                .toList();


        return Optional.of(new ErrorLogDto(uniqueExceptionCodes, uniqueExceptionService, uniqueExceptionMessages));

    }
}
