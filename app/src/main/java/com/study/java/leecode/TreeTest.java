package com.study.java.leecode;

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
     *
     * @param treeNode
     * @return
     */
    public static void inorderTraversal(TreeNode treeNode, int[] order) {
        if (treeNode == null) {
            return;
        }
        inorderTraversal(treeNode.left, order);
        order[++pre1] = treeNode.val;
        inorderTraversal(treeNode.right, order);
    }

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
}
