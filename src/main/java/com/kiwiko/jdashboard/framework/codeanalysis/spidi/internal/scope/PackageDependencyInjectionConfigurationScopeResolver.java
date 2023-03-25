package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;

public class PackageDependencyInjectionConfigurationScopeResolver extends AbstractDependencyInjectionConfigurationScopeResolver {

    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PACKAGE;
    }

    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        String injectingConfigurationPackageName = input.getInjectingConfigurationClass().getPackageName();
        String injectedClassPackageName = input.getInjectedClass().getPackageName();

        return injectedClassPackageName.startsWith(injectingConfigurationPackageName);
    }
}
