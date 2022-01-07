package com.study.java.leecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 * 设计LRU(最近最少使用)缓存结构，该结构在构造时确定大小，假设大小为 k ，并有如下两个功能
 * 1. set(key, value)：将记录(key, value)插入该结构
 * 2. get(key)：返回key对应的value值
 * <p>
 * 提示:
 * 1.某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的，然后都会刷新缓存。
 * 2.当缓存的大小超过k时，移除最不经常使用的记录。
 * 3.输入一个二维数组与k，二维数组每一维有2个或者3个数字，第1个数字为opt，第2，3个数字为key，value
 * 若opt=1，接下来两个整数key, value，表示set(key, value)
 * 若opt=2，接下来一个整数key，表示get(key)，若key未出现过或已被移除，则返回-1
 * 对于每个opt=2，输出一个答案
 * 4.为了方便区分缓存里key与value，下面说明的缓存里key用""号包裹
 * <p>
 * 要求：set和get操作复杂度均为 O(1)O(1)
 * 示例1
 * 输入：
 * [[1,1,1],[1,2,2],[1,3,2],[2,1],[1,4,4],[2,2]],3
 * 返回值：
 * [1,-1]
 * <p>
 * 说明：
 * [1,1,1]，第一个1表示opt=1，要set(1,1)，即将(1,1)插入缓存，缓存是{"1"=1}
 * [1,2,2]，第一个1表示opt=1，要set(2,2)，即将(2,2)插入缓存，缓存是{"1"=1,"2"=2}
 * [1,3,2]，第一个1表示opt=1，要set(3,2)，即将(3,2)插入缓存，缓存是{"1"=1,"2"=2,"3"=2}
 * [2,1]，第一个2表示opt=2，要get(1)，返回是[1]，因为get(1)操作，缓存更新，缓存是{"2"=2,"3"=2,"1"=1}
 * [1,4,4]，第一个1表示opt=1，要set(4,4)，即将(4,4)插入缓存，但是缓存已经达到最大容量3，
 * 移除最不经常使用的{"2"=2}，插入{"4"=4}，缓存是{"3"=2,"1"=1,"4"=4}
 * [2,2]，第一个2表示opt=2，要get(2)，查找不到，返回是[1,-1]
 * <p>
 * 示例2
 * 输入：
 * [[1,1,1],[1,2,2],[2,1],[1,3,3],[2,2],[1,4,4],[2,1],[2,3],[2,4]],2
 * 返回值：
 * [1,-1,-1,3,4]
 * <p>
 * 备注：
 * 1 \leq K \leq N \leq 10^51≤K≤N≤10
 * 5
 * <p>
 * -2 \times 10^9 \leq x,y \leq 2 \times 10^9−2×10
 * 9
 * ≤x,y≤2×10
 * 9
 */
public class LRUCache {

    static class Node {
        int key, val;
        Node prev, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private Map<Integer, Node> map;
    private Node head;
    private Node tail;
    private int k;

    public LRUCache(int capacity) {
        k = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * delete 操作：将当前节点从双向链表中移除
     * 由于我们预先建立 head 和 tail 两位哨兵，因此如果 node.l 不为空，则代表了 node 本身存在于双向链表（不是新节点）
     */
    public void delete(Node node) {
        if (node.prev != null) {
            Node preNode = node.prev;
            preNode.next = node.next;
            node.next.prev = preNode;
        }
    }

    /**
     * refresh 操作分两步：
     * 1. 先将当前节点从双向链表中删除（如果该节点本身存在于双向链表中的话）
     * 2. 将当前节点添加到双向链表头部
     */
    public void refresh(Node node) {
        delete(node);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            refresh(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node node = null;
        if (map.containsKey(key)) {
            node = map.get(key);
            node.val = value;
        } else {
            if (map.size() == k) {
                Node delNode = tail.prev;
                map.remove(delNode.key);
                delete(delNode);
            }
            node = new Node(key, value);
            map.put(key, node);
        }
        refresh(node);
    }

    public int[] LRU(int[][] operators, int k) {
        List<Integer> list = new ArrayList<>();
        LRUCache lruCache = new LRUCache(k);
        for (int[] op : operators) {
            int type = op[0];
            if (type == 1) {
                lruCache.put(op[1], op[2]);
            } else {
                list.add(lruCache.get(op[1]));
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i< list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
}
