package com.study.java.reference;

import java.lang.ref.WeakReference;

public class TestWeakReference {
    public static void main(String[] args) {
        Car car = new Car(22000, "black");
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