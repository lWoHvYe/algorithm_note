package com.lwohvye.table.array;

// 并查集
// 并查集是一种树型的数据结构，用于处理一些不相交集合的合并及查询问题。
// 并查集的思想是用一个数组表示了整片森林（parent），树的根节点唯一标识了一个集合，我们只要找到了某个元素的的树根，就能确定它在哪个集合里
// 并查集，主要用于解决一些元素分组的问题。它管理一系列不相交的集合，并支持两种操作：
//      合并（Union）：把两个不相交的集合合并为一个集合。
//      查询（Find）：查询两个元素是否在同一个集合中
public class UnionFind {
    private int[] rank;   // rank[i]表示以i为根的集合所表示的树的层数。称为 秩。当按秩优化与路径压缩同时使用时，秩的值可能不是实际值。
    //秩不是准确的子树高，而是子树高的上界，因为路径压缩可能改变子树高。还可以将秩定义成子树节点数，因为节点多的树倾向更高。无论将秩定义成子树高上界，还是子树节点数，按秩合并都是尝试合出最矮的树，并不保证一定最矮。

    private int[] parent; // parent[i]表示第i个元素所指向的父节点
    private int count;    // 数据个数
    private int part;     // 独立节点的个数

    // 构造函数
    public UnionFind(int count) {
        rank = new int[count];
        parent = new int[count];
        this.count = count;
        this.part = count;
        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for (int i = 0; i < count; ++i) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // 查找过程, 查找元素p所对应的集合编号
    // O(h)复杂度, h为树的高度
    private int find(int p) {
        // 根据业务，可以移除
        assert (p >= 0 && p < count);
        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
//        while (p != parent[p])
//            p = parent[p];
//        return p;

        if (p != parent[p]) return parent[p] = find(parent[p]);
        return parent[p];
    }

    // 压缩路径，但这个与秩冲突了。基于该方式，已不必担心树高问题，时间复杂度O(å(n)) <= O(4)
    private int findZipPath(int p) {
        int root = find(p);
        while (p != root) {
            int t = parent[p];
            parent[t] = root; // 路径压缩
            p = t;
        }
        return root;
    }

    // 查看元素p和元素q是否所属一个集合
    // O(h)复杂度, h为树的高度
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(h)复杂度, h为树的高度
    // 按秩优化：合并时，基于rank进行优化，将层高低的合并到层高高的
    public void union(int p, int q) {
        // 获取各自根节点
        int pRoot = find(p);
        int qRoot = find(q);
        // 根节点同
        if (pRoot == qRoot)
            return;
        // 根节点不同，合并后，分区减一
        part--;
        // 父节点不同时，比较秩
        if (rank[pRoot] < rank[qRoot])
            parent[pRoot] = qRoot;
        else if (rank[qRoot] < rank[pRoot])
            parent[qRoot] = pRoot;
        else { // rank[pRoot] == rank[qRoot]
            // 层高相等。其中一个合并到另一个后，新的父点层高+1
            parent[pRoot] = qRoot;
            rank[qRoot] += 1;   // 维护rank的值
        }
    }
}
