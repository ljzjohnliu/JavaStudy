package com.study.java.stack;

import java.util.Stack;

/**
 * 设计一个有getMin功能的栈
 */
public class MyStack2 {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MyStack2() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public Integer pop() {
        if (stackData.empty()) {
            throw new RuntimeException("Your stack is empty!");
        }

        if (stackData.peek() == stackMin.peek()) {
            stackMin.pop();
        }
        return stackData.pop();
    }

    public void push(int newNum) {
        stackData.push(newNum);

        if (stackMin.isEmpty()) {
            stackMin.push(newNum);
        } else if (newNum <= stackMin.peek()) {
            stackMin.push(newNum);
        }
    }

    public int getMin() {
        if (stackMin.isEmpty()) {
            throw new RuntimeException("Your stack is empty!");
        }

        return stackMin.peek();
    }

    public static void main(String[] args) {
        int[] intArr = new int[]{3, 4, 5, 1, 2, 1};
        MyStack2 myStack1 = new MyStack2();
        for (int i = 0; i < intArr.length; i++) {
            myStack1.push(intArr[i]);
            System.out.println( "当前栈中最小元素是："+ myStack1.getMin());
        }
    }
}
