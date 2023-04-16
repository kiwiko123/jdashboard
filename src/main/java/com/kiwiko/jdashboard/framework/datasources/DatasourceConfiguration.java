package com.kiwiko.jdashboard.framework.datasources;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatasourceConfiguration {

    @Inject private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = JdashboardDataSources.DEFAULT)
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "entityManagerFactory_default")
    @Primary
    public EntityManagerFactory defaultEntityManagerFactory(@Qualifier(JdashboardDataSources.DEFAULT) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPersistenceUnitName("default");
        entityManagerFactory.setPackagesToScan(EntityPackagePaths.DEFAULT_DATASOURCE_PACKAGE_PATHS.toArray(new String[0]));
        entityManagerFactory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "validate", "hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect"));
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean(name = DataSourceBeanNames.TRANSACTION_MANAGER_DEFAULT)
    @Primary
    public PlatformTransactionManager defaultDataSourceTransactionManager(@Qualifier("entityManagerFactory_default") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public DefaultEntityManagerProvider defaultEntityManagerProvider() {
        return new DefaultEntityManagerProvider();
    }
}
