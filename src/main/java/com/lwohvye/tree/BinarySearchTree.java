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

import java.util.*;

// 二分搜索树的特性
//  顺序性：二分搜索树可以当做查找表的一种实现。
//    我们使用二分搜索树的目的是通过查找 key 马上得到 value。minimum、maximum、successor（后继）、predecessor（前驱）、floor（地板）、ceil（天花板、rank（排名第几的元素）、select（排名第n的元素是谁）这些都是二分搜索树顺序性的表现。
//  局限性：二分搜索树在时间性能上是具有局限性的。二叉搜索树可能退化成链表
public class BinarySearchTree<K extends Comparable, V> {
    // 树中的节点为私有的类, 外界不需要了解二分搜索树节点的具体实现
    private class TreeNode {
        // 这种定义方式，二分搜索树的相关性质是基于key的，K 要实现Comparable接口。部分情况下，树中可能没有value。
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
        levelOrder(root);
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
    public boolean isValidBST(TreeNode root) {
        var stack = new LinkedList<TreeNode>();
        // 初始为null，首次使用前要初始化
        K inorder = null;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 此时root已为最左边的节点，在标准的二分搜索树上，应为最小的值
            if (inorder == null) {
                inorder = root.key;
                root = root.right;
                continue;
            }
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            //  A.compartTo(B)。若 A = B则返回0，若A > B则返回正值，若 A < B 则返回负值
            if (root.key.compareTo(inorder) <= 0) {
                return false;
            }
            inorder = root.key;
            root = root.right;
        }
        return true;
    }

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

    // 前序遍历。迭代。先当前再左再右
    public List<K> preorderTraversal(TreeNode root) {
        var stack = new ArrayDeque<TreeNode>();
        var ans = new ArrayList<K>();

        while (root != null || !stack.isEmpty()) {
            // 左节点依次入栈
            while (root != null) {
                ans.add(root.key);
                stack.push(root);
                root = root.left;
            }
            // 出栈
            root = stack.pop();
            root = root.right;
        }
        return ans;
    }

