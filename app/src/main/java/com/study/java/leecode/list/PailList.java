package com.study.java.leecode.list;

import java.util.Stack;

/**
 * 判断一个链表是否为回文结构
 * 描述
 * 给定一个链表，请判断该链表是否为回文结构。
 * 回文是指该字符串正序逆序完全一致。
 * 数据范围： 链表节点数 0≤n≤10^7，链表中每个节点的值满足 ∣val∣≤10^7
 * <p>
 * 示例1
 * 输入： {1}
 * 返回值：true
 * 示例2
 * 输入：{2,1}
 * 返回值：false
 * 说明：2->1
 * 示例3
 * 输入： {1,2,2,1}
 * 返回值：true
 * 说明：1->2->2->1
 */
public class PailList {

    public static void main(String[] args) {
        ListNode listNode5 = new ListNode(1, null);
        ListNode listNode4 = new ListNode(2, listNode5);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
        System.out.println("isPail = " + isPail9(listNode1));
    }

    private static boolean isPail9(ListNode head) {
        ListNode curNode = head;
        Stack<ListNode> stack = new Stack<>();
        while (curNode != null) {
            stack.push(curNode);
            curNode = curNode.next;
        }
        boolean isPail = true;
        curNode = head;
        while (curNode != null) {
            if (curNode.val != stack.pop().val) {
                isPail = false;
            }
            curNode = curNode.next;
        }
        return isPail;
    }

    /**
     * 使用压栈方式判断 空间复杂度O(n)
     */
    public static boolean isPail1(ListNode head) {
        if (head == null) {
            return true;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode curNode = head;
        while (curNode != null) {
            stack.push(curNode);
            curNode = curNode.next;
        }

        boolean isPail = true;
        curNode = head;
        while (curNode != null) {
            if (curNode.val != stack.pop().val) {
                isPail = false;
            }
            curNode = curNode.next;
        }
        return isPail;
    }

    /**
     * 版本1的优化方式，只把后半部分压栈  空间复杂度O(n/2)
     */
    public static boolean isPail2(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slowNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
            break;
        }
        Stack<ListNode> stack = new Stack<>();
        while (slowNode != null) {
            slowNode = slowNode.next;
            if (slowNode != null) {
                stack.push(slowNode);
            }
        }

        boolean isPail = true;
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                isPail = false;
            }
            head = head.next;
        }
        return isPail;
    }

    /**
     * 不使用栈结构，完全自己调整指针完成，空间复杂度O(1)
     */
    public static boolean isPail(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slowNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null && fastNode.next.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }

        System.out.println("slowNode = " + slowNode.val + ", next = " + slowNode.next.val);
        boolean isPail = true;
        ListNode markNode = slowNode;
        ListNode newHead = null;
        while (slowNode != null) {
            ListNode temp = slowNode.next;
            slowNode.next = newHead;
            newHead = slowNode;
            slowNode = temp;
        }

        ListNode curNode = head;
        ListNode lastCurNode = newHead;
        while (curNode != null && lastCurNode != null) {
            if (curNode.val != lastCurNode.val) {
                isPail = false;
                break;
            }
            curNode = curNode.next;
            lastCurNode = lastCurNode.next;
        }

        ListNode afterNode = null;
        System.out.println(newHead != markNode);
        while (newHead != null) {
            ListNode temp = newHead.next;
            newHead.next = afterNode;
            afterNode = newHead;
            newHead = temp;
        }

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }

        return isPail;
    }
}
