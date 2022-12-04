package com.kiwiko.jdashboard.framework.datasources;

import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatasourceConfiguration implements JdashboardDependencyConfiguration {

    @Inject private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = JdashboardDataSources.DEFAULT)
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = JdashboardDataSources.FRAMEWORK_INTERNAL)
    @ConfigurationProperties(prefix = "spring.datasource.framework-internal")
    public DataSource frameworkInternalDataSource() {
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
        entityManagerFactory.setPackagesToScan("com.kiwiko.jdashboard");
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean(name = "entityManagerFactory_frameworkInternal")
    public EntityManagerFactory frameworkInternalEntityManagerFactory(@Qualifier(JdashboardDataSources.FRAMEWORK_INTERNAL) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPersistenceUnitName("framework-internal");
        entityManagerFactory.setPackagesToScan("com.kiwiko.jdashboard");
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean(name = DataSourceBeanNames.TRANSACTION_MANAGER_DEFAULT)
    @Primary
    public JpaTransactionManager defaultDataSourceTransactionManager(@Qualifier("entityManagerFactory_default") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL)
    public JpaTransactionManager frameworkInternalDataSourceTransactionManager(@Qualifier("entityManagerFactory_frameworkInternal") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
