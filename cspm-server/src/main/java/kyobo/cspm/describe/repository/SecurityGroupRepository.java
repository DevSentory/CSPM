package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.SecurityGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityGroupRepository extends JpaRepository<SecurityGroupEntity, String> {
}