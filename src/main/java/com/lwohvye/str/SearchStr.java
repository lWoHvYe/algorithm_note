package com.lwohvye.str;

public class SearchStr {

    // region KMP算法

    // KMP 算法
    // ss: 原串(string)  pp: 匹配串(pattern)
    public int strStrKmp(String ss, String pp) {
        if (pp.isEmpty()) return 0;

        // 构建next数组
        int[] next = genNext(pp);

        // kmp匹配
        return kmp(ss, pp, next);
    }

    private int[] genNext(String pp) {
        var m = pp.length();
        // 构建 next 数组，数组长度为匹配串的长度（next 数组是和匹配串相关的）
        int[] next = new int[m];
        // 构造过程 i = 1，j = 0 开始，i 小于等于匹配串长度 【构造 i 从 1 开始】
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && pp.charAt(i) != pp.charAt(j)) {
                // 匹配不成功的话，j = next(j - 1)
                j = next[j - 1];
            }
            if (pp.charAt(i) == pp.charAt(j)) {
                // 匹配成功的话，先让 j++
                ++j;
            }
            // 更新 next[i]，结束本次循环，i++
            next[i] = j;
        }
        return next;
    }

    private int kmp(String ss, String pp, int[] next) {
        // 分别读取原串和匹配串的长度
        int n = ss.length(), m = pp.length();
        // 匹配过程，i = 0，j = 0 开始，i 小于等于原串长度 【匹配 i 从 0 开始】
        for (int i = 0, j = 0; i < n; ++i) {
            while (j > 0 && ss.charAt(i) != pp.charAt(j)) {
                // 匹配不成功 j = next(j-1)
                j = next[j - 1];
            }
            if (ss.charAt(i) == pp.charAt(j)) {
                // 匹配成功的话，先让 j++，结束本次循环后 i++
                ++j;
            }
            if (j == m) {
                // 整一段匹配成功，直接返回下标
                return i - m + 1;
            }
        }
        // 未找到。返回-1
        return -1;
    }
    // endregion


}
