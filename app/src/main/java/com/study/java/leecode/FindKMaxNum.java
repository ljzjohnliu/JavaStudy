package com.study.java.leecode;

import java.util.Arrays;

public class FindKMaxNum {

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4};
        int result = findKthLargest(nums, 2);
        System.out.println(Arrays.toString(nums));
        System.out.println("result is = " + result);
    }

    public static int findKthLargest(int[] nums, int k) {
        // Arrays.sort(nums);
        // return nums[nums.length - k];
        int[] resArr = sort(nums, 0, nums.length - 1);
        return resArr[nums.length - k];
    }

    public static int[] sort(int[] nums, int start, int end) {
        if (start == end) {
            return new int[]{nums[start]};
        }
//         int mid = start + (end - start) >> 1;
//        System.out.println("mid is = " + mid);
        int mid = start + (end - start) / 2;
        int[] leftArr = sort(nums, start, mid);
        int[] rightArr = sort(nums, mid + 1, end);
        return merge(leftArr, rightArr);
    }

    public static int[] merge(int[] leftArr, int[] rightArr) {
        int[] resArr = new int[leftArr.length + rightArr.length];
        int p = 0, p1 = 0, p2 = 0;
        int length1 = leftArr.length;
        int length2 = rightArr.length;
        while (p1 < length1 && p2 < length2) {
            if (leftArr[p1] <= rightArr[p2]) {
                resArr[p++] = leftArr[p1++];
            } else {
                resArr[p++] = rightArr[p2++];
            }
        }
        while (p1 < length1) {
            resArr[p++] = leftArr[p1++];
        }
        while (p2 < length2) {
            resArr[p++] = rightArr[p2++];
        }
        return resArr;
    }
}
