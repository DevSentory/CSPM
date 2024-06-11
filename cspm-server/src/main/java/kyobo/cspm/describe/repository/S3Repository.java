package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.describe.S3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3Repository extends JpaRepository<S3Entity, String> {
}