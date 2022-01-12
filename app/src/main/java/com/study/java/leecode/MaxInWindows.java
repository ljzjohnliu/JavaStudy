package com.study.java.leecode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 滑动窗口的最大值
 * 描述:给定一个长度为 n 的数组 num 和滑动窗口的大小 size ，找出所有滑动窗口里数值的最大值。
 * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
 * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
 * {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 *
 * 窗口大于数组长度或窗口长度为0的时候，返回空。
 *
 * 数据范围： 1≤n≤10000，0≤size≤10000，数组中每个元素的值满足∣val∣≤10000
 * 要求：空间复杂度 O(n)，时间复杂度 O(n)
 */
public class MaxInWindows {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 4, 2, 6, 2, 5, 1};
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ArrayList<Integer> maxList = maxInWindows3(nums, 3);
        }
        System.out.println("Method 1 cost time is : " + (System.currentTimeMillis() - startTime1));

        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ArrayList<Integer> maxList = maxInWindows2(nums, 3);
        }
        System.out.println("Method 2 cost time is : " + (System.currentTimeMillis() - startTime2));
        ArrayList<Integer> maxList = maxInWindows2(nums, 3);
        System.out.println(maxList);
        if (maxList != null) {
            for (int i = 0; i < maxList.size(); i++) {
                System.out.println(maxList.get(i));
            }
        }
    }

    /**
     * 暴力解法1
     */
    public static ArrayList<Integer> maxInWindows1(int[] num, int size) {
        ArrayList<Integer> resultArr = new ArrayList<>();
        if (num == null || size == 0 || size > num.length) {
            return resultArr;
        }

        int start = size - 1;
        while (start < num.length) {
            int max = num[start];
            for (int i = start; i > start - size; i--) {
                max = Math.max(max, num[i]);
            }
            resultArr.add(max);
            start++;
        }
        return resultArr;
    }

    /**
     * 暴力解法2
     */
    public static ArrayList<Integer> maxInWindows2(int[] num, int size) {
        ArrayList<Integer> res = new ArrayList<>();
        if (num == null || size > num.length || size == 0) {
            return res;
        }
        int start = 0;
        while (start + size <= num.length) {
            int max = num[start];
            for (int i = start + 1; i < size + start; i++) {
                max = (max > num[i]) ? max : num[i];
            }
            res.add(max);
            start++;
        }

        return res;
    }

    /**
     * 双端队列实现
     */
    public static ArrayList<Integer> maxInWindows3(int[] num, int size) {
        ArrayList<Integer> maxList = new ArrayList<>();
        if (num == null || size == 0 || size > num.length) {
            return maxList;
        }
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        int r = 0;
        deque.addLast(r++);
        while (r < num.length) {
            if (r - size >= 0) {
                if (deque.getFirst() == (r - size)) {
                    maxList.add(num[deque.pollFirst()]);
                } else {
                    maxList.add(num[deque.peekFirst()]);
                }
            }

            while (!deque.isEmpty() && num[deque.peekLast()] <= num[r]) {
                deque.pollLast();
            }
            deque.add(r++);
        }
        maxList.add(num[deque.peekFirst()]);
        return maxList;
    }

    /**
     * 自己按照视频思路实现的方法，双端链表实现
     */
    public static ArrayList<Integer> maxInWindows4(int[] num, int size) {
        ArrayList<Integer> maxList = new ArrayList<>();
        if (num == null || size == 0 || size > num.length) {
            return maxList;
        }
        LinkedList<Integer> linkedList = new LinkedList<>();
        int r = 0;
        while (r < num.length) {
            if (linkedList.size() == 0) {
                linkedList.addLast(r++);
            }

            if (r - size >= 0) {
                maxList.add(num[linkedList.getFirst()]);
                if (linkedList.getFirst() == (r - size)) {
                    linkedList.removeFirst();
                }
            }

            while (linkedList.size() > 0 && num[linkedList.getLast()] <= num[r]) {
                linkedList.removeLast();
            }
            linkedList.addLast(r++);
        }
        maxList.add(num[linkedList.getFirst()]);
        return maxList;
    }
}
