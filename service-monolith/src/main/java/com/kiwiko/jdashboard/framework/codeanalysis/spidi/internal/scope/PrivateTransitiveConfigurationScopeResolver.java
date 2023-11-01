package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;

import java.util.Objects;

public class PrivateTransitiveConfigurationScopeResolver extends AbstractTransitiveConfigurationScopeResolver {
    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PRIVATE;
    }

    @Override
    protected boolean isValid(ResolveTransitiveConfigurationScopeInput resolveTransitiveConfigurationScopeInput) {
        String subjectConfigurationPackageName = resolveTransitiveConfigurationScopeInput.getSubjectConfigurationClass().getPackageName();
        String transitiveConfigurationPackageName = resolveTransitiveConfigurationScopeInput.getTransitiveConfigurationClass().getPackageName();

        return Objects.equals(subjectConfigurationPackageName, transitiveConfigurationPackageName);
    }
}
