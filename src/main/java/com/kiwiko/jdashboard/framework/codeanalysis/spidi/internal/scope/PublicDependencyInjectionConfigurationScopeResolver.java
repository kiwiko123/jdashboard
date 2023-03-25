package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;

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
