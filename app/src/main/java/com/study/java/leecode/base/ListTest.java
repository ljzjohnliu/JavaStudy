package com.study.java.leecode.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection两个子类List和set
 * 这俩区别三个方面：
 * (1) 有序性：List保证按插入顺序排序，Set存储和取出顺序不一致
 * (2) 唯一性：List可重复，Set元素唯一
 * (3)获取元素：List可通过索引操作元素，Set不能根据索引获取元素
 * <p>
 * List实现类有
 * (1) LinkedList 底层数据结构是链表，查询慢，增删快，线程不安全，效率高，可以存储重复元素
 * (2) ArrayList 底层数据结构是数组，查询快，增删慢，线程不安全，效率高，可以存储重复元素
 * (3) Vector(子类有Stack) 底层数据结构是数组，查询快，增删慢，线程安全，效率低，可以存储重复元素
 * <p>
 * 总结：线程安全集合类与非线程安全集合类
 * LinkedList、ArrayList、HashSet是非线程安全的，Vector是线程安全的;
 * HashMap是非线程安全的，HashTable是线程安全的;
 * StringBuilder是非线程安全的，StringBuffer是线程安全的。
 * <p>
 * 数据结构
 * ArrayXxx:底层数据结构是数组，查询快，增删慢
 * LinkedXxx:底层数据结构是链表，查询慢，增删快
 * HashXxx:底层数据结构是哈希表。依赖两个方法：hashCode()和equals()
 * TreeXxx:底层数据结构是二叉树。两种方式排序：自然排序和比较器排序
 */
public class ListTest {

    public static final int COUNT = 600000;
    public static final int INSERT_COUNT = 600;
    public static final int DELETE_COUNT = 6000;

    public static void main(String[] args) {
        /**
         * ArrayList底层是数组实现，LinkedList底层是链表实现
         * 理论上数组存储速度是要优于链表的，但是出现扩容情况的话，可能某些情况下打印结果是ArrayList是要比LinkedList更耗时！
         * 不过总的来说ArrayList存储速度是要优于LinkedList的
         */
        List<String> arrayList = new ArrayList<>();
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            arrayList.add("array " + i);
        }
        System.out.println("Array list cost time is = " + (System.currentTimeMillis() - startTime1));

        long startTime2 = System.currentTimeMillis();
        List<String> linkedList = new LinkedList<>();
        for (int i = 0; i < COUNT; i++) {
            linkedList.add("array " + i);
        }
        System.out.println("linked list cost time is = " + (System.currentTimeMillis() - startTime2));

        /**
         * 采用下标遍历的话，ArrayList和LinkedList的性能差别会很大
         * 其他两种遍历方式，没什么区别
         */
        long traStartTime1 = System.currentTimeMillis();
//        traversalList1(arrayList);
//        traversalList2(arrayList);
        traversalList3(arrayList);
        System.out.println("Traversal Array list cost time is = " + (System.currentTimeMillis() - traStartTime1));

        long traStartTime2 = System.currentTimeMillis();
//        traversalList1(linkedList);
//        traversalList2(linkedList);
        traversalList3(linkedList);
        System.out.println("Traversal Linked list cost time is = " + (System.currentTimeMillis() - traStartTime2));

        /**
         * 修改元素是否LinkedList优于ArrayList的话 一定是index偏前！
         */
        long insertStartTime1 = System.currentTimeMillis();
        for (int i = 0; i < INSERT_COUNT; i++) {
            arrayList.set(COUNT / 2, "array " + i);
        }
        System.out.println("Insert Array list cost time is = " + (System.currentTimeMillis() - insertStartTime1));

        long insertStartTime2 = System.currentTimeMillis();
        for (int i = 0; i < INSERT_COUNT; i++) {
            linkedList.set(COUNT / 2, "array " + i);
        }
        System.out.println("Insert Linked list cost time is = " + (System.currentTimeMillis() - insertStartTime2));

        /**
         * 删除元素是否LinkedList优于ArrayList的话 一定是index偏前！
         */
        long delStartTime1 = System.currentTimeMillis();
        for (int i = 500000; i < 500000 + DELETE_COUNT; i++) {
            arrayList.remove(i);
        }
        System.out.println("Delete Array list cost time is = " + (System.currentTimeMillis() - delStartTime1));

