package com.kiwiko.webapp.mvc.json.api.annotations;

import com.kiwiko.webapp.mvc.json.api.CustomRequestBodySerializationStrategy;
import com.kiwiko.webapp.mvc.json.impl.EasyRequestBodySerializationStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRequestBody {

    Class<? extends CustomRequestBodySerializationStrategy> strategy() default EasyRequestBodySerializationStrategy.class;
}
