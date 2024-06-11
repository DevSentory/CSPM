package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.ENIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ENIRepository extends JpaRepository<ENIEntity, String> {
}