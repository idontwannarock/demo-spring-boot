package com.example.demospringboot.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * Version number of the api.
     * @return version number.
     */
    int value() default 1;
}
