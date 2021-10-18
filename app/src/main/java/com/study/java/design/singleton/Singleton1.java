package com.study.java.design.singleton;

/**
 * 单例模式——懒汉式
 * 懒汉式单例模式在第一次调用的时候进行实例化。
 * 是否 Lazy 初始化：是
 */
public class Singleton1 {

    private static volatile Singleton1 instance = null;

    private Singleton1() {

    }

    public void print() {
        System.out.println("hello world");
    }

    /**
     * 1、适用于单线程环境（不推荐）
     */
    public static Singleton1 getInstanceA() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }

    /**
     * 2、适用于多线程环境，但效率不高（不推荐）
     */
    public static synchronized Singleton1 getInstanceB() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }

    /**
     * 3、双重检查加锁（推荐）
     * 为了在多线程环境下，不影响程序的性能，不让线程每次调用getInstanceC()方法时都加锁，
     * 而只是在实例未被创建时再加锁，在加锁处理里面还需要判断一次实例是否已存在。
     */
    public static Singleton1 getInstanceC() {
        if (instance == null) {
            // 先判断实例是否存在，若不存在再对类对象进行加锁处理
            synchronized (Singleton1.class) {
                if (instance == null) {
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }

    /**
     * 静态内部类方式（推荐）
     * 加载一个类时，其内部类不会同时被加载。一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生。
     * 由于在调用 Singleton1.getInstanceD() 的时候，才会对单例进行初始化，
     * 而且通过反射，是不能从外部类获取内部类的属性的；由于静态内部类的特性，只有在其被第一次引用的时候才会被加载，所以可以保证其线程安全性。
     * 总结：
     * 优势：兼顾了懒汉模式的内存优化（使用时才初始化）以及饿汉模式的安全性（不会被反射入侵）。
     * 劣势：需要两个类去做到这一点，虽然不会创建静态内部类的对象，但是其 Class 对象还是会被创建，而且是属于永久带的对象。
     *
     * 为何静态内部类可以实现懒加载?
     * 其实静态内部类与普通类除了在访问时需要使用外部类做入口外,其他地方与普通类没有任何区别,因此只要不加载静态内部类,其静态常量就不会被初始化
     */
    private static class StaticSingletonHolder {
        private static final Singleton1 instance = new Singleton1();
    }

    public static Singleton1 getInstanceD() {
        return StaticSingletonHolder.instance;
    }

    public static void main(String[] args) {
        Singleton1.getInstanceA().print();
        Singleton1.getInstanceB().print();
        Singleton1.getInstanceC().print();
        Singleton1.getInstanceD().print();
    }
}
