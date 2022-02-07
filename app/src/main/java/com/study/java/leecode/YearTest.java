package com.study.java.leecode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.study.java.fina.FinalTest;
import com.study.java.leecode.list.ListIntersect;
import com.study.java.leecode.list.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class YearTest {

    public static void main(String[] args) {
        ListNode list1Node7 = new ListNode(5, null);
        ListNode list1Node6 = new ListNode(4, list1Node7);
        ListNode list1Node5 = new ListNode(4, list1Node6);
        ListNode list1Node4 = new ListNode(3, list1Node5);
        ListNode list1Node3 = new ListNode(3, null);
        ListNode list1Node2 = new ListNode(2, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);

//        ListNode resNode = reverseList(list1Node1);
//        while (resNode != null) {
//            System.out.println(resNode.val);
//            resNode = resNode.next;
//        }

        int[] subNums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = subNums[0];
        int sum = 0;
        for (int num : subNums) {
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            result = Math.max(result, sum);
        }
//        int result = maxSubArray44(subNums);
        System.out.println(result);
//
//        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
//        int gap = maxProfit(prices);
//        System.out.println(gap);

//        int[] nums1 = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
//        int[] nums1 = new int[]{0, 1, 0, 3, 2, 3};
//        int length = lengthOfLIS(nums1);
//        System.out.println(length);

//        int[] trap = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
//        int[] trap = new int[]{4, 2, 3};
//        System.out.println(trap(trap));

//        TreeNode node3 = new TreeNode(3);
//        TreeNode node2 = new TreeNode(2);
//        node2.left = node3;
//        TreeNode node1 = new TreeNode(1);
//        node1.right = node2;
//        List<Integer> list = inorderTraversal(node1);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

//        ListNode list2Node5 = new ListNode(5, null);
//        ListNode list2Node4 = new ListNode(4, list2Node5);
//        ListNode list2Node3 = new ListNode(3, list2Node4);
//        ListNode list2Node2 = new ListNode(2, null);
//        ListNode list2Node1 = new ListNode(1, list2Node2);
//        ListNode resNode = removeNthFromEnd(list2Node1, 2);
//        while (resNode != null) {
//            System.out.print(resNode.val + "  ");
//            resNode = resNode.next;
//        }

        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};
        double median = findMedianSortedArrays(nums1, nums2);
        System.out.println("median = " + median);

        int[] nums = new int[]{1, 1};
//        int[] nums = new int[]{3, 4, -1, 1};
        int res = firstMissingPositive2(nums);
        System.out.println("res = " + res);

        List<String> list = generateParenthesis(3);
        for (String s : list) {
            System.out.println(s);
        }

    }

    private static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode resNode = null;
        ListNode temp;
        while (head != null) {
            temp = head.next;
            head.next = resNode;
            resNode = head;
            head = temp;
        }
        return resNode;
    }

    public static int lengthOfLongestSubstring(String s) {
        int[] last = new int[128];
        Arrays.fill(last, -1);

        char[] chars = s.toCharArray();
        int start = 0;
        int result = 0;
        char cn;
        for (int i = 0; i < s.length(); i++) {
            cn = chars[i];
            start = Math.max(start, last[cn] + 1);
            result = Math.max(result, i - start + 1);
            last[cn] = i;
        }
        return result;
    }

    /**
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组 是数组中的一个连续部分。
     */
    //{-2, 1, -3, 4, -1, 2, 1, -5, 4}
    public static int maxSubArray44(int[] nums) {
        int sum = nums[0];
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] > nums[i]) {
                sum += nums[i];
            } else {
                sum = nums[i];
            }
            max = Math.max(max, sum);
        }
        return max;
    }
    public static int maxSubArray(int[] nums) {

        int res = nums[0];
        int sum = 0;

        for (int n : nums) {
            if (sum > 0)
                sum += n;
            else
                sum = n;
            res = Math.max(res, sum);
        }
        return res;
    }

    /**
     * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * 你可以按任意顺序返回答案。
     */
    public static int[] twoSum0(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                result[0] = map.get(nums[i]);
                result[1] = i;
            }
            map.put(target - nums[i], i);
        }
        return result;
    }
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] res = new int[2];

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                res[0] = map.get(nums[i]);
                res[1] = i;
            }
            map.put(target - nums[i], i);
        }
        return res;
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode resNode = new ListNode(0);
        ListNode resCur = resNode;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                resCur.next = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                resCur.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            resCur = resCur.next;
        }

        if (list1 != null)
            resCur.next = list1;

        if (list2 != null)
            resCur.next = list2;

        return resNode.next;
    }
    public static ListNode getCycleNode(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slowNode = head.next;
        ListNode fastNode = head.next.next;
        boolean hsCycle = false;
        while (fastNode != null && fastNode.next != null) {
            if (fastNode == slowNode) {
                fastNode = head;
                hsCycle = true;
                break;
            }
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        if (hsCycle) {
            while (fastNode != slowNode) {
                fastNode = fastNode.next;
                slowNode = slowNode.next;
            }
            return fastNode;
        }
        return null;
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        ListNode slowNode = head.next;
        ListNode fastNode = head.next.next;
        boolean isHasCycle = false;

        while (fastNode != null && fastNode.next != null) {
            if (slowNode == fastNode) {
                isHasCycle = true;
                break;
            }

            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        return isHasCycle;
    }

    public static ListNode firstCycleNode(ListNode head) {
        if (head == null || head.next == null)
            return null;

        ListNode slowNode = head.next;
        ListNode fastNode = head.next.next;
        boolean isHasCycle = false;
        ListNode firstCycleNode = null;

        while (fastNode != null && fastNode.next != null) {
            if (slowNode == fastNode) {
                fastNode = head;
                isHasCycle = true;
                break;
            }

            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        if (isHasCycle) {
            while (slowNode != fastNode) {
                slowNode = slowNode.next;
                fastNode = fastNode.next;
            }
            firstCycleNode = slowNode;
        }

        return firstCycleNode;
    }

    //层序遍历 打印到一个list中！
    public static List<List<Integer>> levelOrder01(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (root == null)
            return lists;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int curLevel = 0;
        int curSize = queue.size();
        TreeNode tempNode;
        while (!queue.isEmpty()) {
            tempNode = queue.poll();
            list.add(tempNode.val);
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
            if (list.size() == curSize) {
                lists.add(curLevel++, list);
                list = new ArrayList<>();
                curSize = queue.size();
            }
        }
        return lists;
    }
    public static List<Integer> levelOrder1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode tempNode;
        while (!queue.isEmpty()) {
            tempNode = queue.poll();
            list.add(tempNode.val);
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
        return list;
    }

    //层序遍历 分层打印到list中
    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null)
            return lists;

        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        int curLevel = 0;
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        queue.add(root);
        TreeNode tempNode;
        while (!queue.isEmpty()) {
            tempNode = queue.poll();
            list.add(tempNode.val);
            if (tempNode.left != null) {
                queue.add(tempNode.left);
                nextEnd = tempNode.left;
            }
            if (tempNode.right != null) {
                queue.add(tempNode.right);
                nextEnd = tempNode.right;
            }
            if (tempNode == curEnd) {
                lists.add(curLevel++, list);
                list = new ArrayList<>();
                curEnd = nextEnd;
                nextEnd = null;
            }
        }
        return lists;
    }

    public static List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null)
            return lists;

        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        int curLevel = 0;
        int levelCount;
        queue.add(root);
        TreeNode tempNode;
        while (!queue.isEmpty()) {
            levelCount = queue.size();
            while (levelCount > 0) {
                tempNode = queue.poll();
                levelCount--;
                list.add(tempNode.val);
                if (tempNode.left != null) {
                    queue.add(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.add(tempNode.right);
                }
                if (levelCount == 0) {
                    lists.add(curLevel++, list);
                    list = new ArrayList<>();
                }
            }
        }
        return lists;
    }

    /**
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     */

    public static int maxProfit(int[] prices) {
        int min = prices[0];
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            result = Math.max(result, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return result;
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        boolean isAHasCycle = hasCycle(headA);
        boolean isBHasCycle = hasCycle(headB);

        ListNode curNode = headA;
        int count = 0;
        while (curNode != null) {
            curNode = curNode.next;
            count++;
        }

        curNode = headB;
        while (curNode != null) {
            curNode = curNode.next;
            count--;
        }

        ListNode list1 = count >= 0 ? headA : headB;
        ListNode list2 = count < 0 ? headB : headA;

        int step = Math.abs(count);
        while (step > 0) {
            list1 = list1.next;
            step--;
        }

        ListNode resNode = null;
        while (list1 != null && list2 != null) {
            if (list1 == list2) {
                resNode = list1;
                break;
            }
            list1 = list1.next;
            list2 = list2.next;
        }
        return resNode;
    }

    /**
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4
     */
    //{0, 1, 0, 3, 2, 3}
    public static int lengthOfLIS(int[] nums) {
        int res = 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
     */
//    public static int trap(int[] height) {
//        int[][] traps = new int[height.length][2];
//        int leftMax = height[0];
//        for (int i = 0; i < height.length; i++) {
//            if (height[i] >= leftMax) {
//                traps[i][0] = -1;
//                leftMax = Math.max(leftMax, height[i]);
//            } else {
//                traps[i][0] = leftMax - height[i];
//            }
//        }
//        int rightMax = height[height.length - 1];
//        for (int i = height.length - 1; i >= 0; i--) {
//            if (height[i] >= rightMax) {
//                traps[i][1] = -1;
//                rightMax = Math.max(rightMax, height[i]);
//            } else {
//                traps[i][0] = rightMax - height[i];
//            }
//        }
//        int trapCount = 0;
//        for (int i = 0; i < traps.length; i++) {
//            if (traps[i][0] != -1 && traps[i][1] != -1) {
//                trapCount += (Math.min(traps[i][0], traps[i][1]) - height[i]);
//            }
//        }
//        return trapCount;
//    }
    public static int trap(int[] height) {
        int[][] traps = new int[height.length][2];

        int leftMax = 0, rightMax = height.length - 1;
        for (int i = 0; i < height.length; i++) {
            traps[i][0] = height[leftMax] > height[i] ? leftMax : -1;
            if (height[leftMax] < height[i]) {
                leftMax = i;
            }
        }
        for (int j = height.length - 1; j >= 0; j--) {
            traps[j][1] = height[rightMax] > height[j] ? rightMax : -1;
            if (height[rightMax] < height[j]) {
                rightMax = j;
            }
        }
        int count = 0;
        for (int i = 0; i < traps.length; i++) {
            System.out.println(Arrays.toString(traps[i]));
            if (traps[i][0] != -1 && traps[i][1] != -1) {
                int cc = Math.min(height[traps[i][0]], height[traps[i][1]]) - height[i];
                count += Math.max(cc, 0);
            }
        }
        return count;
    }

    /**
     * 二叉树的中序遍历
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     */
//    public static List<Integer> inorderTraversal(TreeNode root) {
//
//    }
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null)
            return list;
        Stack<TreeNode> stack = new Stack<>();
        HashSet<TreeNode> set = new HashSet<>();
        stack.add(root);
        set.add(root);
        TreeNode tempNode;
        while (!stack.isEmpty()) {
            if (stack.peek().left == null || set.contains(stack.peek().left)) {
                tempNode = stack.pop();
                list.add(tempNode.val);
                if (tempNode.right != null) {
                    if (!set.contains(tempNode.right)) {
                        stack.add(tempNode.right);
                        set.add(tempNode.right);
                    }
                }
            } else {
                if (stack.peek().left != null) {
                    if (!set.contains(stack.peek().left)) {
                        stack.add(stack.peek().left);
                        set.add(stack.peek().left);
                    }
                }
            }
        }
        return list;
    }

    //利用递归序遍历
    public static List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        traversalProcess(root, list);
        return list;
    }

    public static void traversalProcess(TreeNode root, List<Integer> list) {
        if (root == null)
            return;

        traversalProcess(root.left, list);
        list.add(root.val);
        traversalProcess(root.right, list);
    }

    /**
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
        for (int i = 0; i < n; ++i) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

//    public static ListNode removeNthFromEnd(ListNode head, int n) {
//        ListNode dummy = new ListNode(0, head);
//        ListNode first = head;
//        ListNode second = dummy;
//        for (int i = 0; i < n; ++i) {
//            first = first.next;
//        }
//        while (first != null) {
//            first = first.next;
//            second = second.next;
//        }
//        second.next = second.next.next;
//        ListNode ans = dummy.next;
//        return ans;
//    }

    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        return removeNode(head, n) == n ? head.next : head;
    }

    public static int removeNode(ListNode node, int n) {
        if (node.next == null) return 1;
        int m = removeNode(node.next, n);
        if (m == n)
            if (m == 1) node.next = null;
            else node.next = node.next.next;
        return m + 1;
    }

    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        if (head == null || head.next == null)
            return null;
        if (head.next.next == null) {
            switch (n) {
                case 1:
                    head.next = null;
                    return head;
                case 2:
                    return head.next;
            }
        }
        ListNode slowNode = head;
        ListNode fastNode = head.next;
        for (int i = 0; i < n; i++) {
            fastNode = fastNode.next;
        }

        while (fastNode != null) {
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }
        System.out.println(slowNode.val);
        if (slowNode == head)
            return head.next;

        slowNode.next = slowNode.next != null ? slowNode.next.next : null;
        return head;
    }

    public static void print() {

    }

    /**
     * 排序链表
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ListNode sortList1(ListNode head) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        while (head != null) {
            queue.add(new ListNode(head.val));
            head = head.next;
        }
        ListNode resNode = new ListNode(0);
        while (!queue.isEmpty()) {
            resNode.next = queue.poll();
            resNode = resNode.next;
        }
        return resNode;
    }

    public static ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slowNode = head, fastNode = head.next, l, r;
        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        r = mergeSort(slowNode.next);
        slowNode.next = null;
        l = mergeSort(head);
        return mergeList(l, r);
    }

    public static ListNode mergeList(ListNode left, ListNode right) {
        ListNode resultNode = new ListNode(0);
        ListNode curRes = resultNode;
        ListNode curL = left;
        ListNode curR = right;
        while (curL != null && curR != null) {
            if (curL.val <= curR.val) {
                curRes.next = new ListNode(curL.val);
                curL = curL.next;
            } else {
                curRes.next = new ListNode(curR.val);
                curR = curR.next;
            }
            curRes = curRes.next;
        }

        if (curL != null) {
            curRes.next = curL;
        }
        if (curR != null) {
            curRes.next = curR;
        }
        return resultNode;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }
    }

    public static int getKthElement(int[] nums1, int[] nums2, int k) {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */

        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int[] result = mergeArrays(nums1, nums2);
        System.out.println(Arrays.toString(result));
        int count = nums1.length + nums2.length;
        int mid = count / 2;
        if (count % 2 == 1) {
            return result[mid];
        } else {
            return (result[mid - 1] + result[mid]) / 2.0;
        }
    }

    public static int[] mergeArrays(int[] nums1, int[] nums2) {
        int[] resultArray = new int[nums1.length + nums2.length];
        int p1 = 0, p2 = 0, p = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] <= nums2[p2]) {
                resultArray[p++] = nums1[p1++];
            } else {
                resultArray[p++] = nums2[p2++];
            }
        }
        while (p1 < nums1.length) {
            resultArray[p++] = nums1[p1++];
        }
        while (p2 < nums2.length) {
            resultArray[p++] = nums2[p2++];
        }
        return resultArray;
    }

    /**
     * 给定两个整数数组preorder 和 inorder，其中preorder 是二叉树的先序遍历， inorder是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLength = preorder.length;
        int inLength = inorder.length;
        if (preLength != inLength) {
            throw new RuntimeException("Incorrect input data!");
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(preorder, 0, preLength - 1, map, 0, inLength - 1);
    }

    public static TreeNode buildTree(int[] preorder, int preLeft, int preRight, Map<Integer, Integer> map, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        int rootVal = preorder[preLeft];
        TreeNode root = new TreeNode(rootVal);
        int pIndex = map.get(rootVal);
        root.left = buildTree(preorder, preLeft + 1, preLeft + pIndex - inLeft, map, inLeft, pIndex - 1);
        root.right = buildTree(preorder, preLeft + pIndex - inLeft + 1, preRight, map, pIndex + 1, inRight);
        return root;
    }

    /**
     * 二叉树的最大深度
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int leftDp = maxDepth(root.left);
        int rightDp = maxDepth(root.right);
        return Math.max(leftDp, rightDp) + 1;
    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中，一棵高度平衡二叉树定义为：一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1
     */
    public static BalancedInfo isBalanced1(TreeNode root) {
        BalancedInfo balancedInfo = new BalancedInfo();
        if (root == null) {
            balancedInfo.isBalanced = true;
            balancedInfo.dp = 0;
            return balancedInfo;
        }
        BalancedInfo leftB = isBalanced1(root.left);
        BalancedInfo rightB = isBalanced1(root.right);
        if (leftB.isBalanced && rightB.isBalanced && Math.abs(leftB.dp - rightB.dp) <= 1) {
            balancedInfo.isBalanced = true;
        } else {
            balancedInfo.isBalanced = false;
        }
        balancedInfo.dp = Math.max(leftB.dp, rightB.dp) + 1;
        return balancedInfo;
    }

    public static class BalancedInfo {
        public boolean isBalanced;
        public int dp;
    }

    /**
     * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
     * 每条从根节点到叶节点的路径都代表一个数字：
     * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
     * 计算从根节点到叶节点生成的 所有数字之和 。
     * 叶节点 是指没有子节点的节点。
     */
    public static int sumNumbers(TreeNode root) {
        if (root == null)
            return 0;

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> numsQueue = new LinkedList<>();
        nodeQueue.add(root);
        numsQueue.add(root.val);
        TreeNode tempNode;
        int tempNum;
        int sum = 0;
        while (!nodeQueue.isEmpty()) {
            tempNode = nodeQueue.poll();
            tempNum = numsQueue.poll();
            if (tempNode.left == null && tempNode.right == null) {
                sum += tempNum;
            } else if (tempNode.left != null) {
                nodeQueue.add(tempNode.left);
                numsQueue.add(tempNum * 10 + tempNode.left.val);
            } else if (tempNode.right != null) {
                nodeQueue.add(tempNode.right);
                numsQueue.add(tempNum * 10 + tempNode.right.val);
            }
        }
        return sum;
    }

    /**
     * 深度优先搜索
     */
    public static int sumNumbers1(TreeNode root) {
        return dfs(root, 0);
    }

    public static int dfs(TreeNode root, int preSum) {
        if (root == null) {
            return 0;
        }
        int sum = preSum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return dfs(root.left, sum) + dfs(root.right, sum);
        }
    }

    /**
     * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
     */
    //深度优先遍历
    List<List<Integer>> lists = new ArrayList<>();
    Deque<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum1(TreeNode root, int targetSum) {
        dfs2(root, targetSum);
        return lists;
    }
    public void dfs23(TreeNode root, int target) {
        if (root == null)
            return;
        path.offerLast(root.val);
        if (root.left == null && root.right == null && target == 0) {
            lists.add(new LinkedList<>(path));
        }
        target -= root.val;
        dfs23(root.left, target);
        dfs23(root.right, target);
        path.pollLast();
    }

    public void dfs2(TreeNode root, int target) {
        if (root == null)
            return;
        path.offerLast(root.val);
        target -= root.val;
        if (root.left == null && root.right == null && target == 0) {
            lists.add(new LinkedList<>(path));
        }
        dfs2(root.left, target);
        dfs2(root.right, target);
        path.pollLast();
    }

    //广度优先遍历，利用HashMap存储节点父节点
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null)
            return lists;

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        HashMap<TreeNode, TreeNode> map = new HashMap<>();

