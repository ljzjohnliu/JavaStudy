package com.study.java.leecode;

import java.util.Stack;

public class MyQueue {

    Stack<Integer> stack1;//存入时候的栈
    Stack<Integer> stack2;//取出时候的栈

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        if (stack1.isEmpty()) {
            reverseStack();
        }
        stack1.push(x);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            reverseStack();
        }
        return stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            reverseStack();
        }
        return stack2.peek();
    }

    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    private void reverseStack() {
        if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        } else {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }
    }
}
