package com.study.java.leecode;

import java.util.Stack;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * push(x) —— 将元素 x 推入栈中。
 * pop()—— 删除栈顶的元素。
 * top()—— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 */
public class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
    int tempMin;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.add(val);
        if (minStack.isEmpty()) {
            tempMin = val;
        } else {
            tempMin = Math.min(tempMin, val);
        }
        minStack.add(tempMin);
    }

    public void pop() {
        stack.pop();
        minStack.pop();
        if (minStack.isEmpty()) {
            tempMin = minStack.peek();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
