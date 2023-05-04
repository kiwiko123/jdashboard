package com.kiwiko.jdashboard.featureflags.service;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.featureflags.service.internal.FeatureFlagUserAssociationEntityMapper;
import com.kiwiko.jdashboard.featureflags.service.internal.FeatureFlagUserAssociationEntityService;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagUserAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.featureflags.service.internal.FeatureFlagEntityMapper;
import com.kiwiko.jdashboard.featureflags.service.internal.data.FeatureFlagEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class FeatureFlagInternalConfiguration {

    @Bean
    public FeatureFlagEntityDataFetcher featureFlagEntityDAO() {
        return new FeatureFlagEntityDataFetcher();
    }

    @Bean
    public FeatureFlagEntityMapper featureFlagEntityMapper() {
        return new FeatureFlagEntityMapper();
    }

    @Bean
    public FeatureFlagUserAssociationEntityDataFetcher featureFlagUserAssociationEntityDataFetcher() {
        return new FeatureFlagUserAssociationEntityDataFetcher();
    }

    @Bean
    public FeatureFlagUserAssociationEntityMapper featureFlagUserAssociationEntityMapper() {
        return new FeatureFlagUserAssociationEntityMapper();
    }

    @Bean
    @ConfiguredBy(PersistenceServicesCrudConfiguration.class)
    public FeatureFlagUserAssociationEntityService featureFlagUserAssociationEntityService(
            FeatureFlagUserAssociationEntityDataFetcher featureFlagUserAssociationEntityDataFetcher,
            FeatureFlagUserAssociationEntityMapper featureFlagUserAssociationEntityMapper,
            TransactionProvider transactionProvider) {
        return new FeatureFlagUserAssociationEntityService(
                featureFlagUserAssociationEntityDataFetcher,
                featureFlagUserAssociationEntityMapper,
                transactionProvider);
    }
}
