package com.study.java.leecode;

public class ListNode {
    public int value;
    public ListNode next;

    public ListNode(int x) {
        value = x;
    }

    public ListNode(int x, ListNode node) {
        value = x;
        next = node;
    }
}
