package kyobo.cspm.describe.repository;

import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.repository.query.DescribeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DescribeRepository extends JpaRepository<DescribeEntity, Long>, DescribeRepositoryCustom {
}
