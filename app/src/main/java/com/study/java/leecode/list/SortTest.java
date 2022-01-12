package com.study.java.leecode.list;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        int[] nums = {5, 2, 3, 1, 4};
//        insertSort(nums);
//        System.out.println(Arrays.toString(nums));

//        int[] resArr = mergeSort(nums, 0, nums.length - 1);
//        System.out.println(Arrays.toString(resArr));

        int[] resArr = fastSort2(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(resArr));
    }

    /**
     * 构建大顶堆
     */
    public static void adjustHeap(int[] nums, int i, int len) {
        int temp, j;
        temp = nums[i];
        for (j = 2 * i; j < len; j *= 2) {// 沿关键字较大的孩子结点向下筛选
            if (j < len && nums[j] < nums[j + 1])
                ++j; // j为关键字中较大记录的下标
            if (temp >= nums[j])
                break;
            nums[i] = nums[j];
            i = j;
        }
        nums[i] = temp;
    }

    public static int[] heapSort(int[] nums) {
        int i;
        for (i = nums.length / 2 - 1; i >= 0; i--) {// 构建一个大顶堆
            adjustHeap(nums, i, nums.length - 1);
        }
        for (i = nums.length - 1; i >= 0; i--) {// 将堆顶记录和当前未经排序子序列的最后一个记录交换
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            adjustHeap(nums, 0, i - 1);// 将a中前i-1个记录重新调整为大顶堆
        }
        return nums;
    }

    /**
     * 快速排序2.0版本
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    public static int[] fastSort2(int[] nums, int start, int end) {
        if (start == end)
            return nums;

        int random = start + (int) (Math.random() * (end - start));
        int tempValue = nums[end];
        nums[end] = nums[random];
        nums[random] = tempValue;

        int low = start - 1;
        int high = end;
        int p = start;
        while (p < high) {
            if (nums[p] < nums[end]) {
                int temp = nums[p];
                nums[p] = nums[low + 1];
                nums[low + 1] = temp;
                p++;
                low++;
            } else if (nums[p] > nums[end]) {
                int temp = nums[p];
                nums[p] = nums[high - 1];
                nums[high - 1] = temp;
                high--;
            } else {
                p++;
            }
        }

        int temp = nums[end];
        nums[end] = nums[high];
        nums[high] = temp;
        high++;

        if (low >= start) {
            fastSort2(nums, start, low);
        }
        if (high <= end) {
            fastSort2(nums, high, end);
        }
        return nums;
    }

    /**
     * 快速排序1.0版本
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    public static int[] fastSort1(int[] nums, int start, int end) {
        if (start == end)
            return nums;

        int random = start + (int) (Math.random() * (end - start));
        int tempValue = nums[end];
        nums[end] = nums[random];
        nums[random] = tempValue;

        int low = start - 1;
        int p = start;
        while (p < end) {
            if (nums[p] <= nums[end]) {
                int temp = nums[low + 1];
                nums[low + 1] = nums[p];
                nums[p] = temp;
                low++;
            }
            p++;
        }
        int temp = nums[low + 1];
        nums[low + 1] = nums[end];
        nums[end] = temp;
        if (low >= start) {
            fastSort1(nums, start, low);
        }
        if (low + 2 <= end) {
            fastSort1(nums, low + 2, end);
        }
        return nums;
    }

    /**
     * 归并排序
     *
     * @param nums
     * @param start
     * @param end
     * @return
     */
    public static int[] mergeSort(int[] nums, int start, int end) {
        if (start == end) {
            return new int[]{nums[start]};
        }
        int mid = (start + end) / 2;
        int[] left = mergeSort(nums, start, mid);
        int[] right = mergeSort(nums, mid + 1, end);
        return mergeArr(left, right);
    }

    /**
     * 合并两个有序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] mergeArr(int[] left, int[] right) {
        int[] resultArr = new int[left.length + right.length];
        int p = 0;
        int pL = 0;
        int pR = 0;
        while (pL < left.length && pR < right.length) {
            if (left[pL] < right[pR]) {
                resultArr[p++] = left[pL++];
            } else {
                resultArr[p++] = right[pR++];
            }
        }

        while (pL < left.length) {
            resultArr[p++] = left[pL++];
        }

        while (pR < right.length) {
            resultArr[p++] = right[pR++];
        }

        return resultArr;
    }

    /**
     * 插入排序
     */
    public static int[] insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i - 1; j++) {
                if (nums[i] < nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 选择排序改进
     */
    public static int[] chooseSort1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[index]) {
                    index = j;
                }
            }
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
        }
        return nums;
    }

    /**
     * 选择排序改进
     */
    public static int[] chooseSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 冒泡排序
     */
    public static int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums;
    }
}
