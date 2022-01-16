package com.study.java.leecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
public class LRUCache2 {

    private Map<Integer, Integer> map = new LinkedHashMap<>();
    private int count;

    public LRUCache2(int capacity) {
        count = capacity;
    }

    private int get(int key) {
        if (map.containsKey(key)) {
            int value = map.get(key);
            map.remove(key);
            map.put(key, value);
            return value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            map.remove(key);
        } else if (map.size() == count) {
            Iterator iterator = map.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
        map.put(key, value);
    }
}
