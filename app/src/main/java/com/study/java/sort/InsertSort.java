package com.study.java.sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {
        int arr[] = new int[]{1, 6, 2, 2, 5, 8};
        InsertSort.insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr) {
        int target;

        //假定第一个元素被放到了正确的位置上
        //这样，仅需遍历1 - n-1
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            target = arr[i];

            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            arr[j] = target;
        }
    }
}
