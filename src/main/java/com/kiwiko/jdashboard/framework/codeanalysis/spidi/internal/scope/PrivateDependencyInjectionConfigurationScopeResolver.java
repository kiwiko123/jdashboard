package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;

import java.util.Objects;

public class PrivateDependencyInjectionConfigurationScopeResolver extends AbstractDependencyInjectionConfigurationScopeResolver {


    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PRIVATE;
    }

    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        String injectingConfigurationClassPackageName = input.getInjectingConfigurationClass().getPackageName();
        String injectedClassPackageName = input.getInjectedClass().getPackageName();
        String injectingClassPackageName = input.getInjectingClass().getPackageName();

        return Objects.equals(injectingConfigurationClassPackageName, injectedClassPackageName)
                && Objects.equals(injectingConfigurationClassPackageName, injectingClassPackageName);
    }
}
