package noctem.storeService;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "purchaseEntityManager",
        transactionManagerRef = "purchaseTransactionManager",
        basePackages = "noctem.storeService.purchase.domain.repository"
)
public class PurchaseDbConfig {
    private final Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-purchase")
    public DataSource purchaseDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager purchaseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(purchaseEntityManager().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean purchaseEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(purchaseDataSource());
        em.setPackagesToScan("noctem.storeService.purchase.domain.entity");

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", "noctem.storeService.global.common.SnakeCaseNamingStrategy");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(properties);
        return em;
    }
}
