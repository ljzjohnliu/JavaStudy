package com.study.java.leecode.base;

import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {
        //初始化
        Stack stack = new Stack<>();
        //判断是否为空 empty方法是Stack中的成员方法,isEmpty是Vector中的方法，Stack继承了Vector
        System.out.println("stack is 1 empty : " + stack.empty());
        System.out.println("stack is 2 empty : " + stack.isEmpty());
        //进栈push 返回压入栈的对象
        System.out.println("push : " + stack.push("Hello"));
        System.out.println("push : " + stack.push(5));
        stack.push("World");
        stack.push("Hello");
        stack.push(5);

        /*
        push 和 add 的区别
        push其实就是调用了Vector的addElement方法。
        返回值不一样，add返回布尔类型 而push则返回插入元素的类型
         */
        System.out.println("push : " + stack.add("ljz"));
        stack.add(1, "modify 5");

        /*
        search 方法用于搜索堆栈中的元素并获取其与顶部的距离。此方法从1开始而不是从0开始计数位置。位于堆栈顶部的元素被视为在位置1。
        如果存在多个元素，则最接近顶部的元素的索引返回。如果成功找到该元素，则该方法返回其位置；如果缺少该元素，则返回-1。
        */
        System.out.println("search : " + stack.search("World"));

        //取栈顶值（不出栈）是线程安全的
        System.out.println("peek 1 : " + stack.peek());
        System.out.println("peek 2 : " + stack.peek());
        //取栈顶值（出栈）是线程安全的
        System.out.println("pop 1 : " + stack.pop());
        System.out.println("pop 2 : " + stack.pop());
//        System.out.println("pop 3 : " + stack.pop());//会报错！EmptyStackException
    }
}
