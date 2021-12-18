package com.study.java.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
    static class Student {
        public int age;
        public String name;
        public int level;

        public Student(int age, String name, int level) {
            super();
            this.age = age;
            this.name = name;
            this.level = level;
        }

        @Override
        public String toString() {
            return "\nStudent [age=" + age + ", name=" + name + ", level=" + level + "]";
        }
    }

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(4, "StudentD", 1));
        list.add(new Student(5, "StudentA", 3));
        list.add(new Student(6, "StudentB", 2));
        list.add(new Student(7, "StudentC", 3));

        //当compare的返回值小于0时，会将  o2  和  o1 （compare()中的两个参数）交换顺序，大于等于0不会变换顺序；
        Comparator<Student> levelComparator = (o1, o2) -> {
            if (o1.level == 3) {
                if (o2.level == 3) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2.level == 3) {
                return 1;
            } else {
                return 0;
            }
        };

        Collections.sort(list, new Comparator<Student>() {

            @Override
            public int compare(Student o1, Student o2) {
                int brandResult = levelComparator.compare(o1, o2);
                if (brandResult != 0) {
                    return brandResult;
                }

                return o2.age - o1.age;
            }
        });
        System.out.println("给学生按照年龄倒序：" + list);
//        Collections.sort(list, new Comparator<Student>() {
//
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o1.name.compareTo(o2.name);
//            }
//        });
//        System.out.println("给学生按名字字母顺序排序：" + list);

        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2, 4};
        double mid = findMedianSortedArrays(nums1, nums2);
        System.out.println(" mid = " + mid);
    }

    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。
     *
     * 请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1L = nums1.length;
        int nums2L = nums2.length;
        double result = 0;
        int i = 0, j = 0, k = 0;
        int[] num = new int[nums1L + nums2L];
        while (i < nums1L && j < nums2L) {
            if (nums1[i] < nums2[j]) num[k++] = nums1[i++];
            else num[k++] = nums2[j++];
        }
        while (i == nums1L && j < nums2L) {
            num[k++] = nums2[j++];
        }
        while (i < nums1L && j == nums2L) {
            num[k++] = nums1[i++];
        }
        if (num.length % 2 == 0) {
            result = ((double) (num[num.length / 2] + num[num.length / 2 - 1])) / 2;
        } else {
            result = (double) (num[num.length / 2]);
        }
        return result;
    }
}