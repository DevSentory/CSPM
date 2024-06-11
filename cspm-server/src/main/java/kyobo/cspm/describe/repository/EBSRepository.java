package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.EBSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EBSRepository extends JpaRepository<EBSEntity, String> {
}