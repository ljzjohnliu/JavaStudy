package com.study.java.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Demo {
    public static void main(String[] args) throws Exception {
        testSerialize();
    }

    public static void testSerialize() throws Exception {
        // 构造对象
        User user = new User();
        user.setId(1000);
        user.setName("韩梅梅");

        // 把对象序列化到文件
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/liujianzhang/work/user.txt"));
//        oos.writeObject(user);
//        oos.close();

        // 反序列化到内存
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/Users/liujianzhang/work/user.txt"));
        User userBack = (User) ois.readObject();
        System.out.println("read serializable user:id = " + userBack.getId() + ", name = " + userBack.getName());
        ois.close();
    }
}
