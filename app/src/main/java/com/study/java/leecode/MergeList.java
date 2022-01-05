package com.study.java.leecode;

/**
 * 题目描述
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例：
 *
 * 输入：1->3->4, 2->5->6
 * 输出：1->2->3->4->5->6
 * 题目解析
 * 首先，设定一个虚拟节点 dummy 用来存储结果，循环对比 L1 和 L2 节点上的数字，通过调整 p节点的 next 指针来调整 dummy 的结果。
 * 如果 L1 当前位置的值小于等于 L2 ，我们就把  L1 的值接在  dummy 节点的后面同时将  L1 指针往后移一个
 * 如果 L2 当前位置的值小于 L2 ，我们就把  L2 的值接在  p 节点的后面同时将  L2 指针往后移一个
 * 不管我们将哪一个元素接在了 p 节点后面，都需要向后移一个元素
 * 重复以上过程，直到  L1 或者  L2 指向了 null
 * 在循环终止的时候，  L1 和  L2 至多有一个是非空的。由于输入的两个链表都是有序的，所以不管哪个链表是非空的，
 * 它包含的所有元素都比前面已经合并链表中的所有元素都要大。这意味着我们只需要简单地将非空链表接在合并链表的后面，并返回合并链表。
 */
public class MergeList {
    public static void main(String[] args) {
        ListNode list1Node3 = new ListNode(4, null);
        ListNode list1Node2 = new ListNode(3, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);

        ListNode list2Node3 = new ListNode(6, null);
        ListNode list2Node2 = new ListNode(5, list2Node3);
        ListNode list2Node1 = new ListNode(2, list2Node2);

        ListNode result = merge(list1Node1, list2Node1);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    public static ListNode merge(ListNode list1, ListNode list2) {
        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        if (list1 != null) {
            cur.next = list1;
        }

        if (list2 != null) {
            cur.next = list2;
        }
        return newHead.next;
    }
}
