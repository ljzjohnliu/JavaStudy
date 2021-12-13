package com.study.java.design.mediator;

public class SimpleConcreteColleague1 implements SimpleColleague {

    public SimpleConcreteColleague1() {
        SimpleMediator simpleMediator = SimpleMediator.getMedium();
        simpleMediator.register(this);
    }

    @Override
    public void receive() {
        System.out.println("具体同事类1：收到请求。");
    }

    @Override
    public void send() {
        SimpleMediator smd = SimpleMediator.getMedium();
        System.out.println("具体同事类1：发出请求...");
        smd.relay(this); //请中介者转发
    }
}
