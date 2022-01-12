package com.study.java.leecode;

import java.util.ArrayList;
import java.util.LinkedList;

public class TreeTest {
    public static void main(String[] args) {

    }

    static int pre0 = -1;
    static int pre1 = -1;
    static int pre2 = -1;
    static int n = 0;

    public static int[][] threeOrders(TreeNode root) {
        // write code here
        n = 0;
        int n = countTree(root);
        System.out.println("n is = " + n);
        int[][] orders = new int[3][n];
        preTraversal(root, orders[0]);
        inorderTraversal(root, orders[1]);
        lastTraversal(root, orders[2]);
        return orders;
    }

    /**
     * 先序遍历
     */
    public static void preTraversal(TreeNode treeNode, int[] order) {
        if (treeNode == null) {
            return;
        }
        order[++pre0] = treeNode.val;
        preTraversal(treeNode.left, order);
        preTraversal(treeNode.right, order);
    }

    /**
     * 中序遍历
     */
    public static void inorderTraversal(TreeNode treeNode, int[] order) {
        if (treeNode == null) {
            return;
        }
        inorderTraversal(treeNode.left, order);
        order[++pre1] = treeNode.val;
        inorderTraversal(treeNode.right, order);
    }

    /**
     * 后序遍历
     */
    public static void lastTraversal(TreeNode treeNode, int[] order) {
        if (treeNode == null) {
            return;
        }
        lastTraversal(treeNode.left, order);
        order[++pre2] = treeNode.val;
        lastTraversal(treeNode.right, order);
    }

    //count The tree node_number
    public static int countTree(TreeNode root) {
        if (root == null) {
            return n;
        } else {
            n++;
            countTree(root.left);
            countTree(root.right);
        }
        return n;
    }

    /**
     * 二叉树的层序遍历
     * 给定一个二叉树，返回该二叉树层序遍历的结果，（从左到右，一层一层地遍历）
     * 例如：给定的二叉树是{3,9,20,#,#,15,7},
     * 该二叉树层序遍历的结果是
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     * 提示: 0 <= 二叉树的结点数 <= 1500
     * 示例1
     * 输入：{1,2}
     * 返回值：[[1],[2]]
     * 示例2
     * 输入：{1,2,3,4,#,#,5}
     * 返回值：[[1],[2,3],[4,5]]
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        n = 0;
        int count = countTree(root);
        return levelOrderInner(root, 0);
    }

    public ArrayList<ArrayList<Integer>> levelOrderInner(TreeNode root, int level) {
        if (root.left == null) {
            return null;
        }
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(root.val);

        LinkedList<TreeNode> queue = new LinkedList();
        queue.offer(root);

        levelOrderInner(root.left, level+1);
        levelOrderInner(root.right, level+1);
        return null;
    }
}
