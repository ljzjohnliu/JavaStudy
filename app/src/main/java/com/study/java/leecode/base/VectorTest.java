package com.study.java.leecode.base;

import java.util.Vector;

/**
 * Vector 可实现自动增长的对象数组。线程安全的！！ Vector是有序的，可以重复的
 * java.util.vector提供了向量类(Vector)以实现类似动态数组的功能。
 * 创建了一个向量类的对象后，可以往其中随意插入不同类的对象，即不需顾及类型也不需预先选定向量的容量，并可以方便地进行查找。
 * <p>
 * 对于预先不知或者不愿预先定义数组大小，并且需要频繁地进行查找，插入，删除工作的情况，可以考虑使用向量类。
 * <p>
 * 向量类提供了三种构造方法：
 * <p>
 * public vector()
 * public vector(int initialCapacity,int capacityIncrement)
 * public vector(int initialCapacity)
 */
public class VectorTest {

    public static void main(String[] args) {
        Vector vector1 = new Vector();
        /*
        添加数据方法有 add, addElement, addAll
        需要注意的一点是Vector是对象数据，其存储的元素是对象，而不是基本数据类型
        如下 vector1.add(1) 其实自动装箱成了Integer类型
        add是实现List接口重写的方法，返回值为boolean。
        addElement是Vector类中的特有方法，返回值是void。
         */
        vector1.add(8);
        vector1.addElement(9);
        vector1.add(8);
        vector1.add(7);
        vector1.add(9);
        vector1.add("world");
        //将index处的对象设置成obj，原来的对象将被覆盖。
//        vector1.setElementAt("hello", 0);
        //在index指定的位置插入obj，原来对象以及此后的对象依次往后顺延。
        vector1.insertElementAt("hello", 0);

        for (int i = 0; i < vector1.size(); i++) {
            System.out.print(vector1.get(i) + "  ");
        }
        System.out.println();
        /*
        查询搜索功能：
        (1) indexOf(Object obj) 从向量头开始搜索obj,返回所遇到的第一个obj对应的下标，若不存在此obj,返回-1.
        (2) indexOf(Object obj, int index) 从index所表示的下标处开始搜索obj.
        (3) lastIndexOf(Object obj) 从向量尾部开始逆向搜索obj.
        (4) lastIndex(Object obj,int index) 从index所表示的下标处由尾至头逆向搜索obj.
        (5) firstElement() 获取向量对象中的首个obj
        (6) lastElement() 获取向量对象的最后一个obj
         */

        System.out.println(vector1.indexOf(8, 2));
        System.out.println(vector1.indexOf("world", 7));
        System.out.println(vector1.lastIndexOf(8));
        System.out.println(vector1.firstElement());
        System.out.println(vector1.lastElement());

        /*
        删除操作：
        (1) remove(Object obj), removeElement(Object obj) 从向量中删除obj,若有多个存在，则从向量头开始试，删除找到的第一个与obj相同的向量成员。
        (2) removeAllElement() 删除向量所有的对象
        (3) remove(int index), removeElementAt(int index) 删除index所指的地方的对象
        (3) removeAll(Collection<?> c)
         */
//        vector1.remove(new Integer(9));
//        vector1.removeAllElements();

        System.out.println("vector1 size is : " + vector1.size());
        vector1.setSize(5);//此方法用来定义向量的大小，若向量对象现有成员个数已经超过了newsize的值，则超过部分的多余元素会丢失。
        System.out.println("vector1 new size is : " + vector1.size());

        for (int i = 0; i < vector1.size(); i++) {
            System.out.print(vector1.get(i) + "  ");
        }
    }
}
