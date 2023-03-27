package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

abstract class AbstractDependencyInjectionConfigurationScopeResolver extends AbstractConfigurationScopeResolver<ResolveScopeLevelInput> {

    @Override
    protected void throwDefaultViolationException(ResolveScopeLevelInput input, ConfigurationScopeLevel scopeLevel) throws ConfigurationScopeViolationException {
        String message = String.format(
                "%s cannot inject an instance of %s because it's wired by %s, which is restricted by scope visibility %s",
                input.getInjectingClass().getSimpleName(),
                input.getInjectedClass().getSimpleName(),
                input.getInjectedConfigurationClass().getSimpleName(),
                scopeLevel.name());
        throw new ConfigurationScopeViolationException(message);
    }
}
