package com.study.java.design.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {

        MaotaiJiu maotaijiu = new MaotaiJiu();

        InvocationHandler jingxiao1 = new GuitaiA(maotaijiu);

        SellWine dynamicProxy = (SellWine) Proxy.newProxyInstance(MaotaiJiu.class.getClassLoader(),
                MaotaiJiu.class.getInterfaces(), jingxiao1);

        dynamicProxy.mainJiu();

        Furongwang furongwang = new Furongwang();
        InvocationHandler jingxiao2 = new GuitaiA(furongwang);
        SellCigarette dynamicProxy2 = (SellCigarette)Proxy.newProxyInstance(Furongwang.class.getClassLoader(),
                Furongwang.class.getInterfaces(), jingxiao2);

        dynamicProxy2.sell();
    }

}
