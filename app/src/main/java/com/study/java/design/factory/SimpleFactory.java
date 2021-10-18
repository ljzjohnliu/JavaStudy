package com.study.java.design.factory;

public class SimpleFactory {

    public static void main(String[] args) {
        PhoneFactory factory = new PhoneFactory();
        Phone miPhone = factory.makePhone("MiPhone");
        IPhone iPhone = (IPhone) factory.makePhone("iPhone");
    }
}
