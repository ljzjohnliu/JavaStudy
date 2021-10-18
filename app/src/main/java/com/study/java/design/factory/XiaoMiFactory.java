package com.study.java.design.factory;

public class XiaoMiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new MiPhone();
    }
}
