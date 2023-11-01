package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;

public class PublicDependencyInjectionConfigurationScopeResolver extends AbstractDependencyInjectionConfigurationScopeResolver {

    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PUBLIC;
    }

    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        return true;
    }
}
