package com.study.java.design.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerSingleton implements Serializable {
    private volatile static SerSingleton uniqueInstance;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private SerSingleton() {
    }

    public static SerSingleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (SerSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SerSingleton();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     *  可以看出，序列化前后两个对象并不想等。为什么会出现这种问题呢？这个讲起来，又可以写一篇博客了，
     *  简单来说“任何一个readObject方法，不管是显式的还是默认的，它都会返回一个新建的实例，
     *  这个新建的实例不同于该类初始化时创建的实例。”当然，这个问题也是可以解决的，
     *  想详细了解的同学可以翻看《effective java》第77条：对于实例控制，枚举类型优于readResolve。
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerSingleton s = SerSingleton.getInstance();
        s.setContent("单例序列化");
        System.out.println("序列化前读取其中的内容：" + s.getContent());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SerSingleton.obj"));
        oos.writeObject(s);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("SerSingleton.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SerSingleton s1 = (SerSingleton) ois.readObject();
        ois.close();
        System.out.println(s + "\n" + s1);
        System.out.println("序列化后读取其中的内容：" + s1.getContent());
        System.out.println("序列化前后两个是否同一个：" + (s == s1));
    }

}
