package noctem.storeService;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "storeEntityManager",
        transactionManagerRef = "storeTransactionManager",
        basePackages = "noctem.storeService.store.domain.repository"
)
public class StoreDbConfig {
    private final Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-store")
    public DataSource storeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager storeTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(storeEntityManager().getObject());
        return transactionManager;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean storeEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setDataSource(storeDataSource());
        em.setPackagesToScan("noctem.storeService.store.domain.entity");

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", "noctem.storeService.global.common.SnakeCaseNamingStrategy");
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource-store.hbm2ddl.auto"));
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(properties);
        return em;
    }
}
