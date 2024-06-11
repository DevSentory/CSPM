package kyobo.cspm.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class QueryDslConfig {
    @PersistenceContext  // entityManager를 빈으로 주입
    private EntityManager entityManager;

    @PersistenceContext(unitName = "compliance")
    private EntityManager complianceEntityManager;

    @Primary
    @Bean(name = "jpaQueryFactory")
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean(name = "complianceJpaQueryFactory")
    public JPAQueryFactory complianceJpaQueryFactory() {
        return new JPAQueryFactory(complianceEntityManager);
    }
}