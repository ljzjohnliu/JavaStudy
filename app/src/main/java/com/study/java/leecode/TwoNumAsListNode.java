package com.study.java.leecode;

/**
 * 给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
 * 示例 1：
 *
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 */
public class TwoNumAsListNode {

    public static void main(String[] args) {
        ListNode list1Node3 = new ListNode(3, null);
        ListNode list1Node2 = new ListNode(4, list1Node3);
        ListNode list1Node1 = new ListNode(2, list1Node2);

        ListNode list2Node3 = new ListNode(4, null);
        ListNode list2Node2 = new ListNode(6, list2Node3);
        ListNode list2Node1 = new ListNode(5, list2Node2);

        ListNode result = addTwoNum(list1Node1, list2Node1);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    public static ListNode addTwoNum(ListNode l1, ListNode l2) {
        ListNode resultNum = new ListNode(0);
        ListNode cur = resultNum;
        int sum;
        int addToHigh = 0;
        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + addToHigh;
            System.out.println("addToHigh = " + addToHigh + ", sum = " + sum);
            cur.next = new ListNode(sum%10);
            addToHigh = sum > 9 ? 1 : 0;
            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null) {
            sum = l1.val + addToHigh;
            cur.next = new ListNode(sum%10);
            addToHigh = sum > 9 ? 1 : 0;
            l1 = l1.next;
            cur = cur.next;
        }

        while (l2 != null) {
            sum = l2.val + addToHigh;
            cur.next = new ListNode(sum%10);
            addToHigh = sum > 9 ? 1 : 0;
            l2 = l2.next;
            cur = cur.next;
        }
        cur.next = addToHigh > 0 ? new ListNode(addToHigh) : null;
        return resultNum.next;
    }
}
