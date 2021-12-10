package com.study.java.thread;

import com.study.java.TestABCD;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Bank {

    private static ThreadLocal<Integer> account = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 100;
        }
    };

    public void save(int money) {
        account.set(account.get() + money);
    }

    public int getAccount() {
        return account.get();
    }

    public static void main(String[] args) {
        String ss;
        Bank bank = new Bank();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    bank.save(10);
                }
            }
        }, "T1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    bank.getAccount();
                }
            }
        }, "T1");

        t1.start();
        t2.start();
    }
}
