package com.study.java.leecode;

import com.study.java.leecode.list.ListNode;

import java.util.Arrays;

public class ReviewTest {

    public static void testFindMissingNum() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
                61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                71, 72, 73, 74, 75, 76, 78, 79, 80,
                81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                91, 92, 93, 94, 95, 96, 97, 98, 99, 100};

        int find = findMissingNumber(nums);
        System.out.println("findMissingNumber is = " + find);
    }

    public static void testLongestSubstring() {
        String s1 = "abcabcbb";
        String s2 = "bbbbb";
        String s3 = "pwwkew";
        String s4 = "";
        String s5 = null;
        String s6 = "tmmzuxt";
        System.out.println(s1 + " longest length is : " + lengthOfLongestSubstring(s1));
        System.out.println(s2 + " longest length is : " + lengthOfLongestSubstring(s2));
        System.out.println(s3 + " longest length is : " + lengthOfLongestSubstring(s3));
        System.out.println(s4 + " longest length is : " + lengthOfLongestSubstring(s4));
        System.out.println(s5 + " longest length is : " + lengthOfLongestSubstring(s5));
        System.out.println(s6 + " longest length is : " + lengthOfLongestSubstring(s6));
    }

    public static void testReverse() {
        ListNode listNode4 = new ListNode(4, null);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);

        ListNode list = reverseList(listNode1);
        while (list != null) {
            System.out.print(list.val + "  ");
            list = list.next;
        }
    }

    public static void testMerge() {
        ListNode list1Node3 = new ListNode(4, null);
        ListNode list1Node2 = new ListNode(3, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);

        ListNode list2Node3 = new ListNode(6, null);
        ListNode list2Node2 = new ListNode(5, list2Node3);
        ListNode list2Node1 = new ListNode(2, list2Node2);

        ListNode result = mergeList(list1Node1, list2Node1);
        while (result != null) {
            System.out.print(result.val + "  ");
            result = result.next;
        }
    }

    public static void testAddNumAsList() {
        ListNode list1Node3 = new ListNode(4, null);
        ListNode list1Node2 = new ListNode(3, list1Node3);
        ListNode list1Node1 = new ListNode(1, list1Node2);

        ListNode list2Node3 = new ListNode(6, null);
        ListNode list2Node2 = new ListNode(5, list2Node3);
        ListNode list2Node1 = new ListNode(2, list2Node2);
        ListNode twoResult = addTwoNum(list1Node1, list2Node1);
        while (twoResult != null) {
            System.out.print(twoResult.val + "  ");
            twoResult = twoResult.next;
        }
    }

    public static void testSortArray() {
        int[] nums = {5, 2, 3, 1, 4};

//        sortArray(nums);
//        partition(nums, 0, nums.length - 1);
//        System.out.println("SortArray is = " + Arrays.toString(nums));

        int[] resultArr = partition(nums, 0, nums.length - 1);
        System.out.println("SortArray is = " + Arrays.toString(resultArr));

//        int[] resultArr = sortArray(nums, 0, nums.length - 1);
//        System.out.println("SortArray is = " + Arrays.toString(resultArr));
    }

    public static void main(String[] args) {
        /*
        1?????????????????? 1 ??? n ????????????????????????????????????????
        (1)???????????????????????????????????????????????????
        (2)?????????????????????????????????????????????
         */
        System.out.println();
        System.out.println("-----------------------???1???????????????----------------------------");
        testFindMissingNum();

        /*
        2???????????????????????? s ?????????????????????????????????????????????????????????????????????
        ?????????????????????3??? 1??? 3??? 0??? 0??? 5
         */
        System.out.println("-----------------------???2???????????????----------------------------");
        testLongestSubstring();

        /*
        3???????????????
        ????????????????????? 4??? 3??? 2??? 1
         */
        System.out.println("-----------------------???3???????????????----------------------------");
        testReverse();

        /*
        4?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        ?????????????????????1->2->3->4->5->6
         */
        System.out.println();
        System.out.println("-----------------------???4???????????????----------------------------");
        testMerge();

        /*
        5?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        ??????????????????????????????????????????????????????????????????????????????
        ??????????????????????????? 0 ????????????????????????????????? 0?????????
        ?????? 1???
        ?????????l1 = [1,3,4], l2 = [2,5,6]
        ?????????[3,8,0,1]
        ?????????431 + 652 = 1083.
         */
        System.out.println();
        System.out.println("-----------------------???5???????????????----------------------------");
        testAddNumAsList();

        /*
         ??????
         ????????????????????? n ????????????????????????????????????????????????????????????????????????????????????

         ??????????????? 0 <= n <= 10^5????????????????????????????????? 0 <= val <= 10^9
         ???????????????????????? O(nlogn)?????????????????? O(n)
         ??????1
         ????????? [5,2,3,1,4]
         ????????????[1,2,3,4,5]

         ??????2
         ?????????[5,1,6,2,5]
         ????????????[1,2,5,5,6]
         */
        System.out.println();
        System.out.println("-----------------------???6???????????????----------------------------");
        testSortArray();
    }

    public static ListNode addTwoNum(ListNode list1Node1, ListNode list2Node1) {
        //todo-write your code
        return null;
    }

    public static int findMissingNumber(int[] nums) {
        //todo-write your code
        return -1;
    }

    public static ListNode mergeList(ListNode list1, ListNode list2) {
        //todo-write your code

        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeList(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeList(list1, list2.next);
            return list2;
        }
    }

    public static int lengthOfLongestSubstring(String s) {
        //todo-write your code
        return -1;
    }

    public static ListNode reverseList(ListNode head) {
        //todo-write your code
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    // {5,2,3,1,4};
//    public static int[] sortArray(int[] nums, int start, int end) {
//
//    }


    //??????????????????
    public static int[] partition(int[] nums, int start, int end) {
        if (start == end)
            return nums;

        int random = start + (int) (Math.random() * (end - start));
        int temR = nums[end];
        nums[end] = nums[random];
        nums[random] = temR;

        int tempVal = nums[end];
        int low = start - 1;
        int high = end;
        int p1 = start;
        while (p1 < high) {
            if (nums[p1] < tempVal) {
                int tem = nums[p1];
                nums[p1] = nums[low + 1];
                nums[low + 1] = tem;
                low++;
                p1++;
            } else if (nums[p1] > tempVal) {
                int tem = nums[p1];
                nums[p1] = nums[high - 1];
                nums[high - 1] = tem;
                high--;
            } else {
                p1++;
            }
        }
        int tem = nums[high];
        nums[high] = nums[end];
        nums[end] = tem;
        if (low >= start) {
            partition(nums, start, low);
        }
        if (low + 2 <= end) {
            partition(nums, low + 2, end);
        }
        return nums;
    }

    // {5,2,3,1,4};
//    public static int[] partition(int[] nums, int start, int end) {
//        if (start == end)
//            return nums;
//
//        int random = start + (int) (Math.random() * (end - start));
//        System.out.println(" start is = " + start + ", end = " + end + ", random = " + random);
//        int temR = nums[end];
//        nums[end] = nums[random];
//        nums[random] = temR;
//
//        int tempVal = nums[end];
//        int low = start - 1;
//        int p1 = start;
//        while (p1 < end) {
//            if (nums[p1] < tempVal) {
//                int tem = nums[p1];
//                nums[p1] = nums[low + 1];
//                nums[low + 1] = tem;
//                low++;
//            }
//            p1++;
//        }
//        int tem = nums[low + 1];
//        nums[low + 1] = nums[end];
//        nums[end] = tem;
//        if (low >= start) {
//            partition(nums, start, low);
//        }
//        if (low + 2 <= end) {
//            partition(nums, low + 2, end);
//        }
//
//        return nums;
//    }


//    for (int i = start; i < end; i++) {
//        if (nums[i] < tempVal) {
//            int tem = nums[i];
//            nums[i] = nums[low + 1];
//            nums[low + 1] = tem;
//            low++;
//        }
//        p1++;
//        if (p1 == end)
//            break;
//    }

    // {5,2,3,1,4};
//    public static int[] sortArray(int[] nums, int start, int end) {
//        if (start == end) {
//            int[] resArr1 = new int[1];
//            resArr1[0] = nums[start];
//            return resArr1;
//        }
//
////        if (start + 1 == end) {
////            int[] resArr2 = new int[2];
////            if (nums[start] < nums[end]) {
////                resArr2[0] = nums[start];
////                resArr2[1] = nums[end];
////            } else {
////                resArr2[0] = nums[end];
////                resArr2[1] = nums[start];
////            }
////            return resArr2;
////        }
//
//        int mid = (end + start) / 2;
//        int[] left = sortArray(nums, start, mid);
//        int[] right = sortArray(nums, mid + 1, end);
//        return mergeArray(left, right);
//    }

    public static int[] mergeArray(int[] left, int[] right) {
        int[] mergeArr = new int[left.length + right.length];
        int p = 0;
        int p1 = 0;
        int p2 = 0;
        while (p1 < left.length && p2 < right.length) {
            if (left[p1] < right[p2]) {
                mergeArr[p++] = left[p1++];
            } else {
                mergeArr[p++] = right[p2++];
            }
        }
        while (p1 < left.length) {
            mergeArr[p++] = left[p1++];
        }
        while (p2 < right.length) {
            mergeArr[p++] = right[p2++];
        }
        return mergeArr;
    }

//    public static void  sortArray(int[] nums) {
//        for (int i = 1; i < nums.length; i++) {
//            for (int j = 0; j < i; j++) {
//                if (nums[i] < nums[j]) {
//                    int temp = nums[j];
//                    nums[j] = nums[i];
//                    nums[i] = temp;
//                }
//            }
//        }
//    }

    public static void sortArray(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

//    public static void  sortArray(int[] nums) {
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] < nums[i]) {
//                    int temp = nums[j];
//                    nums[j] = nums[i];
//                    nums[i] = temp;
//                }
//            }
//        }
//    }

    public static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = b ^ a;
    }
}
