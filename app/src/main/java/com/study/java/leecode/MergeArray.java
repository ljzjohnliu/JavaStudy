package com.study.java.leecode;

import java.util.Arrays;

/**
 * 给你两个按 非递减顺序 排列的整数数组nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，
 * 其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 */
public class MergeArray {

    public static void main(String[] args) {
        int[] num1 = new int[]{1, 2, 3, 0, 0, 0};
        int[] num2 = new int[]{2, 5, 6};
        merge(num1, 3, num2, 3);
        System.out.println(Arrays.toString(num1));
    }

    /**
     * 这里不能直接使用int[] copy1 = nums1；方式复制，这样会修改nums1的时候copy跟着被修改掉
     */
    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        int[] copy1 = nums1.clone();
        int p1 = 0;
        int p2 = 0;
        int p = 0;
        while (p1 < m && p2 < n) {
            if (copy1[p1] <= nums2[p2]) {
                nums1[p++] = copy1[p1++];
            } else {
                nums1[p++] = nums2[p2++];
            }
        }
        while (p1 < m) {
            nums1[p++] = copy1[p1++];
        }
        while (p2 < n) {
            nums1[p++] = nums2[p2++];
        }
    }

    /**
     * 由于nums1的后几位是预留给nums2的，故可以采取逆序填充
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p--] = nums1[p1--];
            } else {
                nums1[p--] = nums2[p2--];
            }
        }

        while (p1 >= 0) {
            nums1[p--] = nums1[p1--];
        }

        while (p2 >= 0) {
            nums1[p--] = nums2[p2--];
        }
    }
}
