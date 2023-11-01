package com.kiwiko.jdashboard.framework.jobscheduler.api;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface JobFunction {

    @Nonnull JobFunctionOutput run(@Nonnull JobFunctionInput input) throws Exception;
}
