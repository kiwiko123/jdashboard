package com.kiwiko.jdashboard.framework.datasources.frameworkinternal;

import com.kiwiko.jdashboard.framework.datasources.DataSourceBeanNames;
import com.kiwiko.jdashboard.framework.datasources.EntityPackagePaths;
import com.kiwiko.jdashboard.framework.datasources.api.JdashboardDataSources;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class FrameworkInternalDatasourceConfiguration {
    private static final String ENTITY_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory_frameworkInternal";

    @Inject private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = JdashboardDataSources.FRAMEWORK_INTERNAL)
    @ConfigurationProperties(prefix = "spring.datasource.framework-internal")
    public DataSource frameworkInternalDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public EntityManagerFactory frameworkInternalEntityManagerFactory(@Qualifier(JdashboardDataSources.FRAMEWORK_INTERNAL) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPersistenceUnitName(FrameworkInternalDatasourceConstants.PERSISTENCE_UNIT_NAME);
        entityManagerFactory.setPackagesToScan(EntityPackagePaths.FRAMEWORK_INTERNAL_DATASOURCE_PACKAGE_PATHS.toArray(new String[0]));
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean(name = DataSourceBeanNames.TRANSACTION_MANAGER_FRAMEWORK_INTERNAL)
    public PlatformTransactionManager frameworkInternalDataSourceTransactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_BEAN_NAME) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public FrameworkInternalEntityManagerProvider frameworkInternalEntityManagerProvider() {
        return new FrameworkInternalEntityManagerProvider();
    }
}
