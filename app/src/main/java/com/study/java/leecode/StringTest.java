package com.study.java.leecode;

public class StringTest {

    public static void main(String[] args) {
        String s = "rabbbit";
        String t = "rabbit";
        int find = count(s, t);
        System.out.println(find);
    }

//    int c = 0;
//        for (int i = s.indexOf(sub, 0); i != -1; i = s.indexOf(sub, i + sub.length())) {
//        c++;
//    }
    //判断一个子字符串在一个字符串中出现的次数
    //思路：利用indexOf循环
    public static int count(String s, String sub) {
        int c = 0;

        int j = 0;
        char sp = sub.charAt(j++);
        int form = 0;
        int i = s.indexOf(sp, form);

        while (i != -1 && j < sub.length()) {
            sp = sub.charAt(j++);
            i = s.indexOf(sp, i+1);
            if (j == sub.length() && i != -1) {
                c++;
            }
        }



        return c;
    }

}
