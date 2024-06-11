package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.IGWEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGWRepository extends JpaRepository<IGWEntity, String> {
}