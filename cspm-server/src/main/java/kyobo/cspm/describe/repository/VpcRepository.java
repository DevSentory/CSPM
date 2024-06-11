package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.VpcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VpcRepository extends JpaRepository<VpcEntity, String> {
}