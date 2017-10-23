package com.zk.function.annotation.entities;

import com.zk.function.annotation.myAnnotation.FirstAnnotation;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 14:41
 * @description
 */
public class FirstEntities {

    //自定义的注解
    @FirstAnnotation(priority = FirstAnnotation.Priority.HIGH, author = "koori", status = FirstAnnotation.Status.STARTED)
    public static void function01() {
        System.out.print("function01  执行了!");
    }

    //自定义的注解
    @FirstAnnotation(priority = FirstAnnotation.Priority.MEDIUM, author = "kotori", status = FirstAnnotation.Status.STARTED)
    public static void function02() {
        System.out.print("function02  执行了!");
    }

}
