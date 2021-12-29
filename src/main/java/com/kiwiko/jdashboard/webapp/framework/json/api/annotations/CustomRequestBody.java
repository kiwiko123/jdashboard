package com.kiwiko.jdashboard.webapp.framework.json.api.annotations;

import com.kiwiko.jdashboard.webapp.framework.json.api.CustomRequestBodySerializationStrategy;
import com.kiwiko.jdashboard.webapp.framework.json.impl.DefaultRequestBodySerializationStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRequestBody {

    Class<? extends CustomRequestBodySerializationStrategy> strategy() default DefaultRequestBodySerializationStrategy.class;
}
