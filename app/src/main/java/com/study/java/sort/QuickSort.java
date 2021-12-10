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
public class QuickSort {

    public static void main(String[] args) {
        int arr[] = new int[]{1, 7, 4, 6, 3, 2, 5, 8};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] array) {
        int len;
        if (array == null
                || (len = array.length) == 0
                || len == 1) {
            return;
        }
        sort(array, 0, len - 1);
    }

    /**
     * 快排核心算法，递归实现
     *
     * @param array
     * @param left
     * @param right
     */
    public static void sort(int[] array, int left, int right) {
        if (left > right) {
            return;
        }
        // base中存放基准数
        int base = array[left];
        int start = left, end = right;
        while (start != end) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while (array[end] >= base && start < end) {
                end--;
            }

            // 再从左往右边找，直到找到比base值大的数
            while (array[start] <= base && start < end) {
                start++;
            }

            // 上面的循环结束表示找到了位置或者(start>=end)了，交换两个数在数组中的位置
            if (start < end) {
                int tmp = array[start];
                array[start] = array[end];
                array[end] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        array[left] = array[start];
        array[start] = base;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        sort(array, left, start - 1);
        sort(array, start + 1, right);
    }

    public static void quickSort(int[] a, int low, int high) {
        int start = low;
        int end = high;
        int key = a[low];
        //判断开始角标是否小于结束角标
        while (end > start) {
            //从后往前比较
            while (end > start && a[end] >= key)
                end--;
            if (a[end] < key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后排列
            while (end > start && a[start] <= key)
                start++;
            if (a[start] > key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
        }
        //递归调用子序列
        if (start > low)
            quickSort(a, low, start - 1);
        if (end < high)
            quickSort(a, end + 1, high);
    }
}