        long delStartTime2 = System.currentTimeMillis();
        for (int i = 500000; i < 500000 + DELETE_COUNT; i++) {
            linkedList.remove(i);
        }
        System.out.println("Delete Linked list cost time is = " + (System.currentTimeMillis() - delStartTime2));

//        testArrayList();
//        testLinkedList();
    }

    public static void testArrayList() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("aaa");//直接在后面添加元素
        arrayList.add("bbb");
        arrayList.add("ccc");
        arrayList.add("ddd");
        arrayList.add(0, "!!!!");//在列表的指定位置插入指定元素,原来对象以及此后的对象依次往后顺延
//        arrayList.set(0, "@@@@");//将index处的对象设置成obj，原来的对象将被覆盖。
        System.out.println(Arrays.toString(arrayList.toArray()));
        System.out.println("arrayList size is : " + arrayList.size() + ", get position 3 value is = " + arrayList.get(3));

        System.out.println("contains zzz is : " + arrayList.contains("zzz"));
        System.out.println("contains bbb is : " + arrayList.contains("bbb"));
        System.out.println("remove zzz is : " + arrayList.remove("zzz"));
        System.out.println("remove 2 is : " + arrayList.remove(2));//index要在有效范围内，不然会越界崩溃

        System.out.println(Arrays.toString(arrayList.toArray()));

        traversalList1(arrayList);
        System.out.println("\n--------------foreach遍历方式----------------");
        traversalList2(arrayList);
        System.out.println("\n--------------iterator遍历方式----------------");
        traversalList3(arrayList);
    }

    /**
     * ArrayList LinkedList的基本操作是一致的，因为他们都实现了List接口
     */
    public static void testLinkedList() {
        List<String> linkedList = new LinkedList<>();
        linkedList.add("aaa");//直接在后面添加元素
        linkedList.add("bbb");
        linkedList.add("ccc");
        linkedList.add("ddd");
        linkedList.add(0, "!!!!");//在列表的指定位置插入指定元素,原来对象以及此后的对象依次往后顺延
//        arrayList.set(0, "@@@@");//将index处的对象设置成obj，原来的对象将被覆盖。
        System.out.println(Arrays.toString(linkedList.toArray()));
        System.out.println("arrayList size is : " + linkedList.size() + ", get position 3 value is = " + linkedList.get(3));

        System.out.println("contains zzz is : " + linkedList.contains("zzz"));
        System.out.println("contains bbb is : " + linkedList.contains("bbb"));
        System.out.println("remove zzz is : " + linkedList.remove("zzz"));
        System.out.println("remove 2 is : " + linkedList.remove(2));//index要在有效范围内，不然会越界崩溃

        System.out.println(Arrays.toString(linkedList.toArray()));

        traversalList1(linkedList);
        System.out.println("\n--------------foreach遍历方式----------------");
        traversalList2(linkedList);
        System.out.println("\n--------------iterator遍历方式----------------");
        traversalList3(linkedList);
    }

    /**
     * 遍历方式1 最基础的遍历方式：for循环，指定下标长度，使用List集合的size()方法，进行for循环遍历
     */
    public static void traversalList1(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
//            System.out.print(list.get(i) + "  ");
            //这种遍历方式可以进行如下操作，遍历过程中删除元素等操作
//            if (i == 2) {
//                list.remove(2);
//            }
        }
    }

    /**
     * 遍历方式2 较为简洁的遍历方式：使用foreach遍历List，但不能对某一个元素进行操作（这种方法在遍历数组和Map集合的时候同样适用）
     */
    public static void traversalList2(List<String> list) {
        for (String str : list) {
            str = "haha";
//            System.out.print(str + "  ");
            //这种遍历方式不能对某一个元素进行操作,如下删除操作会报错ConcurrentModificationException
//            if ("aaa".equals(str)) {
//                list.remove("aaa");
//            }
        }
    }

    /**
     * 遍历方式3 适用迭代器Iterator遍历：直接根据List集合的自动遍历
     */
    public static void traversalList3(List list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
//            System.out.print(str + "  ");
            //这种遍历方式不能对某一个元素进行操作,如下删除操作会报错ConcurrentModificationException
//            if ("aaa".equals(str)) {
//                list.remove("aaa");
//            }
        }
    }
}
