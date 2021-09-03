package com.study.java.leecode;

import java.util.Arrays;

public class GenericArrayDemo<T> {

    static class GenericArray<T> {
        private T[] array;

        public GenericArray(int num) {
            array = (T[]) new Object[num];
        }

        public void put(int index, T item) {
            array[index] = item;
        }

        public T get(int index) {
            return array[index];
        }

        public T[] array() {
            return array;
        }
    }

    public static void main(String[] args) {
        GenericArray<Integer> genericArray = new GenericArray<Integer>(4);
        genericArray.put(0, 0);
        genericArray.put(1, 1);
        Object[] array = genericArray.array();
        System.out.println(Arrays.deepToString(array));
    }
}
