package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

abstract class AbstractTransitiveConfigurationScopeResolver extends AbstractConfigurationScopeResolver<ResolveTransitiveConfigurationScopeInput> {

    @Override
    protected void throwDefaultViolationException(ResolveTransitiveConfigurationScopeInput input, ConfigurationScopeLevel scopeLevel) throws ConfigurationScopeViolationException {
        String message = String.format(
                "%s cannot list %s as a transitive dependency because it is restricted by scope visibility %s",
                input.getSubjectConfigurationClass().getSimpleName(),
                input.getTransitiveConfigurationClass().getSimpleName(),
                scopeLevel.name());
        throw new ConfigurationScopeViolationException(message);
    }
}
