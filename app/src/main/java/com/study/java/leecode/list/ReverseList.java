package com.study.java.leecode.list;

import java.util.Stack;

/**
 * 给定一个单链表的头结点pHead，长度为n，反转该链表后，返回新链表的表头。
 * 数据范围： n≤1000
 * 要求：空间复杂度 O(1) ，时间复杂度 O(n)。
 * 如当输入链表{1,2,3}时，经反转后，原链表变为{3,2,1}，所以对应的输出为{3,2,1}。
 * 示例1
 * 输入：{1,2,3}
 * 返回值：{3,2,1}
 * 示例2
 * 输入：{}
 * 返回值：{}
 * 说明：空链表则输出空
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode listNode5 = new ListNode(5, null);
        ListNode listNode4 = new ListNode(4, listNode5);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
//        ListNode newNode = reverseList3(listNode1);
//        while (newNode != null) {
//            System.out.println(newNode.val);
//            newNode = newNode.next;
//        }
        ListNode newNode = reverseKGroup(listNode1, 3);
        System.out.println();
        while (newNode != null) {
            System.out.print(newNode.val + " ");
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

    /**
     * 链表中的节点每k个一组翻转
     * 描述
     * 将给出的链表中的节点每 k 个一组翻转，返回翻转后的链表
     * 如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
     * 你不能更改节点中的值，只能更改节点本身。
     *
     * 数据范围：0≤n≤2000，1≤k≤2000 ，链表中每个元素都满足0≤val≤1000
     * 要求空间复杂度 O(1)，时间复杂度 O(n)
     * 例如：
     * 给定的链表是 1->2->3->4->5
     * 对于 k = 2, 你应该返回 2→1→4→3→5
     * 对于 k = 3, 你应该返回 3→2→1→4→5
     *
     * 示例1
     * 输入：{1,2,3,4,5},2
     * 返回值：{2,1,4,3,5}
     * 示例2
     * 输入：{},1
     * 返回值：{}
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        int length = getListLength(head);
        if (k <= 1 || k > length) {
            return head;
        }

        int count = length / k;//有count段需要反转
        ListNode resNode = new ListNode(-1);//返回值
        ListNode cur = head, res_cur = resNode;
        ListNode tar = null;//用于保存现场
        //每遍历k个节点都要：保存现场、反转、拼到结果链表上、恢复现场。
        for (int i = 0; i < count; i++) {
            int n = 1;
            ListNode tem = cur;//记录反转部分的头节点，方便反转操作
            while (n < k) {//遍历k个节点来反转
                cur = cur.next;
                n += 1;
            }

            tar = cur.next;//保存现场，最后一趟反转后，tar指向的是不用反转的链表段的头节点，或null
            cur.next = null;//断链
            res_cur.next = ReverseList(tem);//反转，并拼到结果链表上
            while (res_cur.next != null) res_cur = res_cur.next;//结果链表的遍历指针总是要指向链尾
            cur = tar;//恢复现场
        }
        res_cur.next = tar;//接上剩下几个不用反转的节点
        return resNode.next;
    }

    public static int getListLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * 反转链表
     */
    public static ListNode ReverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
