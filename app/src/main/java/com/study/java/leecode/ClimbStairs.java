package com.study.java.leecode;

/**
 * 爬楼梯
 * 假设你正在爬楼梯。需要 n阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 * 示例 1：
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 */
public class ClimbStairs {

    public static void main(String[] args) {
        System.out.println(climbStairs1(44));
    }

    /**
     * 递归实现，但是递归层数太多
     */
    public static int climbStairs(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * 动态规划思想，合理利用上次计算值
     */
    public static int climbStairs1(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        int i1 = 1;
        int i2 = 2;
        for (int i = 3; i <= n; i++) {
            int temp = i1 + i2;
            i1 = i2;
            i2 = temp;
        }
        return i2;
    }
}
