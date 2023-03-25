package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;

public class PublicConfigurationScopeLevelResolver extends AbstractConfigurationScopeLevelResolver {


    @Override
    protected boolean isValid(ResolveScopeLevelInput input) {
        return true;
    }

    @Override
    protected void onViolationDetected(ResolveScopeLevelInput input) throws ConfigurationScopeViolationException {
        throwDefaultViolationException(input, ConfigurationScopeLevel.PUBLIC);
    }
}
