package com.lwohvye.tree;

// 二分搜索树、二叉查找树
// 二分搜索树（英语：Binary Search Tree），也称为 二叉查找树 、二叉搜索树 、有序二叉树或排序二叉树。满足以下几个条件：
//  若它的左子树不为空，左子树上所有节点的值都小于它的根节点。
//  若它的右子树不为空，右子树上所有的节点的值都大于它的根节点。
//  它的左、右子树也都是二分搜索树。

//二分搜索树有着高效的插入、删除、查询操作。
//平均时间的时间复杂度为 O(log n)，最差情况为 O(n)。二分搜索树与堆不同，不一定是完全二叉树，底层不容易直接用数组表示故采用链表来实现二分搜索树

// 二分搜索树遍历分为两大类，深度优先遍历和层序遍历（广度优先）。
//深度优先遍历分为三种：先序遍历（preorder tree walk）、中序遍历（inorder tree walk）、后序遍历（postorder tree walk），分别为：
//  1、前序遍历：先访问当前节点，再依次递归访问左右子树。
//  2、中序遍历：先递归访问左子树，再访问自身，再递归访问右子树。
//  3、后序遍历：先递归访问左右子树，再访问自身节点。
//
//二分搜索树的层序遍历，即逐层进行遍历，即将每层的节点存在队列当中，然后进行出队（取出节点）和入队（存入下一层的节点）的操作，以此达到遍历的目的。
//  通过引入一个队列来支撑层序遍历（广度优先）

import java.util.LinkedList;

// 二分搜索树的特性
//  顺序性：二分搜索树可以当做查找表的一种实现。
//    我们使用二分搜索树的目的是通过查找 key 马上得到 value。minimum、maximum、successor（后继）、predecessor（前驱）、floor（地板）、ceil（天花板、rank（排名第几的元素）、select（排名第n的元素是谁）这些都是二分搜索树顺序性的表现。
//  局限性：二分搜索树在时间性能上是具有局限性的。二叉搜索树可能退化成链表
public class BinarySearchTree<K extends Comparable, V> {
    // 树中的节点为私有的类, 外界不需要了解二分搜索树节点的具体实现
    private class TreeNode {
        private K key;
        private V value;
        private TreeNode left, right;

        public TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }

