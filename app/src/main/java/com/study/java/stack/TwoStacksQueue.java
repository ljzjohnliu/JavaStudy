package com.study.java.stack;

import java.util.Stack;

/**
 * 两个Stack实现队列
 */
public class TwoStacksQueue {

    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public TwoStacksQueue() {
        this.stack1 = new Stack<Integer>();
        this.stack2 = new Stack<Integer>();
    }

    public void add(int newNum) {
        stack1.push(newNum);
    }

    public int poll() {
        if (stack1.empty()) {
            throw new RuntimeException("Your queue is empty!");
        }

        stack2.clear();
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }

        int pp = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return pp;
    }

    public int peek() {
        if (stack1.empty()) {
            throw new RuntimeException("Your queue is empty!");
        }

        stack2.clear();
        while (!stack1.isEmpty()) {
            System.out.println("当前stack1首元素：" + stack1.peek());
            stack2.push(stack1.pop());
        }
        int pp = stack2.peek();

        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }

        return pp;
    }

    public static void main(String[] args) {
        int[] intArr = new int[]{3, 4, 5, 1, 2, 1};
//        int[] intArr = new int[]{3, 4};
        TwoStacksQueue twoStacksQueue = new TwoStacksQueue();
        for (int i = 0; i < intArr.length; i++) {
            twoStacksQueue.add(intArr[i]);
            System.out.println("当前队列首元素：" + twoStacksQueue.peek());
        }

        ;
        System.out.println("当前队列1元素：" + twoStacksQueue.poll());
        System.out.println("当前队列2元素：" + twoStacksQueue.poll());
        System.out.println("当前队列3元素：" + twoStacksQueue.poll());
        System.out.println("当前队列4元素：" + twoStacksQueue.poll());
    }
}
