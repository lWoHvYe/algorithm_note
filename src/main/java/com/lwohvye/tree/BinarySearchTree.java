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
    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }

        public Node(Node node) {
            this.key = node.key;
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        }
    }

    private Node root;  // 根节点
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
        var queue = new LinkedList<Node>();
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
    private Node insert(Node node, K key, V value) {

        if (node == null) {
            count++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) == 0)
            node.value = value;
        else if (key.compareTo(node.key) < 0)
            node.left = insert(node.left, key, value);
        else    // key > node->key
            node.right = insert(node.right, key, value);

        return node;
    }

    // 查看以node为根的二分搜索树中是否包含键值为key的节点, 使用递归算法
    private boolean contain(Node node, K key) {

        if (node == null)
            return false;

        if (key.compareTo(node.key) == 0)
            return true;
        else if (key.compareTo(node.key) < 0)
            return contain(node.left, key);
        else // key > node->key
            return contain(node.right, key);
    }

    // 在以node为根的二分搜索树中查找key所对应的value, 递归算法
    // 若value不存在, 则返回NULL
    private V search(Node node, K key) {

        if (node == null)
            return null;

        if (key.compareTo(node.key) == 0)
            return node.value;
        else if (key.compareTo(node.key) < 0)
            return search(node.left, key);
        else // key > node->key
            return search(node.right, key);
    }

    // 对以node为根的二叉搜索树进行前序遍历, 递归算法
    private void preOrder(Node node) {

        if (node != null) {
            System.out.println(node.key);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // 对以node为根的二叉搜索树进行中序遍历, 递归算法
    private void inOrder(Node node) {

        if (node != null) {
            inOrder(node.left);
            System.out.println(node.key);
            inOrder(node.right);
        }
    }

    // 对以node为根的二叉搜索树进行后序遍历, 递归算法
    private void postOrder(Node node) {

        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.key);
        }
    }

    // 返回以node为根的二分搜索树的最小键值所在的节点
    private Node minimum(Node node) {
        if (node.left == null)
            return node;

        return minimum(node.left);
    }

    // 返回以node为根的二分搜索树的最大键值所在的节点
    private Node maximum(Node node) {
        if (node.right == null)
            return node;

        return maximum(node.right);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {
        // 左节点为空，则删除该节点后，右节点上移到该节点位置
        if (node.left == null) {

            Node rightNode = node.right;
            node.right = null;
            count--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 删除掉以node为根的二分搜索树中的最大节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node) {
        // 右节点为空，则删除该节点后，左节点上移到该节点位置
        if (node.right == null) {

            Node leftNode = node.left;
            node.left = null;
            count--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    // 删除掉以node为根的二分搜索树中键值为key的节点, 递归算法
    // 返回删除节点后新的二分搜索树的根
    private Node remove(Node node, K key) {

        if (node == null)
            return null;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {   // key == node->key

            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                count--;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                count--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = new Node(minimum(node.right));
            count++;

            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;
            count--;

            return successor;
        }
    }
}
