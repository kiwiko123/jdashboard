package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationScope {
    ConfigurationScopeLevel value();
}
