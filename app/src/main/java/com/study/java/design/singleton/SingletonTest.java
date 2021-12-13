package com.study.java.design.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTest {

    public static void main(String[] args) throws ClassNotFoundException {
        testSingleton3();
    }


    /**
     * 私有化的构造函数可以通过反射实例化多个对象
     */
    public static void testSingleton1() throws ClassNotFoundException {
        Singleton1 singleton1 = Singleton1.getInstanceD();
        singleton1.print();

        Class clazz = Class.forName("com.study.java.design.singleton.Singleton1");
        Singleton1 singleton11 = null;
        try {
            Constructor[] constructors = clazz.getDeclaredConstructors();
            for (Constructor cc : constructors) {
                System.out.println("---------cc = " + cc);
            }

//            Constructor constructor = constructors[0];
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton11 = (Singleton1) constructor.newInstance();
            singleton11.print();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println("---------singleton1 = " + singleton1);
        System.out.println("---------singleton11 = " + singleton11);
    }

    public static void testSingleton3() throws ClassNotFoundException {
        Singleton3.Single single = Singleton3.Single.SINGLE;
        single.print();

        Class clazz = Singleton3.Single.class;
        Singleton1 singleton = null;
        try {
            Constructor[] constructors = clazz.getDeclaredConstructors();
            for (Constructor cc : constructors) {
                System.out.println("---------cc = " + cc);
            }

            Constructor constructor = constructors[0];
//            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton = (Singleton1) constructor.newInstance("SINGLE", 0);
            singleton.print();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } /*catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/

        System.out.println("---------single = " + single);
        System.out.println("---------singleton = " + singleton);
    }
}
