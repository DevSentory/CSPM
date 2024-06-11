package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.InstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstanceRepository extends JpaRepository<InstanceEntity, String> {
    Optional<InstanceEntity> findAllByMonitoringEquals(String monitoring);
}