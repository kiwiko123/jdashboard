package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

import java.util.Objects;

public class PackageConfigurationScopeLevelResolver extends AbstractConfigurationScopeLevelResolver {


    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        String injectingConfigurationPackageName = input.getInjectingConfigurationClass().getPackageName();
        String injectedClassPackageName = input.getInjectedClass().getPackageName();

        return injectedClassPackageName.startsWith(injectingConfigurationPackageName);
    }

    @Override
    protected void onViolationDetected(ResolveScopeLevelInput input) throws ConfigurationScopeViolationException {
        throwDefaultViolationException(input, ConfigurationScopeLevel.PACKAGE);
    }
}
