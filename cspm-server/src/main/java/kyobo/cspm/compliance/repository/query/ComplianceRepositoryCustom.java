package kyobo.cspm.compliance.repository.query;

import kyobo.cspm.compliance.dto.ComplianceRequestDto;
import kyobo.cspm.compliance.entity.ComplianceEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplianceRepositoryCustom {
    List<ComplianceEntity> findQueryCompliance(ComplianceRequestDto complianceRequestDto);

    Optional<ComplianceEntity> findByDetailResourceId(String resourceId, String scanTime, String accountId, String accountName);

    //Exception 등록할 때, 어떤 취약점에 대한 것인지 찾기 위해 complianceDB에 resourceId, policyTitle, accountId, accountName으로 찾는다.
    Optional<ComplianceEntity> findByExceptionSoruce(String resourceId, String policyTitle, String accountId, String accountName);
}
