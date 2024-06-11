package kyobo.cspm.service.components;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 테이블에 데이터가 있는지 확인
        Integer rowCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM policy", Integer.class);

        if (rowCount != null && rowCount == 0) {
            // 테이블에 데이터가 없으면 초기화 SQL 실행
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("data.sql"));
            populator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        }
    }
}
