package com.study.java.reference;

import java.lang.ref.WeakReference;

public class TestWeakReference {

    private static Car car;
    public static void main(String[] args) {
        //局部变量在方法栈中过了调用时机，那么其指向的对象可以被回收
        //在一个方法的内部有一个强引用，这个引用保存在Java栈中，而真正的引用内容(Object)保存在Java堆中。
        //当这个方法运行完成后，就会退出方法栈，则引用对象的引用数为0，这个对象会被回收。
        Car car = new Car(22000, "black");
//        car = new Car(22000, "black");
        WeakReference weakCar = new WeakReference(car);
        int i = 0;
        while (true) {
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar.get().toString());
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }
}