        public TreeNode(TreeNode treeNode) {
            this.key = treeNode.key;
            this.value = treeNode.value;
            this.left = treeNode.left;
            this.right = treeNode.right;
        }
    }

    private TreeNode root;  // 根节点
    private int count;  // 树中的节点个数

    // 构造函数, 默认构造一棵空二分搜索树
    public BinarySearchTree() {
        root = null;
        count = 0;
    }

    // 返回二分搜索树的节点个数
    public int size() {
        return count;
    }

    // 返回二分搜索树是否为空
    public boolean isEmpty() {
        return count == 0;
    }

    // 向二分搜索树中插入一个新的(key, value)数据对
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    // 查看二分搜索树中是否存在键key
    public boolean contain(K key) {
        return contain(root, key);
    }

    // 在二分搜索树中搜索键key所对应的值。如果这个值不存在, 则返回null
    public V search(K key) {
        return search(root, key);
    }

    // 二分搜索树的前序遍历
    public void preOrder() {
        preOrder(root);
    }

    // 二分搜索树的中序遍历
    public void inOrder() {
        inOrder(root);
    }

    // 二分搜索树的后序遍历
    public void postOrder() {
        postOrder(root);
    }

    // 二分搜索树的层序遍历
    public void levelOrder() {

        // 我们使用LinkedList来作为我们的队列
        var queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {

            var node = queue.remove();

            System.out.println(node.key);

            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
    }

    // 寻找二分搜索树的最小的键值
    public K minimum() {
        assert count != 0;
        var minNode = minimum(root);
        return minNode.key;
    }

    // 寻找二分搜索树的最大的键值
    public K maximum() {
        assert count != 0;
        var maxNode = maximum(root);
        return maxNode.key;
    }

    // 从二分搜索树中删除最小值所在节点
    public void removeMin() {
        if (root != null)
            root = removeMin(root);
    }

    // 从二分搜索树中删除最大值所在节点
    public void removeMax() {
        if (root != null)
            root = removeMax(root);
    }

    // 从二分搜索树中删除键值为key的节点
    public void remove(K key) {
        root = remove(root, key);
    }

    //********************
    //* 二分搜索树的辅助函数
    //********************

    // 向以node为根的二分搜索树中, 插入节点(key, value), 使用递归算法
    // 返回插入新节点后的二分搜索树的根
    private TreeNode insert(TreeNode root, K key, V value) {

        if (root == null) {
            count++;
            return new TreeNode(key, value);
        }

        if (key.compareTo(root.key) == 0)
            root.value = value;
        else if (key.compareTo(root.key) < 0)
            root.left = insert(root.left, key, value);
        else    // key > node->key
            root.right = insert(root.right, key, value);

        return root;
    }

    // 查看以node为根的二分搜索树中是否包含键值为key的节点, 使用递归算法
    private boolean contain(TreeNode root, K key) {

        if (root == null)
            return false;

        if (key.compareTo(root.key) == 0)
            return true;
        else if (key.compareTo(root.key) < 0)
            return contain(root.left, key);
        else // key > node->key
            return contain(root.right, key);
    }

    // 在以node为根的二分搜索树中查找key所对应的value, 递归算法
    // 若value不存在, 则返回NULL
    private V search(TreeNode root, K key) {

        if (root == null)
            return null;

        if (key.compareTo(root.key) == 0)
            return root.value;
        else if (key.compareTo(root.key) < 0)
            return search(root.left, key);
        else // key > node->key
            return search(root.right, key);
    }

    // 对以node为根的二叉搜索树进行前序遍历, 递归算法
    private void preOrder(TreeNode root) {

        if (root != null) {
            System.out.println(root.key);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    // 对以node为根的二叉搜索树进行中序遍历, 递归算法
    // 也可使用栈来实现
    private void inOrder(TreeNode root) {

        if (root != null) {
            inOrder(root.left);
            System.out.println(root.key);
            inOrder(root.right);
        }
    }

    private void inOrder1(TreeNode root) {
        var stack = new LinkedList<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            // 左节点依次入栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 出栈
            root = stack.pop();
            System.out.println(root.value);
            root = root.right;
        }
    }

    // 对以node为根的二叉搜索树进行后序遍历, 递归算法
    private void postOrder(TreeNode root) {

        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.println(root.key);
        }
    }

    // 返回以node为根的二分搜索树的最小键值所在的节点
    private TreeNode minimum(TreeNode root) {
        if (root.left == null)
            return root;

        return minimum(root.left);
    }

    // 返回以node为根的二分搜索树的最大键值所在的节点
    private TreeNode maximum(TreeNode root) {
        if (root.right == null)
            return root;

        return maximum(root.right);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private TreeNode removeMin(TreeNode root) {
        // 左节点为空，则删除该节点后，右节点上移到该节点位置
        if (root.left == null) {

            var rightTreeNode = root.right;
            root.right = null;
            count--;
            return rightTreeNode;
        }

        root.left = removeMin(root.left);
        return root;
    }

    // 删除掉以node为根的二分搜索树中的最大节点
    // 返回删除节点后新的二分搜索树的根
    private TreeNode removeMax(TreeNode root) {
        // 右节点为空，则删除该节点后，左节点上移到该节点位置
        if (root.right == null) {

            var leftTreeNode = root.left;
            root.left = null;
            count--;
            return leftTreeNode;
        }

        root.right = removeMax(root.right);
        return root;
    }

    // 删除掉以node为根的二分搜索树中键值为key的节点, 递归算法
    // 返回删除节点后新的二分搜索树的根
    private TreeNode remove(TreeNode root, K key) {

        if (root == null)
            return null;

        if (key.compareTo(root.key) < 0) {
            root.left = remove(root.left, key);
            return root;
        } else if (key.compareTo(root.key) > 0) {
            root.right = remove(root.right, key);
            return root;
        } else {   // key == node->key

            // 待删除节点左子树为空的情况
            if (root.left == null) {
                var rightTreeNode = root.right;
                root.right = null;
                count--;
                return rightTreeNode;
            }

            // 待删除节点右子树为空的情况
            if (root.right == null) {
                var leftTreeNode = root.left;
                root.left = null;
                count--;
                return leftTreeNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            var successor = new TreeNode(minimum(root.right));
            count++;

            successor.right = removeMin(root.right);
            successor.left = root.left;

            root.left = root.right = null;
            count--;

            return successor;
        }
    }
}
