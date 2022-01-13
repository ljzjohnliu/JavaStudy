package com.study.java.leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 示例 2：
 * 输入：nums = []
 * 输出：[]
 * 示例 3：
 * 输入：nums = [0]
 * 输出：[]
 */
public class ThreeSum {
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
//        int[] nums = new int[]{6, 0, 1, 2, 5, 9};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));

        List<List<Integer>> lists = threeSum(nums);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println();
            for (int j = 0; j < lists.get(i).size(); j++) {
                System.out.print(lists.get(i).get(j) + " ");
            }
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        if (nums == null || nums.length <= 2) {
            return lists;
        }
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        int length = nums.length;
        for (int i = 0; i <length; i++) {
            if (nums[i] > 0) break;
//            if (i > 0 && nums[i] == nums[i - 1]) continue;
            while (i > 1 && nums[i] == nums[i - 1]) i++;

            int target = -nums[i];
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    lists.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return lists;
    }

    /**
     * 含重复序列结果
     */
    public static List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i ++) {
            list.add(nums[i]);
            for (int j = i + 1; j < nums.length; j++) {
                list.add(nums[j]);
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        list.add(nums[k]);
                        lists.add(list);
                        if (k < nums.length) {
                            list = new ArrayList<>();
                            list.add(nums[i]);
                            list.add(nums[j]);
                            continue;
                        }
                    }
                    if (k == nums.length - 1) {
                        list = new ArrayList<>();
                        list.add(nums[i]);
                    }
                }
                if (j == nums.length - 1) {
                    list = new ArrayList<>();
                }
            }
        }
        return lists;
    }
}
