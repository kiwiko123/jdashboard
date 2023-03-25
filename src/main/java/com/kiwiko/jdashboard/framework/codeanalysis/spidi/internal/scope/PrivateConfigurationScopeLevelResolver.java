package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

import java.util.Objects;

public class PrivateConfigurationScopeLevelResolver extends AbstractConfigurationScopeLevelResolver {


    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        return Objects.equals(input.getInjectingConfigurationClass().getPackageName(), input.getInjectedClass().getPackageName());
    }

    @Override
    protected void onViolationDetected(ResolveScopeLevelInput input) throws ConfigurationScopeViolationException {
        throwDefaultViolationException(input, ConfigurationScopeLevel.PRIVATE);
    }
}
