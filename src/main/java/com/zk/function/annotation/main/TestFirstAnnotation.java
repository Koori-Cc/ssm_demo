package com.zk.function.annotation.main;

import com.zk.function.annotation.entities.FirstEntities;
import com.zk.function.annotation.myAnnotation.FirstAnnotation;

import java.lang.reflect.Method;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 14:27
 * @description 测试注解
 */
public class TestFirstAnnotation {

    public static void main(String args[]) {
        //获取字节码文件
        Class FirstClass = FirstEntities.class;
        //通过字节码文件获取方法
        for(Method method : FirstClass.getMethods()) {
            //通过方法获取注解
            FirstAnnotation annotation = (FirstAnnotation)method.getAnnotation(FirstAnnotation.class);
            if(annotation != null) {
                System.out.println(" Method Name : " + method.getName());
                System.out.println(" Author : " + annotation.author());
                System.out.println(" Priority : " + annotation.priority());
                System.out.println(" Status : " + annotation.status());
                System.out.println("---------------------------------------");
            }
        }
    }
}
