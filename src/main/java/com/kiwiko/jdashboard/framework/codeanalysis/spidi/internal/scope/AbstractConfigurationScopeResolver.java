package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

abstract class AbstractConfigurationScopeResolver<Input> {

    protected abstract ConfigurationScopeLevel getConfigurationScopeLevel();

    protected abstract boolean isValid(Input input);

    protected abstract void throwDefaultViolationException(Input input, ConfigurationScopeLevel scopeLevel) throws ConfigurationScopeViolationException;

    protected void onViolationDetected(Input input) throws ConfigurationScopeViolationException {
        ConfigurationScopeLevel configurationScopeLevel = getConfigurationScopeLevel();
        throwDefaultViolationException(input, configurationScopeLevel);
    }

    public void resolve(Input input) throws ConfigurationScopeViolationException {
        if (!isValid(input)) {
            onViolationDetected(input);
        }
    }
}
