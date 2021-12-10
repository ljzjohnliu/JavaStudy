package com.study.java.reflect;

import java.util.ArrayList;
import java.util.List;

public class DAO<T> {
    List<T> list = new ArrayList<T>();

    //根据id获取一个对象
    T get(Integer id) throws Exception {
        if (list == null)
            return null;
        if (id >= list.size()) {
            throw new Exception("id超出list的大小！");
        }
        return list.get(id);
    }

    //保存一个对象
    void save(T entity) {
        list.add(entity);
    }
}
