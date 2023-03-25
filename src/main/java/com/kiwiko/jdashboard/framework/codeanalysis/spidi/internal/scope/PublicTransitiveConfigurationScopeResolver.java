package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;

public class PublicTransitiveConfigurationScopeResolver extends AbstractTransitiveConfigurationScopeResolver {
    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PUBLIC;
    }

    @Override
    protected boolean isValid(ResolveTransitiveConfigurationScopeInput resolveTransitiveConfigurationScopeInput) {
        return true;
    }
}
