package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResolveScopeLevelInput {
    private final Class<?> injectingClass;
    private final Class<?> injectingConfigurationClass;
    private final Class<?> injectedClass;
    private final Class<?> injectedConfigurationClass;
}
