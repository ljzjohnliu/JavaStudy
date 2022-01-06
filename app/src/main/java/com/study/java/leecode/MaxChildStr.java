package com.study.java.leecode;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 * 示例1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
 * <p>
 * 示例 4:
 * 输入: s = ""
 * 输出: 0
 * <p>
 * 提示：
 * 0 <= s.length <= 5 * 104
 * s由英文字母、数字、符号和空格组成
 */
public class MaxChildStr {

    public static void main(String[] args) {
        String s1 = "abcabcbb";
        String s2 = "bbbbb";
        String s3 = "pwwkew";
        String s4 = "";
        String s5 = "tmmzuxt";
//        System.out.println("----111------ max child length is : " + lengthOfLongestSubstring(s1));
//        System.out.println("----222------ max child length is : " + lengthOfLongestSubstring(s2));
//        System.out.println("----333------ max child length is : " + lengthOfLongestSubstring(s3));
//        System.out.println("----444------ max child length is : " + lengthOfLongestSubstring(s4));
        System.out.println("----555------ max child length is : " + lengthOfLongestSubstring(s5));
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0)
            return 0;
        char[] chars = s.toCharArray();
        int length = 0;
        Queue<String> queue = new LinkedList<String>();
        HashSet hashSet = new HashSet();
        int i = 0;

        while (i < chars.length) {
            System.out.println(" queue = " + queue + ", i = " + i + ", char is = " + chars[i]
                    + ", isContain = " + hashSet.contains(String.valueOf(chars[i]))
                    + "， hashSet = " + hashSet);
            if (!hashSet.contains(String.valueOf(chars[i]))) {
                queue.add(String.valueOf(chars[i]));
                hashSet.add(String.valueOf(chars[i]));
            } else {
                System.out.println(" queue = " + queue + ", i = " + i + ", char is = " + chars[i] + ", queue.peek() = " + queue.peek());
                while(queue.peek() != null && !queue.peek().equals(String.valueOf(chars[i]))) {
                    System.out.println("hashSet = " + hashSet + ",----delete : " + queue.peek());
                    hashSet.remove(queue.poll());
                }
                if (queue.peek().equals(String.valueOf(chars[i]))) {
                    hashSet.remove(queue.poll());
                }
                queue.add(String.valueOf(chars[i]));
                hashSet.add(String.valueOf(chars[i]));
            }
            i++;
            length = Math.max(length, queue.size());
        }
        return length;
    }

    /**
     * 优化方法
     */
    public static int lengthOfLongestSubstring2(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res   = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }
}
