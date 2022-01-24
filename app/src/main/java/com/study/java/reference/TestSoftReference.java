package com.study.java.reference;

import android.provider.Browser;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class TestSoftReference {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        // 强引用
        String strongReference = new String("abc");
        // 软引用
        String str = new String("abc");
        SoftReference<String> softReference = new SoftReference<String>(str);
    }

    /**
     * 软引用可以和一个引用队列(ReferenceQueue)联合使用。如果软引用所引用对象被垃圾回收，JAVA虚拟机就会把这个软引用加入到与之关联的引用队列中。
     */
    private static void test2() {
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        String str = new String("abc");
        SoftReference<String> softReference = new SoftReference<>(str, referenceQueue);

        /**
         * 注意：软引用对象是在jvm内存不够的时候才会被回收，我们调用System.gc()方法只是起通知作用，
         * JVM什么时候扫描回收对象是JVM自己的状态决定的。就算扫描到软引用对象也不一定会回收它，只有内存不够的时候才会回收。
         *
         * 当内存不足时，JVM首先将软引用中的对象引用置为null，然后通知垃圾回收器进行回收：
         *     if(JVM内存不足) {
         *         // 将软引用中的对象引用置为null
         *         str = null;
         *         // 通知垃圾回收器进行回收
         *         System.gc();
         *     }
         * 也就是说，垃圾收集线程会在虚拟机抛出OutOfMemoryError之前回收软引用对象，而且虚拟机会尽可能优先回收长时间闲置不用的软引用对象。
         * 对那些刚构建的或刚使用过的“较新的”软对象会被虚拟机尽可能保留，这就是引入引用队列ReferenceQueue的原因。
         */
        str = null;
        // Notify GC
        System.gc();

        System.out.println(softReference.get()); // abc

        Reference<? extends String> reference = referenceQueue.poll();
        System.out.println(reference); //null
    }
}
