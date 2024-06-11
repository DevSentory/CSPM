package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, String> {
}