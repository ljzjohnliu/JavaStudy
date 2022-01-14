package com.study.java.leecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class KuohaoQuestion {

    public static void main(String[] args) {
//        String s = "()";
//        String s = "(]";
        String s = "){";
        System.out.println(isValid(s));
    }

    //'('，')'，'{'，'}'，'['，']'
    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        if (chars.length % 2 != 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            char cn = chars[i];
            if(cn == '(' || cn == '{' || cn == '[') {
                stack.push((int)cn);
            } else {
                switch (cn) {
                    case ')':
                        if (stack.isEmpty() || '(' != stack.pop()) {
                            return false;
                        }
                        break;
                    case '}':
                        if (stack.isEmpty() || '{' != stack.pop()) {
                            return false;
                        }
                        break;
                    case ']':
                        if (stack.isEmpty() || '[' != stack.pop()) {
                            return false;
                        }
                        break;
                }
            }
        }
        return stack.isEmpty();
    }
}
