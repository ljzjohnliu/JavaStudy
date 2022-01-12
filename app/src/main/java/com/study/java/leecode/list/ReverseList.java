package com.study.java.leecode.list;

import java.util.Stack;

/**
 * 给定一个单链表的头结点pHead，长度为n，反转该链表后，返回新链表的表头。
 * <p>
 * 数据范围： n≤1000
 * 要求：空间复杂度 O(1) ，时间复杂度 O(n)。
 * <p>
 * 如当输入链表{1,2,3}时，
 * 经反转后，原链表变为{3,2,1}，所以对应的输出为{3,2,1}。
 * 以上转换过程如下图所示：
 * <p>
 * 示例1
 * 输入：
 * {1,2,3}
 * 返回值：
 * {3,2,1}
 * <p>
 * 示例2
 * 输入：
 * {}
 * 返回值：
 * {}
 * <p>
 * 说明：
 * 空链表则输出空
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode listNode4 = new ListNode(4, null);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode newNode = reverseList2(listNode1);
        while (newNode != null) {
            System.out.println(newNode.val);
            newNode = newNode.next;
        }
    }

    /**
     * 这个递归，返回值只是为了控制返回的是最后一个节点
     * 然后通过递归通过栈的特性，这里就是让它可以从最后一个节点开始把自己的子节点的子节点改成自己
     * 自己的子节点改为null
     */
    // 1 2 3  ---> 3 2 1
    public static ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newNode = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newNode;
    }

    // 1 2 3 4  --->4 3 2 1
    public static ListNode reverseList2(ListNode node) {
        Stack<ListNode> nodeStack = new Stack<>();
        ListNode head = null;
        //存入栈中，模拟递归开始的栈状态
        while (node != null) {
            nodeStack.push(node);
            node = node.next;
        }
        //特殊处理第一个栈顶元素（也就是反转前的最后一个元素，因为它位于最后，不需要反转，如果它参与下面的while，
        //因为它的下一个节点为空，如果getNode()， 那么为空指针异常）
        if ((!nodeStack.isEmpty())) {
            head = nodeStack.pop();
        }
        System.out.println(head.val);
        System.out.println(head.next);

        //排除以后就可以快乐的循环
        while (!nodeStack.isEmpty()) {
            ListNode tempNode = nodeStack.pop();
            tempNode.next.next = tempNode;//就是把自己挂在了自己的next的next上
            tempNode.next = null;
        }
        return head;
    }

    // 1 2 3  ---> 3 2 1
    public static ListNode reverseList3(ListNode head) {
        ListNode newHead = null;
        ListNode curNode = head;

        while (curNode != null) {
            ListNode tempNode = curNode.next;
            //把curNode反向往前指
            curNode.next = newHead;
            //newNode向后移动
            newHead = curNode;
            //curNode 向后移动
            curNode = tempNode;
        }
        return newHead;
    }
}
