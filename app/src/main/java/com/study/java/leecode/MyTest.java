package com.study.java.leecode;

import com.study.java.leecode.list.ListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MyTest {

    public static void main(String[] args) {
//        int[] nums = new int[]{2, 7, 11, 15};
//        int[] res = twoSum(nums, 9);
//        System.out.println(Arrays.toString(res));

        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        int money = maxProfit(prices);
        System.out.println(money);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode resNode = null;
        int n = 0;
        ListNode cur1 = headA;
        ListNode cur2 = headB;
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }

        if (n > 0) {
            cur1 = headA;
            cur2 = headB;
        } else {
            cur1 = headB;
            cur2 = headA;
        }

        for (int i = 0; i < Math.abs(n); i++) {
            cur1 = cur1.next;
        }

        while (cur1 != null) {
            if (cur1 == cur2) {
                resNode = cur1;
                break;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return resNode;
    }

//    输入：[7,1,5,3,6,4]
//    输出：5
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int res = 0;
        int preMin = prices[0];

        for (int i = 1; i < prices.length; i++) {
            res = Math.max(res, prices[i] - preMin);
            preMin = Math.min(preMin, prices[i]);
        }
        return res;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
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
            if (curEnd == tempNode) {
                lists.add(curLevel++, list);
                list = new ArrayList<>();
                curEnd = nextEnd;
            }
        }
        return lists;
    }

    public ListNode hasCycle(ListNode head) {
        boolean isHasCycle = false;
        ListNode resNode = null;
        ListNode slowNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
            if (slowNode == fastNode) {
                isHasCycle = true;
                fastNode = head;
                break;
            }
        }

        if (isHasCycle) {
            while (slowNode != fastNode) {
                slowNode = slowNode.next;
                fastNode = fastNode.next;
            }
            resNode = slowNode;
        }

        return fastNode;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
                return res;
            }
            map.put(nums[i], i);
        }
        return res;
    }
}
