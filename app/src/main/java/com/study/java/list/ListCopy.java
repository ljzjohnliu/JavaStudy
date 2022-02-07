package com.study.java.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListCopy {
    public static void main(String[] args) {
        testCopy();
    }

    public static void testCopy() {
        List<String> srcList = new ArrayList<>();
        srcList.add("张三");
        srcList.add("李四");
        srcList.add("王五");
//        for (int i = 0; i < srcList.size(); i++) {
//            srcList.set(i, "Modify " + i);
//            System.out.println("----i = " + i + ", " + srcList.get(i));
//        }

        //如下这样做法并不能得到一个大小为srcList.size的list，实际上的size仍是0！
        //原因是 这样传入的size参数只是为该大小分配了足够的内存，它实际上没有定义元素。
//        List<String> descList = new ArrayList<>(srcList.size());
        List<String> descList = Arrays.asList(new String[srcList.size()]);
        System.out.println("descList.size = " + descList.size());
        Collections.copy(descList, srcList);
        for (int i = 0; i < descList.size(); i++){
            descList.set(i, "Modify " + i);
        }
        for (String src : srcList) {
            System.out.println(src);
        }
        for (String desc : descList) {
            System.out.println(desc);
        }
    }
}
