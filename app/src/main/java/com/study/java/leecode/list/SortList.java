package com.study.java.leecode.list;

import java.util.ArrayList;

/**
 * 单链表的排序
 * 描述 给定一个节点数为n的无序单链表，对其按升序排序。
 *
 * 数据范围：0<n≤100000
 * 要求：空间复杂度 O(n)，时间复杂度 O(nlogn)
 *
 * 示例1
 * 输入：[1,3,2,4,5]
 * 返回值：{1,2,3,4,5}
 * 示例2
 * 输入：[-1,0,-2]
 * 返回值：{-2,-1,0}
 */
public class SortList {

    public static void main(String[] args) {
        ListNode list1Node5 = new ListNode(4, null);
        ListNode list1Node4 = new ListNode(5, list1Node5);
        ListNode list1Node3 = new ListNode(2, list1Node4);
        ListNode list1Node2 = new ListNode(3, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);

        ListNode result = sortInList(list1Node1);
        while (result != null) {
            System.out.print(result.val + "  ");
            result = result.next;
        }
    }

    /**
     * 使用归并排链表
     */
    public static ListNode sortInList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        //快慢指针这里需要注意定制化
        ListNode slowNode = head;
        ListNode fastNode = head.next;
        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        //找到slowNode即中间节点
        ListNode temp = slowNode.next;
        slowNode.next = null;
        ListNode left = sortInList(head);
        ListNode right = sortInList(temp);
        return mergeList(left, right);
    }

    public static ListNode mergeList(ListNode left, ListNode right) {
        ListNode resNode = new ListNode(0);
        while (left != null && right != null) {
            if (left.val <= right.val) {
                resNode.next = new ListNode(left.val);
                left = left.next;
            } else {
                resNode.next = new ListNode(right.val);
                right = right.next;
            }
            resNode = resNode.next;
        }
        if (left != null) {
            resNode.next = left;
        }
        if (right != null) {
            resNode.next = right;
        }
        return resNode.next;
    }

    public static ListNode sortInList1(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        ArrayList<Integer> resList = sortArrayList(list, 0, list.size() - 1);
        ListNode sortNode = new ListNode(0);
        ListNode cur = sortNode;
        for (int i = 0; i < resList.size(); i++) {
            cur.next = new ListNode(resList.get(i));
            cur = cur.next;
        }
        return sortNode.next;
    }



    public static ArrayList<Integer> sortArrayList(ArrayList<Integer> list, int left, int right) {
        ArrayList<Integer> resList = new ArrayList<>();
        if (left == right) {
            resList.add(list.get(left));
            return resList;
        }

        int mid = left + (right - left) / 2;
        ArrayList<Integer> leftList = sortArrayList(list, left, mid);
        ArrayList<Integer> rightList = sortArrayList(list, mid + 1, right);
        return mergeList(leftList, rightList);
    }

    public static ArrayList<Integer> mergeList(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        ArrayList<Integer> resList = new ArrayList<>();
        int pL = 0;
        int pR = 0;
        while (pL < leftList.size() && pR < rightList.size()) {
            if (leftList.get(pL) < rightList.get(pR)) {
                resList.add(leftList.get(pL++));
            } else {
                resList.add(rightList.get(pR++));
            }
        }

        while (pL < leftList.size()) {
            resList.add(leftList.get(pL++));
        }

        while (pR < rightList.size()) {
            resList.add(rightList.get(pR++));
        }
        return resList;
    }
}
