package com.algorithms.tree;

public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(Key key, Value val) {}

    public Value get(Key key) {}

    public void delete(Key key) {}

    public Iterable<Key> iterator() {}
}
