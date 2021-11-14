package com.lwohvye.tree;

import java.util.HashMap;

class TrieNode {
    String word;
    HashMap<Character, TrieNode> next;

    public TrieNode() {
        next = new HashMap<>();
    }
}

class TrieTree {
    public TrieNode root;

    public TrieTree(TrieNode root) {
        this.root = root;
    }

    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.next.containsKey(word.charAt(i)))
                node.next.put(word.charAt(i), new TrieNode());
            node = node.next.get(word.charAt(i));
        }
        node.word = word;
    }

    public String search(String prefix) {
        var node = root;
        for (var c : prefix.toCharArray()) {
            if (!node.next.containsKey(c))
                return "";
            node = node.next.get(c);
        }
        return node.word;
    }
}
/*
class TrieNode {
    String word;
    TrieNode[] next;

    public TrieNode() {
        next = new TrieNode[26];
    }
}

class TrieTree {
    public TrieNode root;

    public TrieTree(TrieNode root) {
        this.root = root;
    }

    public void insert(String word) {
        var node = root;
        for (var i = 0; i < word.length(); i++) {
            if (node.next[word.charAt(i) - 'a'] == null)
                node.next[word.charAt(i) - 'a'] = new TrieNode();
            node = node.next[word.charAt(i) - 'a'];
        }
        node.word = word;
    }

    public String search(String prefix) {
        var node = root;
        for (var c : prefix.toCharArray()) {
            if (node.next[c - 'a'] == null)
                return "";
            node = node.next[c - 'a'];
        }
        return node.word;
    }
}*/
