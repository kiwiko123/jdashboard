package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResolveTransitiveConfigurationScopeInput {
    private final Class<?> subjectConfigurationClass;
    private final Class<?> transitiveConfigurationClass;
}
