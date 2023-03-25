package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

abstract class AbstractConfigurationScopeLevelResolver {

    protected abstract boolean isValid(ResolveScopeLevelInput input);

    protected abstract void onViolationDetected(ResolveScopeLevelInput input) throws ConfigurationScopeViolationException;

    public void resolve(ResolveScopeLevelInput input) throws ConfigurationScopeViolationException {
        if (!isValid(input)) {
            onViolationDetected(input);
        }
    }

    protected void throwDefaultViolationException(ResolveScopeLevelInput input, ConfigurationScopeLevel scopeLevel) throws ConfigurationScopeViolationException {
        String message = String.format(
                "%s cannot inject an instance of %s because it's wired by configuration %s which has scope visibility %s",
                input.getInjectingClass().getName(),
                input.getInjectedClass().getName(),
                input.getInjectingConfigurationClass().getName(),
                scopeLevel.name());
        throw new ConfigurationScopeViolationException(message);
    }
}
