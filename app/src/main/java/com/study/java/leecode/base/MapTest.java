package com.study.java.leecode.base;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map是一个接口
 * 两个常用实现类：HashMap 和 Hashtable，区别点如下：
 * (1) 线程安全：Hashtable是线程同步的，用synchronized实现；HashMap是非线程安全的
 * (2) 空值：HashMap可以让你将一个空值作为一个条目的key或value，但Hashtable是不能放入空值的；
 * HashMap最多只有一个key值是null，可以有多个value是null
 * (3) 性能：HashMap性能最好，Hashtable性能最差
 * (4) 用作key的对象必须实现hashCode和equals方法；不能保证其中的键值对的顺序；尽量不要使用可变对象作为key值。
 *
 * TreeMap：非线程安全基于红黑树实现。TreeMap没有调优选项，因为该树总处于平衡状态。
 *
 * 适用场景分析：
 * HashMap和HashTable:HashMap去掉了HashTable的contains方法，但是加上了containsValue()和containsKey()方法。
 * HashTable同步的，而HashMap是非同步的，效率上比HashTable要高。HashMap允许空键值，而HashTable不允许。
 *
 * HashMap：适用于Map中插入、删除和定位元素。
 * Treemap：适用于按自然顺序或自定义顺序遍历键(key)。
 */
public class MapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.values();
    }
}
