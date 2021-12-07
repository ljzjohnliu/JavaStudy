package com.study.java.stack;

import java.util.Stack;

/**
 * 两个Stack实现队列
 */
public class TwoStacksQueue2 {

    private Stack<Integer> stackPush;
    private Stack<Integer> stackPop;

    public TwoStacksQueue2() {
        this.stackPush = new Stack<Integer>();
        this.stackPop = new Stack<Integer>();
    }

    private void pushToPop() {
        System.out.println("-------------------stackPop.isEmpty() = " + stackPop.isEmpty());
        if (stackPop.isEmpty()) {
            while(!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
    }

    public void add(int newNum) {
        stackPush.push(newNum);
        pushToPop();
    }

    public int poll() {
        if (stackPush.empty() && stackPop.isEmpty()) {
            throw new RuntimeException("Your queue is empty!");
        }

        pushToPop();
        return stackPop.pop();
    }

    public int peek() {
        if (stackPush.empty() && stackPop.isEmpty()) {
            throw new RuntimeException("Your queue is empty!");
        }

        pushToPop();
        return stackPop.peek();
    }

    public static void main(String[] args) {
//        int[] intArr = new int[]{3, 4, 5, 1, 2, 1};
        int[] intArr = new int[]{3, 4};
        TwoStacksQueue2 twoStacksQueue = new TwoStacksQueue2();
        for (int i = 0; i < intArr.length; i++) {
            twoStacksQueue.add(intArr[i]);
//            System.out.println("当前队列首元素：" + twoStacksQueue.peek());
        }

        for (Integer x : twoStacksQueue.stackPush) {
            System.out.println("----stackPush: " + x);
        }

        for (Integer x : twoStacksQueue.stackPop) {
            System.out.println("----stackPop: " + x);
        }

//        System.out.println("当前队列1元素：" + twoStacksQueue.poll());
//        System.out.println("当前队列2元素：" + twoStacksQueue.poll());
//        System.out.println("当前队列3元素：" + twoStacksQueue.poll());
//        System.out.println("当前队列4元素：" + twoStacksQueue.poll());
    }
}
