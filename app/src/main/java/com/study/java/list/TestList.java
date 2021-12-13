package com.study.java.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestList {
    public static int max = 1000000;//测试元素数

    public static void main(String[] args) {

        ArrayList<Object> arrList = new ArrayList<Object>();
        LinkedList<Object> linList = new LinkedList<Object>();

        System.out.println("测试元素数:" + max);
        System.out.println("ArrayList插入消耗的时间：" + insertTime(arrList) + "ms");
        System.out.println("LinkedList插入消耗的时间：" + insertTime(linList) + "ms");

//        System.out.println("\nArrayList访问消耗的时间：" + getTime(arrList) + "ms");
//        System.out.println("LinkedList访问消耗的时间：" + getTime(linList) + "ms");
//
//        System.out.println("\nArrayList删除消耗的时间：" + delTime_v2(arrList) + "ms");
//        System.out.println("LinkedList删除消耗的时间：" + delTime_v2(linList) + "ms");

    }

    public static long insertTime(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            list.add(i); // 逐个插入 max 个元素
        }
        return System.currentTimeMillis() - time;
    }

    public static long getTime(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            int index = Collections.binarySearch(list, list.get(i));
            if (index != i) {
                System.out.println("ERROR!");
            }
        }
        return System.currentTimeMillis() - time;
    }

    public static long delTime_v2(List list) {
        long time = System.currentTimeMillis();

        while (checkListSize(list)) {
            if (list instanceof LinkedList) {
                ((LinkedList) list).removeFirst();
            } else if (list instanceof ArrayList) {
                list.remove(0);
            }
        }

        return System.currentTimeMillis() - time;
    }

    public static boolean checkListSize(List list) {
        if (list.size() <= 0)
            return false;
        else
            return true;
    }
}

