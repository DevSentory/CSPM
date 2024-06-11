package kyobo.cspm.compliance.repository;

import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.repository.query.ComplianceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceEntity, Long>, ComplianceRepositoryCustom {
    List<ComplianceEntity> findByVulnerabilityStatusNotAndAccountIdAndAccountName(String status, String accountId, String accountName);
}
