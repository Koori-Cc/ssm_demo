package com.zk.function.annotation.myAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 14:23
 * @description
 */
@Target(ElementType.METHOD)        //说明该注解只能作用在方法上
@Retention(RetentionPolicy.RUNTIME)   //该注解不会被丢弃
public @interface FirstAnnotation {

    public enum Priority {LOW, MEDIUM, HIGH}

    public enum Status {STARTED, NOT_STARTED}

    public String author() default "umi";

    public Priority priority() default Priority.LOW;

    public Status status() default Status.NOT_STARTED;
}
