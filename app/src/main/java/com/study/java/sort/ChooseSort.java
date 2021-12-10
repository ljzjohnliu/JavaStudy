package com.study.java.sort;

import java.util.Arrays;

/**
 * **选择排序优缺点：**
 * 优点：一轮比较只需要换一次位置；
 * 缺点：效率慢，不稳定(举个例子5，8，5，2，9 我们知道第一遍选择第一个元素5会和2交换，
 * 那么原序列中2个5的相对位置前后顺序就破坏了)。
 */
public class ChooseSort {

    public static void main(String[] args) {
        int arr[] = new int[]{1, 6, 2, 2, 5, 8};
        ChooseSort.chooseSort(arr);
//        ChooseSort.chooseSort2(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void chooseSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        int temp;
        for (int i = 0; i <= arr.length - 1; i++) {
            for (int j = i + 1; j <= arr.length - 1; j++) {
                //把小的元素置换到前边
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println("chooseSort cost time is : " + (System.currentTimeMillis() - startTime));
    }

    public static void chooseSort2(int[] arr) {
        long startTime = System.currentTimeMillis();
        int temp;
        for (int i = 0; i <= arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j <= arr.length - 1; j++) {
                //把小的元素的下标记住到min
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
        System.out.println("chooseSort2 cost time is : " + (System.currentTimeMillis() - startTime));
    }
}
