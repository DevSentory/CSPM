package kyobo.cspm.compliance.repository;

import kyobo.cspm.compliance.entity.ComplianceEntity;
import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import kyobo.cspm.compliance.repository.query.ComplianceExceptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplianceExceptionRepository extends JpaRepository<ComplianceExceptionEntity,Long>, ComplianceExceptionRepositoryCustom {
}
