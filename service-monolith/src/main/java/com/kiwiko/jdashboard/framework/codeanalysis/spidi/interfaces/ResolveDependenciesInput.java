package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;

@Builder
@Getter
public class ResolveDependenciesInput {
    private final @Nonnull String rootPackagePath;

    @Builder.Default
    private final DependencyErrorActionLevel dependencyErrorActionLevel = DependencyErrorActionLevel.WARN;
}
