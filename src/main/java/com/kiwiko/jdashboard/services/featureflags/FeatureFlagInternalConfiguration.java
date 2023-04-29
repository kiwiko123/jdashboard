package com.kiwiko.jdashboard.services.featureflags;

import com.kiwiko.jdashboard.services.featureflags.internal.FeatureFlagUserAssociationEntityMapper;
import com.kiwiko.jdashboard.services.featureflags.internal.FeatureFlagUserAssociationEntityService;
import com.kiwiko.jdashboard.services.featureflags.internal.data.FeatureFlagUserAssociationEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.services.featureflags.internal.FeatureFlagEntityMapper;
import com.kiwiko.jdashboard.services.featureflags.internal.data.FeatureFlagEntityDataFetcher;
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
    public FeatureFlagUserAssociationEntityService featureFlagUserAssociationEntityService() {
        return new FeatureFlagUserAssociationEntityService();
    }
}
