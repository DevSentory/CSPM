package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.SubnetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubnetRepository extends JpaRepository<SubnetEntity, String> {
}