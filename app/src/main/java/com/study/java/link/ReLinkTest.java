package com.study.java.link;

public class ReLinkTest {

    public static void main(String[] args) {
        Integer a = null;//Integer.MIN_VALUE;
//        String str = a.toString();
//        String str = Integer.toString(a);
        String str = String.valueOf(a);
        System.out.println("str = " + str);

        String str2 = "123";
        Integer a2 = null;
        if (str2 != null) {
            a2 = Integer.valueOf(str2);
        }
        System.out.println("a2 = " + a2);

        String obj = "12";
        Integer a3 = Integer.parseInt(obj);
        System.out.println("a3 = " + a3);
    }

    /**
     * 翻转一个单项链表 1->2->3->4->5->null =====> 5->4->3->2->1->null
     *
     * @param head
     * @return
     */
    public static Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node reHead = null;//定义新链表的头
        while (head != null) {
            Node cur = head.next;//记录下一个节点
            head.next = reHead;//将reHead节点链接到head节点上
            reHead = head;//让reHead指向head
            head = cur;//head指向下一个节点
        }
        return reHead;
    }
}
