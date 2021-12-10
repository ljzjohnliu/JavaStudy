package com.study.java.reflect;

public class Person {
    public String pubName;
    String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @AgeValidator(min = 18, max = 35)
    public void setAge(int age) {
        this.age = age;
    }

    private void testPrivate() {
        System.out.println("--------testPrivate-------");
    }

    //包含一个带参的构造器和一个不带参的构造器
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }
}