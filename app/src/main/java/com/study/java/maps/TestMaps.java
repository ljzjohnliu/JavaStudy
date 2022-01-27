package com.study.java.maps;

import android.net.Uri;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TestMaps {

    public static void main(String[] args) {
        String str = "https://www.ljz.com";
        Uri.parse("str");
    }

    private static void testHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
    }

    private static void testConcurrentHashMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap();
    }
}
