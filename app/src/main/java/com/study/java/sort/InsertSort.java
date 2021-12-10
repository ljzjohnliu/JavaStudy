package com.study.java.sort;

import java.util.Arrays;

/**
 * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入。
 * 假定n是数组的长度，
 * 首先假设第一个元素被放置在正确的位置上，这样仅需从1-n-1范围内对剩余元素进行排序。对于每次遍历，从0-i-1范围内的元素已经被排好序，
 * 每次遍历的任务是：通过扫描前面已排序的子列表，将位置i处的元素定位到从0到i的子列表之内的正确的位置上。
 * 将arr[i]复制为一个名为target的临时元素。
 * 向下扫描列表，比较这个目标值target与arr[i-1]、arr[i-2]的大小，依次类推。
 * 这个比较过程在小于或等于目标值的第一个元素(arr[j])处停止，或者在列表开始处停止（j=0）。
 * 在arr[i]小于前面任何已排序元素时，后一个条件（j=0）为真，
 * 因此，这个元素会占用新排序子列表的第一个位置。
 * 在扫描期间，大于目标值target的每个元素都会向右滑动一个位置（arr[j]=arr[j-1]）。
 * 一旦确定了正确位置j，
 * 目标值target（即原始的arr[i]）就会被复制到这个位置。
 * 与选择排序不同的是，插入排序将数据向右滑动，并且不会执行交换。
 */
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
