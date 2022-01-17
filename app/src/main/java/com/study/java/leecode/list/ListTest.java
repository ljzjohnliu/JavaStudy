package com.study.java.leecode.list;

public class ListTest {

    public static void main(String[] args) {

    }

    /**
     * 获取链表倒数第K个节点
     * 快慢指针应用
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
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
}
