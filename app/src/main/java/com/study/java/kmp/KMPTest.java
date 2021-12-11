package com.study.java.kmp;

public class KMPTest {

    public static void main(String[] args) {
        String tStr = "ABCABCDHABCFDIJKABCFD";
        String pStr = "ABCFD";
//        getS(tStr, pStr);
//        System.out.println(bf(tStr, pStr));
        int index = KMPUtil.getIndex(tStr, pStr);
        System.out.println("index = " + index);
    }

    public static void getS(String tStr, String pStr) {
        for (int i = 0; i <= tStr.length() - pStr.length(); i++) {
            for (int j = 0; j < pStr.length(); j++) {
                if (pStr.charAt(j) != tStr.charAt(i + j)) {
                    break;
                }
                if (j == pStr.length() - 1) {
                    System.out.println("----------i = " + i);
                }
            }
        }
    }

    /**
     * 暴力破解法
     *
     * @param ts 主串
     * @param ps 模式串
     * @return 如果找到，返回在主串中第一个字符出现的下标，否则为-1
     */
    public static int bf(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        while (i < t.length && j < p.length) {
            if (t[i] == p[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
            System.out.println("--while--i = " + i + ", j = " + j);
        }
        System.out.println("----i = " + i + ", j = " + j);
        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }
    }
}
