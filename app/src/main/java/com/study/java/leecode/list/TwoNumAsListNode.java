package com.study.java.leecode.list;

import java.util.Stack;

public class TwoNumAsListNode {

    public static void main(String[] args) {
//        ListNode list1Node3 = new ListNode(3, null);
//        ListNode list1Node2 = new ListNode(4, list1Node3);
//        ListNode list1Node1 = new ListNode(2, list1Node2);
//
//        ListNode list2Node3 = new ListNode(4, null);
//        ListNode list2Node2 = new ListNode(6, list2Node3);
//        ListNode list2Node1 = new ListNode(5, list2Node2);
//
//        ListNode result = addTwoNum(list1Node1, list2Node1);
//        while (result != null) {
//            System.out.println(result.val);
//            result = result.next;
//        }

        ListNode list1Node3 = new ListNode(7, null);
        ListNode list1Node2 = new ListNode(3, list1Node3);
        ListNode list1Node1 = new ListNode(9, list1Node2);

        ListNode list2Node2 = new ListNode(3, null);
        ListNode list2Node1 = new ListNode(6, list2Node2);

        ListNode result = addInListUseStack(list1Node1, list2Node1);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    /**
     * 问题1、逆序存储的两个数字相加
     * 给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
     * 示例 1：
     * <p>
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     */
    public static ListNode addTwoNum(ListNode l1, ListNode l2) {
        ListNode resultNum = new ListNode(0);
        ListNode cur = resultNum;
        int sum;
        int addToHigh = 0;
        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + addToHigh;
            System.out.println("addToHigh = " + addToHigh + ", sum = " + sum);
            cur.next = new ListNode(sum % 10);
            addToHigh = sum > 9 ? 1 : 0;
            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null) {
            sum = l1.val + addToHigh;
            cur.next = new ListNode(sum % 10);
            addToHigh = sum > 9 ? 1 : 0;
            l1 = l1.next;
            cur = cur.next;
        }

        while (l2 != null) {
            sum = l2.val + addToHigh;
            cur.next = new ListNode(sum % 10);
            addToHigh = sum > 9 ? 1 : 0;
            l2 = l2.next;
            cur = cur.next;
        }
        cur.next = addToHigh > 0 ? new ListNode(addToHigh) : null;
        return resultNum.next;
    }

    /**
     * 问题2、正序存储俩数相加
     * 两种思路：a) 在问题1的基础上，把待处理list反转得出结果再反转即可
     *         b) 使用栈完成
     * 假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
     * 给定两个这种链表，请生成代表两个整数相加值的结果链表。
     * 数据范围：0≤n,m≤1000000，链表任意值 0≤val≤9
     * 要求：空间复杂度 O(n)，时间复杂度 O(n)
     *
     * 例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0。
     */
    public static ListNode addInListUseStack(ListNode head1, ListNode head2) {
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        ListNode list1 = head1;
        ListNode list2 = head2;
        while (list1 != null) {
            stack1.push(list1);
            list1 = list1.next;
        }

        while (list2 != null) {
            stack2.push(list2);
            list2 = list2.next;
        }

        System.out.println("stack1 size is = " + stack1.size() + ", stack2 size is = " + stack2.size());

        int sum;
        int addToHigh = 0;
        Stack<ListNode> resStack = new Stack<>();
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            sum = stack1.pop().val + stack2.pop().val + addToHigh;
            resStack.push(new ListNode(sum % 10));
            addToHigh = sum / 10;
        }
        System.out.println("resStack 1 size is = " + resStack.size());

        while (!stack1.isEmpty()) {
            sum = stack1.pop().val + addToHigh;
            resStack.push(new ListNode(sum % 10));
            addToHigh = sum / 10;
        }

        while (!stack2.isEmpty()) {
            sum = stack2.pop().val + addToHigh;
            resStack.push(new ListNode(sum % 10));
            addToHigh = sum / 10;
        }
        System.out.println("resStack 2 size is = " + resStack.size());
        if (addToHigh > 0) {
            resStack.push(new ListNode(addToHigh));
        }
        System.out.println("resStack 3 size is = " + resStack.size());
        ListNode resultNode = new ListNode(0);
        ListNode cur = resultNode;
        while (!resStack.isEmpty()) {
            cur.next = new ListNode(resStack.pop().val);
            cur = cur.next;
        }
        return resultNode.next;
    }

    /**
     * a) 在问题1的基础上，把待处理list反转得出结果再反转即可
     */
    public ListNode addInList(ListNode head1, ListNode head2) {
        ListNode reverseList1 = reverseList(head1);
        ListNode reverseList2 = reverseList(head2);
        ListNode reverseResultList = addInList2(reverseList1, reverseList2);
        return reverseList(reverseResultList);
    }

    public ListNode addInList2(ListNode head1, ListNode head2) {
        ListNode resultNode = new ListNode(0);
        ListNode curNode = resultNode;
        int sum;
        int addToHigh = 0;
        while (head1 != null && head2 != null) {
            sum = head1.val + head2.val + addToHigh;
            curNode.next = new ListNode(sum % 10);
            addToHigh = sum / 10;
            head1 = head1.next;
            head2 = head2.next;
            curNode = curNode.next;
        }

        while (head1 != null) {
            sum = head1.val + addToHigh;
            curNode.next = new ListNode(sum % 10);
            addToHigh = sum / 10;
            head1 = head1.next;
            curNode = curNode.next;
        }

        while (head2 != null) {
            sum = head2.val + addToHigh;
            curNode.next = new ListNode(sum % 10);
            addToHigh = sum / 10;
            head2 = head2.next;
            curNode = curNode.next;
        }

        curNode.next = addToHigh > 0 ? new ListNode(addToHigh) : null;
        return resultNode.next;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode temp = curNode.next;
            curNode.next = newHead;
            newHead = curNode;
            curNode = temp;
        }
        return newHead;
    }
}
