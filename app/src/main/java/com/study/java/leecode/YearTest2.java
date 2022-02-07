package com.study.java.leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class YearTest2 {
    public static void main(String[] args) {
//        for (int i = 0; i < 40; i++) {
//            System.out.println(rand10());
//        }

//        List<List<Integer>> lists = new ArrayList<>();
//        lists.add(new ArrayList<>());
//        List<List<Integer>> helper = new ArrayList<>(lists);
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(123);
//        helper.add(list);
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.println("aaa = " + lists.get(i));
//        }
//        for (int i = 0; i < helper.size(); i++) {
//            System.out.println("bbb = " + helper.get(i));
//        }


        int[] nums1 = new int[]{1, 2, 3, 2, 1};
        int[] nums2 = new int[]{3, 2, 1, 4, 7};
        int maxLength = findLength(nums1, nums2);
        System.out.println("maxLength = " + maxLength);

//        int[] nums = new int[]{1, 2, 3};
        int[] nums = new int[]{1, 2};
        List<List<Integer>> lists = subsets(nums);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println("sss = " + lists.get(i));
        }
    }

    /**
     * 给你一个整数数组nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        for (int i = (int)Math.pow(2, n); i < (int)Math.pow(2, n+1); i++) {
            String bitmask = Integer.toBinaryString(i).substring(1);
            List<Integer> cur = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                if (bitmask.charAt(j) == '1') cur.add(nums[j]);
            }
            result.add(cur);
        }
        return result;
    }

    //回溯法
    static List<List<Integer>> result = new ArrayList<>();
    static int n;
    public static List<List<Integer>> subsets11(int[] nums) {
        n = nums.length;
        for (int k = 0; k <= n; k++) {
            backtrack(0, k, new ArrayList<Integer>(), nums);
        }
        return result;
    }
    public static void backtrack(int start, int k, ArrayList<Integer> cur, int[] nums) {
        if (k == 0) {
            result.add(new ArrayList<Integer>(cur));
            return;
        }
        for (int i = start; i < n; i++) {
            cur.add(nums[i]);
            backtrack(i + 1, k - 1, cur, nums);
            cur.remove(cur.size() - 1);
        }
    }
    public static List<List<Integer>> subsets0(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (int num : nums) {
            List<List<Integer>> newSubSets = new ArrayList<>();
            for (List<Integer> subset : result) {
                List<Integer> newSubSet = new ArrayList<>(subset);
                newSubSet.add(num);
                newSubSets.add(newSubSet);
            }
            result.addAll(newSubSets);
        }
        return result;
    }

    public static List<List<Integer>> subsets1(int[] nums) {
        return recurse(nums.length, nums);
    }
    public static List<List<Integer>> recurse(int cur, int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (cur == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        List<List<Integer>> existing_result = recurse(cur - 1, nums);
        result.addAll(existing_result);
        for (List<Integer> subSet : existing_result) {
            List<Integer> newSubset = new ArrayList<>(subSet);
            newSubset.add(nums[cur-1]);
            result.add(newSubset);
        }
        return result;
    }
    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(new ArrayList<>());
        List<List<Integer>> helper = new ArrayList<>();
//        helper.addAll(lists);
        helper.add(new ArrayList<>());
//        Collections.copy(helper, lists);
        for (int i = 0; i < nums.length; i++) {
            for (List<Integer> list : helper) {
                list.add(nums[i]);
                System.out.println("uuu = " + lists);
            }
            lists.addAll(helper);
            helper.clear();
            for (List<Integer> list : lists) {
                helper.add(list);
//                helper = new ArrayList<>(lists);
            }
        }
        return lists;
    }

    /**
     * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度
     * 输入：
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * 输出：3
     * 解释：长度最长的公共子数组是 [3, 2, 1]。
     */
    public static int findLength(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0)
            return 0;
        int max = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }

    public static int findLength2(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) {
            return 0;
        }
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        int result = 0;
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        return result;
    }

    public static int rand10() {
        //如下公式能得到1～7*7上等概率所有数字
        int num = (rand7() - 1) * 7 + rand7();
        while (num > 40) {
            num = (rand7() - 1) * 7 + rand7();
        }
        return (num % 10 == 0) ? 10 : (num % 10);
    }

    //1-10
    public static int qrand10() {
        int result;
        do {
            result = (get0or1() << 3) + (get0or1() << 2) + (get0or1() << 1) + get0or1();
        } while (result > 9);
        return result + 1;
    }

    public static int rand7() {
        return (int) (Math.random() * 6) + 1;
    }

    public static int get0or1() {
        int random = rand7();
        for (; ; ) {
            if (random < 4) {
                return 0;
            } else if (random > 4) {
                return 1;
            } else {
                random = rand7();
            }
        }
    }

    /**
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点
     */
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfsDeep(root);
        return max;
    }

    public int dfsDeep(TreeNode root) {
        if (root.left == null && root.right == null)
            return 0;
        int leftDp = root.left == null ? 0 : dfsDeep(root.left);
        int rightDp = root.right == null ? 0 : dfsDeep(root.right);
        max = Math.max(leftDp, rightDp);
        return Math.max(leftDp, rightDp);
    }

    //利用二叉树的递归套路解题
    public int diameterOfBinaryTree2(TreeNode root) {
        Diameter diameter = diameterOfBinaryTreeProcess(root);
        return diameter != null ? diameter.diameter : 0;
    }

    public Diameter diameterOfBinaryTreeProcess(TreeNode root) {
        Diameter diameter = new Diameter();
        if (root == null) {
            diameter.deep = 0;
            diameter.diameter = 0;
            return diameter;
        }
        Diameter leftD = diameterOfBinaryTreeProcess(root.left);
        Diameter rightD = diameterOfBinaryTreeProcess(root.right);
        diameter.deep = Math.max(leftD.deep, rightD.deep) + 1;
        diameter.diameter = Math.max(Math.max(leftD.diameter, rightD.diameter), leftD.deep + rightD.deep - 1);
        return diameter;
    }

    public static class Diameter {
        int deep;
        int diameter;
    }

    /**
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * 有效 二叉搜索树定义如下：
     * 节点的左子树只包含 小于 当前节点的数。
     * 节点的右子树只包含 大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     */
    public static boolean isValidBST(TreeNode root) {
        BSTInfo bstInfo = isValidBSTProcess(root);
        return bstInfo == null || bstInfo.isBST;
    }

    public static BSTInfo isValidBSTProcess(TreeNode root) {
        BSTInfo bstInfo = new BSTInfo();

        if (root == null) {
            return null;
        }

        BSTInfo leftInfo = isValidBSTProcess(root.left);
        BSTInfo rightInfo = isValidBSTProcess(root.right);
        bstInfo.max = rightInfo != null ? rightInfo.max : root.val;
        bstInfo.min = leftInfo != null ? leftInfo.min : root.val;
        if (leftInfo == null && rightInfo == null) {
            bstInfo.isBST = true;
        } else {
            bstInfo.isBST = (leftInfo == null || (leftInfo.isBST && root.val > leftInfo.max))
                    && (rightInfo == null || (rightInfo.isBST && root.val < rightInfo.min));
        }
        return bstInfo;
    }

    public static BSTInfo isValidBSTProcess1(TreeNode root) {
        BSTInfo bstInfo = new BSTInfo();

        if (root == null) {
            bstInfo.max = null;
            bstInfo.max = null;
            bstInfo.isBST = true;
            return bstInfo;
        }

        BSTInfo leftInfo = isValidBSTProcess1(root.left);
        BSTInfo rightInfo = isValidBSTProcess1(root.right);
        bstInfo.max = rightInfo.max != null ? rightInfo.max : root.val;
        bstInfo.min = leftInfo.min != null ? leftInfo.min : root.val;
        bstInfo.isBST = leftInfo.isBST && rightInfo.isBST
                && (leftInfo.max == null || leftInfo.max < root.val)
                && (rightInfo.min == null || rightInfo.min > root.val);
        return bstInfo;
    }

    public static class BSTInfo {
        Integer max;
        Integer min;
        boolean isBST;
    }
}
