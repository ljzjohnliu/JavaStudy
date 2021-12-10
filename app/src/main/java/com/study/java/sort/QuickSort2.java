package com.study.java.sort;

import java.util.Arrays;

/**
 * 快速排序是一种排序执行效率很高的排序算法，它利用分治法来对待排序序列进行分治排序，
 * 它的思想主要是通过一趟排序将待排记录分隔成独立的两部分，其中的一部分比关键字小，
 * 后面一部分比关键字大，然后再对这前后的两部分分别采用这种方式进行排序，通过递归的运算最终达到整个序列有序
 * 快速排序算法思路：从数列中挑出一个元素，称为基准。
 * 重新排序数列，所有元素比基准值小的排在前面，所有元素比基准值大的排在后面。
 * 分区操作：使用递归把小于基准值元素的子数列和大于基准值元素的子数列排序
 */
public class QuickSort2 {

    public static void main(String[] args) {
        int arr[] = new int[]{1, 7, 4, 6, 3, 2, 5, 8};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int x = getIndex(arr, left, right);
            sort(arr, left, x - 1);
            sort(arr, x + 1, right);
        }
    }

    /**
     * 1、将基准数挖出形成第一个坑
     * 2、由后向前找比它小的数，找到后挖出此数填到前一个坑中
     * 3、由前向后找比它大或等于的数，找到后挖出此数填到前一个坑中
     * 4、重复如上步骤
     */
    public static int getIndex(int[] arr, int left, int right) {
        int i = left;
        int j = right;
        int x = arr[left];
        while (i < j) {
            //由后向前找比它小的数，找到后挖出此数填到前一个坑中
            while (i < j && arr[j] >= x) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }
            //由前向后找比它大或等于的数，找到后挖出此数填到前一个坑中
            while(i < j && arr[i] < x) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        arr[i] = x;
        return i;
    }
}
