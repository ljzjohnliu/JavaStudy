package com.study.java.singleton;

/**
 * 单例模式——枚举方式（推荐）
 * 创建枚举默认就是线程安全的，所以不需要担心double checked locking，而且还能防止反序列化导致重新创建新的对象。
 * 保证只有一个实例（即使使用反射机制也无法多次实例化一个枚举量）。
 */
public class Singleton3 {

    public static void main(String[] args) {
        Single single = Single.SINGLE;
        single.print();
    }

    enum Single {
        SINGLE;

        private Single() {

        }

        public void print() {
            System.out.println("hello world");
        }
    }
}
