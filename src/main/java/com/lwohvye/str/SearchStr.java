package com.lwohvye.str;

public class SearchStr {

    // region KMP算法

    // KMP 算法
    // ts: 原串(string)  ps: 匹配串(pattern)
    public static int kmp(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        // 生成next数组
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
                // 初始及匹配成功时
                i++;
                j++;
            } else {
                // 匹配不成功 j = next(j)
                j = next[j]; // j回到指定位置
            }
        }
        if (j == p.length) {
            // 整一段匹配成功，直接返回下标
            return i - j;
        } else {
            // 未找到。返回-1
            return -1;
        }
    }

    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        // 构建 next 数组，数组长度为匹配串的长度（next 数组是和匹配串相关的）
        int[] next = new int[p.length];
        // next[0] = -1 本身没太多的意义
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                // 初始及匹配成功时
                if (p[++j] == p[++k]) { // 当两个字符相等时要跳过
                    next[j] = next[k];
                } else {
                    // 更新 next[j]，结束本次循环，开始下一轮
                    next[j] = k;
                }
            } else {
                // 匹配不成功的话，k = next(k)
                k = next[k];
            }
        }
        return next;
    }

    // endregion


}
