package com.study.java.lock;

public class User {
    private String name;
    private Integer age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "User(name : " + name + ", age : " + age + ")";
    }
}
