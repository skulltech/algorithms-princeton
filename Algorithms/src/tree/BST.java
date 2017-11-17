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

    public void put(Key key, Value val) { this.root = put(root, key, val); }

    private Node put(Node x, Key key, Value val) {
        if (x==null) return new Node(key, val);

        int cmp = key.compareTo(x.key);
        if      (cmp > 0) x.right = put(x.right, key, val);
        else if (cmp < 0) x.left  = put(x.left,  key, val);
        else                                   x.val = val;

        return x;
    }

    public Value get(Key key) {
        Node x = root;

        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }

        return null;
    }

    /* public void delete(Key key) {} */

    /* public Iterable<Key> iterator() {} */
}
