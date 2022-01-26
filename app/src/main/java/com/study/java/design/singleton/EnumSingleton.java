package com.study.java.design.singleton;

/**
 * 使用枚举的单例模式
 * 需要注意的一点是，这样的写法并不能保证序列化问题以及发射创建多实例问题
 */
public class EnumSingleton {
    private EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private EnumSingleton singleton;

        /**
         * 这里的构造函数会执行几次，编译完之后其实会把枚举Item声明成final static的常量，有多少这样的Item
         * 加载枚举类的时候 构造函数就会执行几次！
         * 因为这里只设计了一个Item，JVM会保证此方法绝对只调用一次
         * 其实枚举实现单例跟静态内部类是一样的都是利用了类加载机制
         */
        Singleton() {
            singleton = new EnumSingleton();
            System.out.println("---------EnumSingleton, Singleton-------");
        }

        public EnumSingleton getInstance() {
            return singleton;
        }
    }

    public static void main(String[] args) {
        EnumSingleton obj1 = EnumSingleton.getInstance();
        EnumSingleton obj2 = EnumSingleton.getInstance();
        //输出结果：obj1==obj2?true
        System.out.println("obj1 == obj2 is : " + (obj1 == obj2));
    }
}
