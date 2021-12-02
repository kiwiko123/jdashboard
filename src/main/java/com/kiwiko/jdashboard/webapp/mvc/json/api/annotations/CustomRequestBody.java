package com.kiwiko.jdashboard.webapp.mvc.json.api.annotations;

import com.kiwiko.jdashboard.webapp.mvc.json.api.CustomRequestBodySerializationStrategy;
import com.kiwiko.jdashboard.webapp.mvc.json.impl.DefaultRequestBodySerializationStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRequestBody {

    Class<? extends CustomRequestBodySerializationStrategy> strategy() default DefaultRequestBodySerializationStrategy.class;
}
