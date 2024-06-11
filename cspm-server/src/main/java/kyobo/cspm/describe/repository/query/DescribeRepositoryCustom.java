package kyobo.cspm.describe.repository.query;

import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.policyDto.ComplianceDto;
import kyobo.cspm.describe.policyDto.PolicyDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DescribeRepositoryCustom {
    List<DescribeEntity> findAllQueryDescription(DescribeFilterDto describeFilterDto);

    List<ComplianceDto> findPolicyCompliance(List<PolicyDto> policyDtoList, String accountId, String accountName);

    Optional<List<String>> findServiceGroup();

    Optional<List<String>> findResourceJson(String resourceId, String scanTime, String accountId, String accountName);
}
