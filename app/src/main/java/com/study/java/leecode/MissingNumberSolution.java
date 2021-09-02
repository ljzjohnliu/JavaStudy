package com.study.java.leecode;

/**
 * 如何在一个 1 到 100 的整数数组中找到丢失的数字?
 *
 * 引申题目：
 * 1、一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 * 2、给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
 */
public class MissingNumberSolution {

    public static void main(String[] args) {
        //如何在一个 1 到 100 的整数数组中找到丢失的数字?
        int[] nums = {1, 2, 3, 4, 5, 6, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
                61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                91, 92, 93, 94, 95, 96, 97, 98, 99, 100};

        int find = findMissingNumber(nums);
        System.out.println("main find = " + find);

        //引申题目1
        int[] nums1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 9};
//        int[] array = new int[]{0, 1, 3, 4, 5, 6, 7};
//        int[] array = new int[]{1};
        int missNum = missingNumberFun(nums1, 0, nums1.length - 1);
//        int missNum = missingNumberFun0(nums1);
//        int missNum = missingNumberFun(nums1);
        System.out.println("main missNum = " + missNum);

        //引申题目2
        int[] nums2 = new int[]{9,6,4,2,3,5,7,0,1};
        int missNum2 = missingNumberFun2(nums2);
        System.out.println("main missNum2 = " + missNum2);
    }

    /**
     *引申题目2,由于无序所以，不能用下标跟值做判断了
     */
    public static int missingNumberFun2(int[] nums) {
        System.out.println("missingNumberFun2 nums.length = " + nums.length);
        int n = nums.length;
        int sum = (n + 0) * (n + 1) / 2;
        int otherSum = 0;
        for (int i : nums) {
            otherSum += i;
        }
        return sum - otherSum;
    }

    /**
     * 因为明确是1～100的整数数组丢失了一个数字，那么1～100的加和可以算出，数组的实际加和也可得出，差值即为缺失数字
     */
    public static int findMissingNumber(int[] nums) {
        int n = nums.length + 1;
        int sum = (n + 1) * n / 2;
        int otherSum = 0;
        for (int i : nums) {
            otherSum += i;
        }
        return sum - otherSum;
    }

    /**
     * 因为数组有序，并且从0开始，可以遍历数组，下标跟值不等的即为缺失的数字,如果都相等，那么就是缺失最后一位，长度加1即可
     */
    public static int missingNumberFun0(int[] nums) {
        System.out.println("missingNumber nums = " + nums);
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i != nums[i]) {
                result = i;
                break;
            }
            result = i + 1;
        }
        return result;
    }

    /**
     * 由于数组有序，可以通过二分法，如果mid的下标和值不等，那么缺失数字在左边的子数组中，将last前移至mid，再次使用二分法；
     * 如果mid下标跟值相等，那么缺失数字在右边的子数组中，将pre后移mid+1,再次使用二分法;
     * 直至pre=last时候，计算出缺失的是pre还是pre+1
     */
    public static int missingNumberFun(int[] nums) {
        int pre = 0;
        int last = nums.length - 1;
        System.out.println("missingNumber nums length = " + nums.length + ", last = " + last + ", pre = " + pre);
        while (pre < last) {
//            int mid = (pre + last) >>> 1;
            int mid = (pre + last) / 2;
            if (nums[mid] == mid) {
                pre = mid + 1;
            } else {
                last = mid;
            }
        }
        return pre == nums[pre] ? pre + 1 : pre;
    }

    /**
     *采用递归处理
     */
    public static int missingNumberFun(int[] nums, int start, int end) {
        System.out.println("missingNumber nums length = " + nums.length + ", end = " + end + ", start = " + start);
        if (end - start <= 1) {
            return nums[end] == end ? end + 1 : end;
        }
        int mid = (end + start) / 2;
        if (nums[mid] == mid) {
            return missingNumberFun(nums, mid + 1, nums.length - 1);
        } else {
            return missingNumberFun(nums, 0, mid);
        }
    }
}
