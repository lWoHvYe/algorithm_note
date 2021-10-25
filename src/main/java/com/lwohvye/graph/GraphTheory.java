package com.lwohvye.graph;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// 图
public class GraphTheory {

    class GraphSearch {
        // 四角  1,0 0,1 0,-1 -1,0
        int[] dx = {1, 0, 0, -1};
        int[] dy = {0, 1, -1, 0};

        // region 查找最大面积
        // 深度-栈
        private int dfsMethod(int[][] grid, int sr, int sc, int newVal) {
            // 行
            var rowLen = grid.length;
            // 列
            var rankLen = grid[0].length;
            // 处理的次数
            var con = 0;
            // 操作值为起始点的
            var goalVal = grid[sr][sc];
            // 定义栈
//            var stack = new Stack<int[]>();
            // LinkList可当栈用，使用push和pop方法
            var stack = new LinkedList<int[]>();
            // 先改再存
            grid[sr][sc] = newVal;
            ++con;
            // 存放
            stack.push(new int[]{sr, sc});
            while (!stack.isEmpty()) {
                // 取出
                var cell = stack.pop();
                int x = cell[0], y = cell[1];
                for (int i = 0; i < 4; ++i) {
                    int mx = x + dx[i], my = y + dy[i];
                    if (mx >= 0 && mx < rowLen && my >= 0 && my < rankLen && grid[mx][my] == goalVal && grid[mx][my] != newVal) {
                        // 先改再存。而非取的时候再改，能避免重复
                        grid[mx][my] = newVal;
                        ++con;
                        stack.push(new int[]{mx, my});
                    }
                }
            }
            return con;
        }

        // 广度-队列
        private int bfsMethod(int[][] grid, int sr, int sc, int newVal) {
            // 行
            var rowLen = grid.length;
            // 列
            var rankLen = grid[0].length;
            // 处理的次数
            var con = 0;
            // 起始点颜色
            var goalVal = grid[sr][sc];
            // 定义队列
//            var queue = new Queue<int[]>();
            // LinkList可当队列用，使用offer和poll方法
            var queue = new LinkedList<int[]>();
            // 先改再存
            grid[sr][sc] = newVal;
            ++con;
            // 存放
            queue.offer(new int[]{sr, sc});
            while (!queue.isEmpty()) {
                // 取出
                var cell = queue.poll();
                int x = cell[0], y = cell[1];
                for (int i = 0; i < 4; ++i) {
                    int mx = x + dx[i], my = y + dy[i];
                    if (mx >= 0 && mx < rowLen && my >= 0 && my < rankLen && grid[mx][my] == goalVal && grid[mx][my] != newVal) {
                        // 先改再存。而非取的时候再改，能避免重复
                        grid[mx][my] = newVal;
                        ++con;
                        queue.offer(new int[]{mx, my});
                    }
                }
            }
            return con;
        }

        // 深度优先算法-递归
        private int dfsMethod1(int[][] grid, int x, int y, int newVal, int goalVal) {
            // 不超出范围
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
                return 0;
            // 已染过色不做处理、且只染与原颜色相同的
            var curColor = grid[x][y];
            if (curColor == newVal || curColor != goalVal)
                return 0;
            grid[x][y] = newVal;
            var ans = 1;
            for (int i = 0; i < 4; ++i) {
                int mx = x + dx[i], my = y + dy[i];
                ans += dfsMethod1(grid, mx, my, newVal, goalVal);
            }
            return ans;
        }
        // endregion

        // 广度-队列-计算层级
        private int bfsRankMethod(int[][] grid, int goalVal) {
            // 行
            var m = grid.length;
            // 列
            var n = grid[0].length;
            // 视业务要求
            var newVal = 2;
            //--------------------start---------------
            // 这一块代码，当起点不值一个时，可能需要定一个名为超级源点的起始，在这里把目标起点放入队列
            // 定义队列
            // LinkList可当队列用，使用offer和poll方法
//            var queue = new LinkedList<int[]>();
            // ArrayDeque is likely to be faster than Stack when used as a stack, and faster than LinkedList when used as a queue
            var queue = new ArrayDeque<int[]>();
            // 这里视作这么目标点都是从一个源点出发的
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    // 将目标点放入
                    if (grid[i][j] == goalVal){
                        // 先改再存
                        grid[i][j] = newVal;
                        // 存放
                        queue.offer(new int[]{i, j});
                    }
                }
            }
            //-----------------------end--------------
            // 处理的轮数
            var con = 0;
            while (!queue.isEmpty()) {
                // 每次处理一层
                var size = queue.size();
                for (int l = 0; l < size; ++l) {
                    // 取出
                    var cell = queue.poll();
                    int x = cell[0], y = cell[1];
                    for (int i = 0; i < 4; ++i) {
                        int mx = x + dx[i], my = y + dy[i];
                        if (mx >= 0 && mx < m && my >= 0 && my < n && grid[mx][my] == goalVal && grid[mx][my] != newVal) {
                            // 先改再存。而非取的时候再改，能避免重复
                            grid[mx][my] = newVal;
                            queue.offer(new int[]{mx, my});
                        }
                    }
                }
                // 扩展次数。若队列中无元素，表示已无法扩展，本次不计
                if (!queue.isEmpty())
                    ++con;
            }
            return con;
        }

    }
}
