package com.study.java.leecode;

import java.util.Stack;

public class TwoAddByString {

    public static void main(String[] args) {
        String num1 = "456", num2 = "77";
        System.out.println(num1.charAt(0));
        System.out.println(Integer.valueOf(num1.charAt(0) - '0'));
        String res = addStrings(num1, num2);
        System.out.println(res);
    }

//    public String addStrings(String num1, String num2) {
//        StringBuilder sb = new StringBuilder();
//        int carry = 0, i = num1.length()-1, j = num2.length()-1;
//        while(i >= 0 || j >= 0 || carry != 0){
//            if(i>=0) carry += num1.charAt(i--)-'0';
//            if(j>=0) carry += num2.charAt(j--)-'0';
//            sb.append(carry%10);
//            carry /= 10;
//        }
//        return sb.reverse().toString();
//    }

    public static String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        int addToHigh = 0;
        int p1 = num1.length() - 1;
        int p2 = num2.length() - 1;

        while (p1 >= 0 || p2 >= 0 || addToHigh > 0) {
            sum = (p1 >= 0 ? (num1.charAt(p1--) - '0') : 0)
                    + (p2 >= 0 ? (num2.charAt(p2--) - '0') : 0)
                    + addToHigh;
            sb.append(sum % 10);
            addToHigh = sum / 10;
        }
        return sb.reverse().toString();
    }

    /**
     * 直接使用StringBuilder存储结果，输出时候反转一下即可！
     */
    public static String addStrings3(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        int addToHigh = 0;
        int p1 = num1.length() - 1;
        int p2 = num2.length() - 1;

        while (p1 >= 0 && p2 >= 0) {
            sum = (num1.charAt(p1--) - '0') + (num2.charAt(p2--) - '0') + addToHigh;
            sb.append(sum % 10);
            addToHigh = sum / 10;
        }
        while (p1 >= 0) {
            sum = (num1.charAt(p1--) - '0') + addToHigh;
            sb.append(sum % 10);
            addToHigh = sum / 10;
        }
        while (p2 >= 0) {
            sum = (num2.charAt(p2--) - '0') + addToHigh;
            sb.append(sum % 10);
            addToHigh = sum / 10;
        }
        if (addToHigh > 0) {
            sb.append(1);
        }

        return sb.reverse().toString();
    }

    /**
     使用一个栈结构存储结果，计算过程可以省去两个栈，因为可以用charAt从字符串尾开始获取数据
     */
    public static String addStrings2(String num1, String num2) {
        Stack<Integer> resStack = new Stack<>();
        int sum = 0;
        int addToHigh = 0;
        int p1 = num1.length() - 1;
        int p2 = num2.length() - 1;

        while (p1 >= 0 && p2 >= 0) {
            sum = (num1.charAt(p1--) - '0') + (num2.charAt(p2--) - '0') + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        while (p1 >= 0) {
            sum = (num1.charAt(p1--) - '0') + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        while (p2 >= 0) {
            sum = (num2.charAt(p2--) - '0') + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        if (addToHigh > 0) {
            resStack.push(1);
        }
        StringBuilder sb = new StringBuilder();
        while (!resStack.isEmpty()) {
            sb.append(resStack.pop());
        }
        return sb.toString();
    }

    /**
     * 使用三个栈
     */
    public static String addStrings1(String num1, String num2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        Stack<Integer> resStack = new Stack<>();
        for (int i = 0; i < num1.length(); i++) {
            stack1.push(Integer.valueOf(num1.charAt(i) - '0'));
        }
        for (int i = 0; i < num2.length(); i++) {
            stack2.push(Integer.valueOf(num2.charAt(i) - '0'));
        }
        int sum = 0;
        int addToHigh = 0;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            sum = stack1.pop() + stack2.pop() + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        while (!stack1.isEmpty()) {
            sum = stack1.pop() + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        while (!stack2.isEmpty()) {
            sum = stack2.pop() + addToHigh;
            resStack.push(sum % 10);
            addToHigh = sum / 10;
        }
        if (addToHigh > 0) {
            resStack.push(1);
        }
        StringBuilder sb = new StringBuilder();
        while (!resStack.isEmpty()) {
            sb.append(resStack.pop());
        }
        return sb.toString();
    }
}
