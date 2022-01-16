package com.study.java.leecode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortUtils {

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        int[] nums = new int[]{ 1, -5, 4};
//        int[] result = sort(nums);
//        heapSort(nums);
        fastSortArray2(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 快速排序1.0版本只有小于等于区域
     */
    public static void fastSortArray1(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }
//        int random = left + (int) (Math.random() * (right - left));
//        int randomValue = nums[random];
//        nums[random] = nums[right];
//        nums[right] = randomValue;

        int random = left + (int) (Math.random() * (right - left + 1));
        int tempValue = nums[right];
        nums[right] = nums[random];
        nums[random] = tempValue;

        int divide = nums[right];
        int low = left - 1;
        int i = left;
        while (i < right) {
            if (nums[i] <= divide) {
                int temp = nums[low + 1];
                nums[low + 1] = nums[i];
                nums[i] = temp;
                low++;
            }
            i++;
        }
        int temp = nums[right];
        nums[right] = nums[low + 1];
        nums[low + 1] = temp;

        if (low >= left) {
            fastSortArray1(nums, left, low);
        }
        if (low + 2 <= right) {
            fastSortArray1(nums, low + 2, right);
        }
        return;
    }

    /**
     * 荷兰国旗问题
     */
    public static void fastSortArray2(int[] nums, int left, int right) {
        if (left == right)
            return;

        int random = left + (int) (Math.random() * (right - left + 1));
        int tempValue = nums[right];
        nums[right] = nums[random];
        nums[random] = tempValue;

        int divide = nums[right];
        int low = left - 1;
        int high = right;
        int i = left;
        int temp;
        while (i < high) {
            if (nums[i] < divide) {
                temp = nums[low + 1];
                nums[low + 1] = nums[i];
                nums[i] = temp;
                low++;
                i++;
            } else if (nums[i] > divide) {
                temp = nums[high - 1];
                nums[high - 1] = nums[i];
                nums[i] = temp;
                high--;
            } else {
                i++;
            }
        }

        temp = nums[high];
        nums[high] = nums[right];
        nums[right] = temp;

        if (low >= left) {
            fastSortArray2(nums, left, low);
        }
        if (high <= right) {
            fastSortArray2(nums, high, right);
        }
    }

//    public static int[] fastSortArray3(int[] nums) {
//
//    }

    public static int[] sort(int[] nums) {
        return sortByMerge(nums, 0, nums.length - 1);
    }

    /**
     * 归并排序 时间复杂度O（nlogn）
     */
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

    /**
     * 堆排序
     */
    public static void heapSort(int[] nums) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i : nums) {
            heap.add(i);
        }
        for (int i = 0; !heap.isEmpty(); i++) {
            nums[i] = heap.poll();
        }
        System.out.println(Arrays.toString(nums));
    }
}
