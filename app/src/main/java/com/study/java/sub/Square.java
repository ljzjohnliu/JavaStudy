package com.study.java.sub;

import com.study.java.base.Shap;

/**
 * 在Java中有两种形式可以实现多态。继承和接口。
 *
 * 1、基于继承实现的多态
 * 2、基于接口实现的多态(就不赘述了)
 */
public class Square extends Shap {

    @Override
    public void draw() {
        System.out.println("draw square!");
    }

    public void draw(int length) {
        System.out.println("draw square with length : " + length);
    }
}
