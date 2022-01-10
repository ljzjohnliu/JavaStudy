package com.study.java.leecode.base;

import java.util.HashSet;
import java.util.Set;

/**
 * Set实现类有：
 * (1) HashSet 底层数据结构采用哈希表实现，元素无序且唯一，线程不安全，效率高，可以存储null元素，
 * 元素的唯一性是靠所存储元素类型是否重写hashCode()和equals()方法来保证的，如果没有重写这两个方法，则无法保证元素的唯一性。
 * 具体实现唯一性的比较过程：存储元素首先会使用hash()算法函数生成一个int类型hashCode散列值，然后已经的所存储的元素的hashCode值比较，
 * 如果hashCode不相等，则所存储的两个对象一定不相等，此时存储当前的新的hashCode值处的元素对象；
 * 如果hashCode相等，存储元素的对象还是不一定相等，此时会调用equals()方法判断两个对象的内容是否相等，如果内容相等，那么就是同一个对象，无需存储；
 * 如果比较的内容不相等，那么就是不同的对象，就该存储了，此时就要采用哈希的解决地址冲突算法，在当前hashCode值处类似一个新的链表，
 * 在同一个hashCode值的后面存储存储不同的对象，这样就保证了元素的唯一性。
 * Set的实现类的集合对象中不能够有重复元素，HashSet也一样他是使用了一种标识来确定元素的不重复，HashSet用一种算法来保证HashSet中的元素是不重复的，
 * HashSet采用哈希算法，底层用数组存储数据。默认初始化容量16，加载因子0.75。
 * Object类中的hashCode()的方法是所有子类都会继承这个方法，这个方法会用Hash算法算出一个Hash（哈希）码值返回，HashSet会用Hash码值去和数组长度取模，
 * 模（这个模就是对象要存放在数组中的位置）相同时才会判断数组中的元素和要加入的对象的内容是否相同，如果不同才会添加进去。
 * Hash算法是一种散列算法。
 * (2) LinkedHashSet 底层数据结构采用链表和哈希表共同实现，链表保证了元素的顺序与存储顺序一致，哈希表保证了元素的唯一性。线程不安全，效率高。
 * (3) TreeSet 底层数据结构采用二叉树来实现，元素唯一且已经排好序，不允许放入null值；唯一性同样需要重写hashCode和equals()方法，二叉树结构保证了元素的有序性。
 * 根据构造方法不同，分为自然排序（无参构造）和比较器排序（有参构造），自然排序要求元素必须实现Compareable接口，并重写里面的compareTo()方法，
 * 元素通过比较返回的int值来判断排序序列，返回0说明两个对象相同，不需要存储；比较器排需要在TreeSet初始化是时候传入一个实现Comparator接口的比较器对象，
 * 或者采用匿名内部类的方式new一个Comparator对象，重写里面的compare()方法；
 *（4）小结：Set具有与Collection完全一样的接口，因此没有任何额外的功能，不像前面有两个不同的List。实际上Set就是Collection,只是行为不同。
 * (这是继承与多态思想的典型应用：表现不同的行为。)Set不保存重复的元素。
 * Set 存入Set的每个元素都必须是唯一的，因为Set不保存重复元素。加入Set的元素必须定义equals()方法以确保对象的唯一性。
 * Set与Collection有完全一样的接口。Set接口不保证维护元素的次序。
 */
public class SetTest {

    public static void main(String[] args) {
        HashSet set = new HashSet();
    }
}
