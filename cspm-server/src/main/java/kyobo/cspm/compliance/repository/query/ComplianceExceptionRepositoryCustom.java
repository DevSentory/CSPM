package kyobo.cspm.compliance.repository.query;

import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplianceExceptionRepositoryCustom {
    Optional<ComplianceExceptionEntity> findByExceptionId(String resourceId, String policyTitle, String accountId, String accountName);
}
