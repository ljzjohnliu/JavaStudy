package com.study.java.design.mediator;


import java.util.ArrayList;
import java.util.List;

//简单单例中介者
public class SimpleMediator {
    private static SimpleMediator smd = new SimpleMediator();
    private List<SimpleColleague> colleagues = new ArrayList<SimpleColleague>();

    public SimpleMediator() {

    }

    public static SimpleMediator getMedium() {
        return smd;
    }

    public void register(SimpleColleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
        }
    }

    public void relay(SimpleColleague scl) {
        for (SimpleColleague colleague : colleagues) {
            if (!colleague.equals(scl)) {
                colleague.receive();
            }
        }
    }
}
