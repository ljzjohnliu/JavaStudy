package com.study.java.reflect;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    public static void main(String[] args) {
//        testNewInstance();
        try {
//            testClassLoader();
//            testMethod();
//            testField();
            testConstructor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();  //插入断点
    }

    public static void testNewInstance() {
        Class clazz = null;
        Person person = new Person();

        //1.得到Class对象
        clazz = Person.class;     //1.通过类名
        clazz = person.getClass();//2.通过对象名
        /*上面这个例子的意义不大，因为已经知道person类型是Person类，再这样写就没有必要了
         * 如果传进来是一个Object类，这种做法就是应该的*/
        Object obj = new Person();
        clazz = obj.getClass();
        //3.通过全类名(会抛出异常)
        //一般框架开发中这种用的比较多，因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例
        String className = "com.study.java.reflect.Person";
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Field[] fields = clazz.getDeclaredFields();

        try {
            Constructor constructor = clazz.getConstructor(String.class, int.class);
            Person pp = (Person) constructor.newInstance("ljz", 20);
            System.out.println("pp name = " + pp.getName() + ", age = " + pp.getAge());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void testClassLoader() throws ClassNotFoundException, FileNotFoundException {
        //1. 获取一个系统的类加载器(可以获取，当前这个类PeflectTest就是它加载的)
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        //2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
        classLoader = classLoader.getParent();
        System.out.println(classLoader);

        //3. 获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
        classLoader = classLoader.getParent();
        System.out.println(classLoader);

        //4. 测试当前类由哪个类加载器进行加载（系统类加载器）:
        classLoader = Class.forName("com.study.java.reflect.ReflectionTest").getClassLoader();
        System.out.println(classLoader);

        //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(classLoader);
    }

    public static void testMethod() throws Exception {
        Class clazz = null;
        clazz = Class.forName("com.study.java.reflect.Person");

        // 所有声明的方法，都可以获取到，且只获取当前类的方法
        Method[] allMethods = clazz.getDeclaredMethods();
        for (Method method : allMethods) {
            System.out.print(" " + method.getName());
        }
        System.out.println();
        //不能获取private方法,且获取从父类继承来的所有方法
        Method[] pubMethods = clazz.getMethods();
        for (Method method : pubMethods) {
            System.out.print(" " + method.getName());
        }
        System.out.println();
        Method getNameMethod = clazz.getDeclaredMethod("getName");
        System.out.print(" " + getNameMethod.getName());
        System.out.println();
        //  这样写是获取不到的，如果方法的参数类型是int型
        //  如果方法用于反射，那么要么int类型写成Integer： public void setAge(Integer age) {  }
        //  要么获取方法的参数写成int.class
        Method setAgeMethod = clazz.getDeclaredMethod("setAge", int.class);
        System.out.print(" " + setAgeMethod.getName());
        System.out.println();

        Object obj = clazz.newInstance();
        setAgeMethod.invoke(obj, 22);
        System.out.print(" age is : " + ((Person)obj).getAge());
        System.out.println();

        //如果一个方法是私有方法，第三步是可以获取到的，但是这一步却不能执行
        //私有方法的执行，必须在调用invoke之前加上一句method.setAccessible（true）;
        Method privateMethod = clazz.getDeclaredMethod("testPrivate");
        privateMethod.setAccessible(true);
        privateMethod.invoke(obj);
        System.out.println();
    }

    public static void testField() throws Exception {
        Class clazzP = Person.class;
        Class clazzS = Student.class;

        //1.1 获取所有字段 -- 字段数组
        //可以获取公用和私有的所有字段，但不能获取父类字段
        Field[] allFields = clazzP.getDeclaredFields();
        for (Field field : allFields) {
            System.out.print(" " + field.getName());
        }
        System.out.println();

        //可以获取公用所有字段，能获取父类字段
        Field[] pubFields = clazzP.getFields();
        for (Field field : pubFields) {
            System.out.print(" " + field.getName());
        }
        System.out.println();

        Field[] allFields1 = clazzS.getDeclaredFields();
        for (Field field : allFields1) {
            System.out.print(" " + field.getName());
        }
        System.out.println();

        Field[] pubFields2 = clazzS.getFields();
        for (Field field : pubFields2) {
            System.out.print(" " + field.getName());
        }
        System.out.println();

        //  1.2获取指定字段
        Field field = clazzP.getDeclaredField("name");
        System.out.println(field.getName());

        Person person = new Person("ABC",12);
        //2.使用字段
        //  2.1获取指定对象的指定字段的值
        Object val = field.get(person);
        System.out.println(val);

        //  2.2设置指定对象的指定对象Field值
        field.set(person, "DEF");
        System.out.println(person.getName());

        //  2.3如果字段是私有的，不管是读值还是写值，都必须先调用setAccessible（true）方法
        //     比如Person类中，字段name字段是公用的，age是私有的
        field = clazzP.getDeclaredField("age");
        field.setAccessible(true);
        System.out.println(field.get(person));
    }

    public static void testConstructor() throws Exception{
        String className = "com.study.java.reflect.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);
        //1. 获取 Constructor 对象
        //   1.1 获取全部
        Constructor<Person> [] constructors =
                (Constructor<Person>[]) Class.forName(className).getConstructors();

        for(Constructor<Person> constructor: constructors){
            System.out.println(constructor);
        }

        //  1.2获取某一个，需要参数列表
        Constructor<Person> constructor = clazz.getConstructor(String.class, int.class);
        System.out.println(constructor);

        //2. 调用构造器的 newInstance() 方法创建对象
        Object obj = constructor.newInstance("ljz", 36);
    }
}
