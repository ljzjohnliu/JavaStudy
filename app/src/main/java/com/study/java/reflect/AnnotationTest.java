package com.study.java.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationTest {

    public static void main(String[] args) {
        try {
//            testAnnotation();
            testAnnotation(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();  //插入断点
    }

    //在给Person类对象的age赋值时，是感觉不到注解的存在的
    public static void testAnnotation() throws Exception{
        Person person = new Person();
        person.setAge(10);
        System.out.println("age is : " + person.getAge());
    }

    public static void testAnnotation(int val) throws Exception{
        Class clazz = Class.forName("com.study.java.reflect.Person");
        Object obj = clazz.newInstance();

        Method method = clazz.getDeclaredMethod("setAge", int.class);
        Method method2 = clazz.getDeclaredMethod("setName", String.class);

        System.out.println("setAge isAnnotationPresent : " + method.isAnnotationPresent(AgeValidator.class));
        System.out.println("setName isAnnotationPresent : " + method2.isAnnotationPresent(AgeValidator.class));
        Annotation annotation = method.getAnnotation(AgeValidator.class);
        System.out.println("annotation is : " + annotation);
        System.out.println("annotation 222 is : " + annotation.annotationType());
        if (annotation != null && annotation instanceof AgeValidator) {
            AgeValidator ageValidator = (AgeValidator) annotation;
            if(val < ageValidator.min() || val > ageValidator.max()){
                throw new RuntimeException("年龄非法");
            }
        }
        method.invoke(obj, 20);
        System.out.println("age is : " + ((Person)obj).getAge());
    }
}
