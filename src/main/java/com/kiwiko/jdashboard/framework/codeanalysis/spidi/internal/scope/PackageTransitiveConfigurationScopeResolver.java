package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;

import java.util.Objects;

public class PackageTransitiveConfigurationScopeResolver extends AbstractTransitiveConfigurationScopeResolver {
    @Override
    protected ConfigurationScopeLevel getConfigurationScopeLevel() {
        return ConfigurationScopeLevel.PACKAGE;
    }

    @Override
    protected boolean isValid(ResolveTransitiveConfigurationScopeInput resolveTransitiveConfigurationScopeInput) {
        String subjectConfigurationPackageName = resolveTransitiveConfigurationScopeInput.getSubjectConfigurationClass().getPackageName();
        String transitiveConfigurationPackageName = resolveTransitiveConfigurationScopeInput.getTransitiveConfigurationClass().getPackageName();

        return transitiveConfigurationPackageName.startsWith(subjectConfigurationPackageName);
    }
}
