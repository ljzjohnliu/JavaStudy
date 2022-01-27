package com.study.java.leecode.list;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class ListTest {

    public static void main(String[] args) {
//        ListNode list1Node5 = new ListNode(3, null);
//        ListNode list1Node4 = new ListNode(9, list1Node5);
//        ListNode list1Node3 = new ListNode(5, list1Node4);
//        ListNode list1Node2 = new ListNode(7, list1Node3);
//        ListNode list1Node1 = new ListNode(4, list1Node2);
//
//        ListNode node = getKthFromEnd(list1Node1, 1);
//        System.out.println(node.val);

        ListNode list1Node7 = new ListNode(5, null);
        ListNode list1Node6 = new ListNode(4, list1Node7);
        ListNode list1Node5 = new ListNode(4, list1Node6);
        ListNode list1Node4 = new ListNode(3, list1Node5);
        ListNode list1Node3 = new ListNode(3, list1Node4);
        ListNode list1Node2 = new ListNode(2, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);
        ListNode result = deleteDuplicates(list1Node1);
        while (result != null) {
            System.out.print(result.val);
            result = result.next;
        }
    }

    /**
     * 获取链表倒数第K个节点
     * 快慢指针应用
     */
    public static ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode slowNode = head;
        ListNode fastNode = head;
        for (int i = 0; i < k; i++) {
            fastNode = fastNode.next;
        }
        while (fastNode != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next;
        }
        return slowNode;
    }

    /**
     * 删除排序链表中的重复元素 II
     * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中没有重复出现的数字。
     * 返回同样按升序排列的结果链表。
     * 示例 1：输入：head = [1,2,3,3,4,4,5]
     * 输出：[1,2,5]
     */
    public static ListNode deleteDuplicates(ListNode head) {
        Deque<ListNode> deque = new LinkedList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        deque.addLast(new ListNode(head.val));
        ListNode curNode = head.next;
        ListNode tailNode;
        while (curNode != null) {
            tailNode = deque.pollLast();
            System.out.println("tail is : " + tailNode);
            if (curNode.val == tailNode.val || hashSet.contains(curNode.val)) {
                hashSet.add(curNode.val);
            } else {
                deque.addLast(tailNode);
                deque.addLast(new ListNode(curNode.val));
            }
        }
        ListNode newHead = new ListNode(0);
        ListNode newCur = newHead;
        while (!deque.isEmpty()) {
            newCur.next = deque.pollFirst();
            newCur = newCur.next;
        }
        return newHead.next;
    }
}
