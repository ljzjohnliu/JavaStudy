package com.study.java.leecode;

import java.util.ArrayList;

/**
 * 最小的K个数
 * 描述
 * 给定一个长度为 n 的可能有重复值的数组，找出其中不去重的最小的 k 个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4(任意顺序皆可)。
 * 数据范围：0≤k,n≤10000，数组中每个数的大小0≤val≤1000
 * 要求：空间复杂度 O(n)，时间复杂度 O(nlogn)
 *
 * 示例1
 * 输入：[4,5,1,6,2,7,3,8],4
 * 返回值：[1,2,3,4]
 * 说明：
 * 返回最小的4个数即可，返回[1,3,2,4]也可以
 * 示例2
 * 输入：[1],0
 * 返回值：[]
 * 示例3
 * 输入：[0,1,2,1,2],3
 * 返回值：[0,1,1]
 */
public class MiniNum {

    public static void main(String[] args) {
        int[] nums = new int[]{4,5,1,6,2,7,3,8};
        ArrayList<Integer> res = GetLeastNumbers_Solution(nums, 4);
        for (Integer i : res) {
            System.out.print(i + "  ");
        }
    }

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || input.length < k) {
            return list;
        }
        int[] resNum = sortNums(input, 0, input.length - 1);
        for (int i = 0; i < k; i++) {
            list.add(resNum[i]);
        }
        return list;
    }

    public static int[] sortNums(int[] nums, int left, int right) {
        if (left == right) {
            return new int[]{nums[left]};
        }
        int mid = left + (right - left) / 2;
        int[] leftArr = sortNums(nums, left, mid);
        int[] rightArr = sortNums(nums, mid + 1, right);
        return mergeArr(leftArr, rightArr);
    }

    public static int[] mergeArr(int[] leftArr, int[] rightArr) {
        int[] resultArr = new int[leftArr.length + rightArr.length];
        int p = 0, pL = 0, pR = 0;
        while (pL < leftArr.length && pR < rightArr.length) {
            if (leftArr[pL] < rightArr[pR]) {
                resultArr[p++] = leftArr[pL++];
            } else {
                resultArr[p++] = rightArr[pR++];
            }
        }

        while (pL < leftArr.length) {
            resultArr[p++] = leftArr[pL++];
        }

        while (pR < rightArr.length) {
            resultArr[p++] = rightArr[pR++];
        }
        return resultArr;
    }
}