//        map.put(root, null);
        nodeQueue.add(root);
        numQueue.add(root.val);
        TreeNode tempNode;
        int tempNum;
        while (!nodeQueue.isEmpty()) {
            tempNode = nodeQueue.poll();
            tempNum = numQueue.poll();
            if (tempNode.left == null && tempNode.right == null && tempNum == targetSum) {
                LinkedList<Integer> list = new LinkedList<>();
                list.addFirst(tempNode.val);
                while (map.containsKey(tempNode)) {
                    tempNode = map.get(tempNode);
                    if (map.get(tempNode) != null) {
                        list.addFirst(map.get(tempNode).val);
                    }
                }
                lists.add(list);
            }
            if (tempNode.left != null) {
                map.put(tempNode.left, tempNode);
                nodeQueue.add(tempNode.left);
                numQueue.add(tempNum + tempNode.left.val);
            }
            if (tempNode.right != null) {
                map.put(tempNode.right, tempNode);
                nodeQueue.add(tempNode.right);
                numQueue.add(tempNum + tempNode.right.val);
            }
        }
        return lists;
    }

    /**
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     */
    //3,1,2,-4,-2
    //3,1,2,6,6
    //-3,1,-2,6,6
    public static int firstMissingPositive1(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }
        int tempIndex;
        for (int i = 0; i < n; i++){
            tempIndex = Math.abs(nums[i]);
            if (tempIndex <= n) {
                nums[tempIndex - 1] = -Math.abs(nums[tempIndex - 1]);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }

    public static int firstMissingPositive11(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }
        System.out.println(Arrays.toString(nums));
        for (int i = 0; i < n; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (index < n) {
                nums[index] = -Math.abs(nums[index]);
            }
        }
        System.out.println(Arrays.toString(nums));
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }

    //暴力解法利用HashMap 空间复杂度是O(n)
    public static int firstMissingPositive2(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(i + 1)) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<String>();
        generate(res, "", 0, 0, n);
        return res;
    }

    //count1统计“(”的个数，count2统计“)”的个数
    public static void generate(List<String> res, String ans, int count1, int count2, int n) {
        if (count1 > n || count2 > n) return;
        if (count1 == n && count2 == n) res.add(ans);

        if (count1 >= count2) {
            generate(res, ans + "(", count1 + 1, count2, n);
            generate(res, ans + ")", count1, count2 + 1, n);
        }
    }

    List<List<Integer>> lists1 = new ArrayList<>();
    Deque<Integer> path2 = new LinkedList<>();
    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        dfs3(root, targetSum);
        return lists1;
    }

    public void dfs3(TreeNode root, int targetSum) {
        if (root == null)
            return;

        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            lists.add(new LinkedList<>(path));
        }
        dfs2(root.left, targetSum);
        dfs2(root.right, targetSum);
        path.pollLast();
    }
}