    //有一种巧妙的方法可以在线性时间内，只占用常数空间来实现前序遍历。这种方法由 J. H. Morris 在 1979 年的论文「Traversing Binary Trees Simply and Cheaply」中首次提出，因此被称为 Morris 遍历。
    //
    //Morris 遍历的核心思想是利用树的大量空闲指针，实现空间开销的极限缩减。
    //Morris 遍历算法整体步骤如下（假设当前遍历到的节点为 x）：
    //
    //如果 x 无左子节点，先将 x 的值加入答案数组，再访问 x 的右子节点，即 x=x.right。
    //如果 x 有左子节点，则找到 x 左子树上最右的节点（即左子树中序遍历的最后一个节点，x 在中序遍历中的前驱节点），我们记为 predecessor。根据 predecessor 的右子节点是否为空，进行如下操作。
    //  如果 predecessor 的右子节点为空（初始情况下），则将其右子节点指向 x，将 x 的值加入答案数组，然后访问 x 的左子节点，即 x=x.left。
    //  如果 predecessor 的右子节点不为空(已经走过了上面那步，此时右子节点就是x)，则此时其右子节点指向 x，说明我们已经遍历完 x 的左子树（因为），我们将 predecessor 的右子节点置空，
    //      然后访问 x 的右子节点，即 x=x.right。
    //重复上述操作，直至访问完整棵树
    //
    public List<K> preorderTraversalMorris(TreeNode root) {
        var res = new ArrayList<K>();
        TreeNode predecessor;

        while (root != null) {
            if (root.left != null) {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root)
                    predecessor = predecessor.right;

                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    res.add(root.key);
                    root = root.left;
                } else {
                    // 说明左子树已经访问完了，我们需要断开链接
                    predecessor.right = null;
                    root = root.right;
                }
            } else {
                // 如果没有左子节点，则直接访问右子节点
                res.add(root.key);
                root = root.right;
            }
        }
        return res;
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

    public List<K> inorderTraversal(TreeNode root) {
        var stack = new ArrayDeque<TreeNode>();
        var ans = new ArrayList<K>();

        while (root != null || !stack.isEmpty()) {
            // 左节点依次入栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 出栈
            root = stack.pop();
            ans.add(root.key);
            root = root.right;
        }
        return ans;
    }

    //Morris 遍历算法是另一种遍历二叉树的方法，它能将非递归的中序遍历空间复杂度降为 O(1)。
    //
    //Morris 遍历算法整体步骤如下（假设当前遍历到的节点为 x）：
    //
    //如果 x 无左子节点，先将 x 的值加入答案数组，再访问 x 的右子节点，即 x=x.right。
    //如果 x 有左子节点，则找到 x 左子树上最右的节点（即左子树中序遍历的最后一个节点，x 在中序遍历中的前驱节点），我们记为 predecessor。根据 predecessor 的右子节点是否为空，进行如下操作。
    //  如果 predecessor 的右子节点为空（初始情况下），则将其右子节点指向 x，然后访问 x 的左子节点，即 x=x.left。
    //  如果 predecessor 的右子节点不为空(已经走过了上面那步，此时右子节点就是x)，则此时其右子节点指向 x，说明我们已经遍历完 x 的左子树（因为），我们将 predecessor 的右子节点置空，
    //      将 x 的值加入答案数组，然后访问 x 的右子节点，即 x=x.right。
    //重复上述操作，直至访问完整棵树
    //
    public List<K> inorderTraversalMorris(TreeNode root) {
        var res = new ArrayList<K>();
        TreeNode predecessor;

        while (root != null) {
            if (root.left != null) {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root)
                    predecessor = predecessor.right;

                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                } else {
                    // 说明左子树已经访问完了，我们需要断开链接
                    res.add(root.key);
                    predecessor.right = null;
                    root = root.right;
                }
            } else {
                // 如果没有左子节点，则直接访问右子节点
                res.add(root.key);
                root = root.right;
            }
        }
        return res;
    }

    // 对以node为根的二叉搜索树进行后序遍历, 递归算法
    private void postOrder(TreeNode root) {

        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.println(root.key);
        }
    }

    public List<K> postorderTraversal(TreeNode root) {
        var stack = new ArrayDeque<TreeNode>();
        var ans = new ArrayList<K>();

        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 无右子节点，或右子节点为之前指定的节点x
            if (root.right == null || root.right == prev) {
                ans.add(root.key);
                prev = root;
                root = null;
            } else {
                stack.push(root);
                root = root.right;
            }
        }
        return ans;
    }

    //Morris 遍历算法整体步骤如下（假设当前遍历到的节点为 x）：
    //
    //如果 x 无左子节点，访问 x 的右子节点，即 x=x.right。
    //如果 x 有左子节点，则找到 x 左子树上最右的节点（即左子树中序遍历的最后一个节点，x 在中序遍历中的前驱节点），我们记为 predecessor。根据 predecessor 的右子节点是否为空，进行如下操作。
    //  如果 predecessor 的右子节点为空（初始情况下），则将其右子节点指向 x，然后访问 x 的左子节点，即 x=x.left。
    //  如果 predecessor 的右子节点不为空(已经走过了上面那步，此时右子节点就是x)，则此时其右子节点指向 x，说明我们已经遍历完 x 的左子树（因为），我们将 predecessor 的右子节点置空，
    //      倒序输出从当前节点的左子节点到该前驱节点这条路径上的所有节点，然后访问 x 的右子节点，即 x=x.right。
    //重复上述操作，直至访问完整棵树
    //
    public List<K> postorderTraversalMorris(TreeNode root) {
        var ans = new ArrayList<K>();

        var curRoot = root;
        TreeNode predecessor;

        while (curRoot != null) {
            if (curRoot.left != null) {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = curRoot.left;
                while (predecessor.right != null && predecessor.right != curRoot)
                    predecessor = predecessor.right;

                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = curRoot;
                    curRoot = curRoot.left;
                } else {
                    // 说明左子树已经访问完了，我们需要断开链接
                    predecessor.right = null;
                    // 倒序输出。。。
                    addPath(ans, curRoot.left);
                    curRoot = curRoot.right;
                }
            } else {
                // 如果没有左子节点，则直接访问右子节点
                curRoot = curRoot.right;
            }
        }
        // 倒序输出。。。
        addPath(ans, root);
        return ans;
    }

    // 逆序输出
    public void addPath(List<K> ans, TreeNode root) {
        if (root == null)
            return;
        ans.add(root.key);
        root = root.right;

        int preIndex = 1;
        while (root != null) {
            //  在指定位置逆序插入
            ans.add(ans.size() - 1 - (preIndex++), root.key);
            root = root.right;
        }
    }

    // 层序遍历-bfs
    public List<List<K>> levelOrder(TreeNode root) {
        var ret = new ArrayList<List<K>>();
        if (root == null)
            return ret;

        var queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            var level = new ArrayList<K>();
            // 每次处理一层
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.key);
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }
            ret.add(level);
        }
        return ret;
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
