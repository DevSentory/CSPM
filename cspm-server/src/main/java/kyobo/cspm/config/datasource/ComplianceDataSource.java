package kyobo.cspm.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        //entityManagerFactoryRef : 해당 EntityManagerFactory를 사용
        entityManagerFactoryRef = "complianceEntityManagerFactory",
        basePackages = "kyobo.cspm.compliance.repository"
)
public class ComplianceDataSource {

    @Bean(name = "DataSource_Compliance")
    @ConfigurationProperties(prefix = "spring.cspm.compliance.datasource")
    public DataSource complianceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "complianceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean complianceEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("DataSource_Compliance") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("kyobo.cspm.compliance.entity")
                .persistenceUnit("compliance")
                .build();
    }


    @Bean(name = "complianceTransactionManager")
    public PlatformTransactionManager complianceTransactionManager(
            @Qualifier("complianceEntityManagerFactory") EntityManagerFactory complianceEntityManagerFactory
    ) {
        return new JpaTransactionManager(complianceEntityManagerFactory);
    }
}