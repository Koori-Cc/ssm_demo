package com.zk.ssm_demo.utils;

/**
 * @author panbing@supcon.com
 * @create 2017/9/15 9:14
 * @description
 */

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormBean {
    String value() default "";

    boolean required() default true;

    String modelCode() default "";
}