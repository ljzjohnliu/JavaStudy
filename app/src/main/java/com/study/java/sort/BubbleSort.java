package com.study.java.sort;

import java.util.Arrays;

/**
 * 依次比较相邻的两个数，将小数放在前面，大数放在后面。
 * 即在第一趟：首先比较第1个和第2个数，将小数放前，大数放后。
 * 然后比较第2个数和第3个数，将小数放前，大数放后，如此继续，
 * 直至比较最后两个数，将小数放前，大数放后。至此第一趟结束，将最大的数放到了最后。
 * 在第二趟：仍从第一对数开始比较（因为可能由于第2个数和第3个数的交换，使得第1个数不再小于第2个数），
 * 将小数放前，大数放后，一直比较到倒数第二个数（倒数第一的位置上已经是最大的），
 * 第二趟结束，在倒数第二的位置上得到一个新的最大数（其实在整个数列中是第二大的数）。
 * 如此下去，重复以上过程，直至最终完成排序。
 */
public class BubbleSort {

    public static void main(String[] args) {
        int arr[] = new int[]{1, 7, 4, 3, 6, 3, 2, 5, 8};
//        int arr[] = new int[]{1, 2, 3, 3, 4, 5, 6, 7, 8};
//        BubbleSort.bubbleSort(arr);
        BubbleSort.bubbleSort2(arr);
        System.out.println(Arrays.toString(arr));

//        int arr2[] = new int[]{2, 8, 4, 1};
//        int[] res = getNumIndex(12, arr2);
//        System.out.println(Arrays.toString(res));
    }

    public static void bubbleSort(int[] arr) {
        int temp;//定义一个临时变量
        for (int i = 0; i < arr.length - 1; i++) {//冒泡趟数
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void bubbleSort2(int[] arr) {
        boolean flag = false;
        while (!flag) {
            int temp;
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = 0; j < arr.length - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        flag = true;
                    }
                }
                System.out.println("----------------flag = " + flag);
                if (!flag) {
                    flag = true;
                    break;//若果没有发生交换，则退出循环
                }
            }
        }
    }

    /**
     * 给一个数sum  在数组中查找是否有两个数的和为sum
     * 如果有 返回数组下标，没有 返回-1，
     * 例如sum=12  数组：{2， 8， 4， 1}
     * 返回1,2  如果sum=13 返回-1
     *
     * @return
     */
    public static int[] getNumIndex(int sum, int[] arr) {
        int[] result = new int[2];
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            temp = sum - arr[i];
            result[0] = i;
            for (int j = i + 1; j < arr.length - 1; j ++) {
                if (temp == arr[j]) {
                    result[1] = j;
                    return result;
                }
            }
        }
        return new int[]{-1};
    }
}
