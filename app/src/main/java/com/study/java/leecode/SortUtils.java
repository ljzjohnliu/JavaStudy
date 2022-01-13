package com.study.java.leecode;

public class SortUtils {
    public static int[] sort(int[] nums) {
        return sortByMerge(nums, 0, nums.length - 1);
    }

    public static int[] sortByMerge(int[] nums, int start, int end) {
        if (start == end) {
            return new int[]{nums[start]};
        }
        int mid = start + (end - start) / 2;
        int[] leftArr = sortByMerge(nums, start, mid);
        int[] rightArr = sortByMerge(nums, mid + 1, end);
        return mergeArr(leftArr, rightArr);
    }

    public static int[] mergeArr(int[] leftArr, int[] rightArr) {
        int[] resArr = new int[leftArr.length + rightArr.length];
        int lengthL = leftArr.length;
        int lengthR = rightArr.length;
        int pL = 0, pR = 0, p = 0;
        while (pL < lengthL && pR < lengthR) {
            if (leftArr[pL] <= rightArr[pR]) {
                resArr[p++] = leftArr[pL++];
            } else {
                resArr[p++] = rightArr[pR++];
            }
        }

        while (pL < lengthL) {
            resArr[p++] = leftArr[pL++];
        }

        while (pR < lengthR) {
            resArr[p++] = rightArr[pR++];
        }
        return resArr;
    }
}
