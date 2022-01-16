package com.study.java.leecode;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TreeTest {
    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(7);
        TreeNode node4 = new TreeNode(15);
        TreeNode node3 = new TreeNode(20);
        TreeNode node2 = new TreeNode(9);
        TreeNode node1 = new TreeNode(3);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;

//        ArrayList<ArrayList<Integer>> lists = levelOrder2(node1);
        List<List<Integer>> lists = zigzagLevelOrder(node1);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println();
            for (int j = 0; j < lists.get(i).size(); j++) {
                System.out.print(lists.get(i).get(j) + "  ");
            }
        }

//        List<Integer> list = new ArrayList<>();
        List<Integer> list = Arrays.asList(new Integer[2]);
        list.set(1, 12);
    }

    /**
     * 二叉树的锯齿形层序遍历
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。
     * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     *
     * 解法：在原来层序遍历基础上改进下。内层list采用LinkedList 不同层分别从头尾add元素
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        LinkedList<Integer> list = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int curLevel = 0;
        TreeNode tempNode;
        int levelCount;
        while (!queue.isEmpty()) {
            levelCount = queue.size();
            while (levelCount > 0) {
                tempNode = queue.poll();
                levelCount--;
                if (curLevel % 2 == 1) {
                    list.addFirst(tempNode.val);
                } else {
                    list.addLast(tempNode.val);
                }

                if (tempNode.left != null) {
                    queue.add(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.add(tempNode.right);
                }
                if (levelCount == 0) {
                    lists.add(curLevel++, list);
                    list = new LinkedList<>();
                }
            }
        }
        return lists;
    }

    /**
     * 深度优先搜索
     */
    public static List<Integer> rightSideView2(TreeNode root) {
        res.clear();
        dfs(root, 0);
        return res;
    }

    static List<Integer> res = new ArrayList<>();
    public static void dfs(TreeNode root, int depth) {
        if (root == null)
            return;
        if (depth == res.size()) {
            res.add(root.val);
        }
        depth++;
        dfs(root.right, depth);
        dfs(root.left, depth);
    }

    /**
     * 二叉树的右视图
     * 广度优先搜索 也就是层序遍历每一层最后元素
     */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null)
            return list;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        while (!queue.isEmpty()) {
            //当前队列只能存在一层的节点，所以这个size也就是当前层的节点个数！这个很有用！！
            int size = queue.size();
            TreeNode node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            if (curEnd == node) {
                list.add(node.val);
                curEnd = nextEnd;
            }
        }
        return list;
    }

    public static ArrayList<ArrayList<Integer>> getLevelLists(TreeNode root) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        int curLevel = 0;
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            if (curEnd == node) {
                lists.add(curLevel++, list);
                list = new ArrayList<>();
                curEnd = nextEnd;
            }
        }
        return lists;
    }

    /**
     * 使用栈实现
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> stk = new LinkedList<TreeNode>();
//        Stack<TreeNode> stk = new Stack<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            list.add(root.val);
            root = root.right;
        }
        return list;
    }


    /**
     * 递归中序遍历
     */
    public static List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversalInner(root, list);
        return list;
    }

    public static void inorderTraversalInner(TreeNode root,  List<Integer> list) {
        if (root == null) {
            return;
        }
        inorderTraversalInner(root.left, list);
        list.add(root.val);
        inorderTraversalInner(root.right, list);
    }


    /**
     * 二叉树的层序遍历
     * 引申题目可以求最大节点的层个数，以及层深度
     */
    public static ArrayList<ArrayList<Integer>> levelOrder2(TreeNode root) {
        ArrayList<ArrayList<Integer>> allList = new ArrayList<>();
        if (root == null) {
            return allList;
        }
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        int curLevel = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            if (node == curEnd) {
                allList.add(curLevel, list);
                list = new ArrayList<>();
                curLevel++;
                curEnd = nextEnd;
            }
        }
        return allList;
    }

    public ArrayList<Integer> levelOrder1(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return list;
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
