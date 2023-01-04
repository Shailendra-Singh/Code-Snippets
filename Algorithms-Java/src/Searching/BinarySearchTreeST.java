package Searching;

import CustomCollections.LinkedListQueue;

import java.util.NoSuchElementException;
/**
 * Binary Search Tree data structure implements Symbol Table API
 *
 * @param <Key>   Symbol Table's Comparable Key
 * @param <Value> Value associated with Key
 */
@SuppressWarnings({"ReplaceNullCheck", "unused"})
public class BinarySearchTreeST<Key extends Comparable<Key>, Value> extends OrderedSymbolTable<Key, Value> {

    public static final int IN_ORDER = 0, PRE_ORDER = 1, POST_ORDER = 2, REVERSE_ORDER = 3, LEVEL_ORDER = 4;

    private Node root;

    /**
     * @return smallest Comparable
     */
    @Override
    public Key min() {
        if (root == null) throw new NoSuchElementException("Symbol Table Underflow!");
        return min(root).key;
    }

    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    /**
     * @return largest Comparable
     */
    @Override
    public Key max() {
        if (root == null) throw new NoSuchElementException("Symbol Table Underflow!");
        return max(root).key;
    }

    private Node max(Node x) {
        while (x.right != null) x = x.right;
        return x;
    }

    /**
     * @param key item Comparable
     * @return largest Comparable less than or equal to Comparable
     */
    @Override
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x; // root of subtree itself is the floor
        if (cmp < 0) return floor(x.left, key); // find floor in left subtree

        Node t = floor(x.right, key);
        if (t != null) return t; // a smaller key found in right subtree
        return x; // no key is smaller in right subtree, so return best found so far
    }

    /**
     * @param key item Comparable
     * @return smallest Comparable greater than or equal to Comparable
     */
    @Override
    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x; // root of subtree itself is the ceiling
        if (cmp > 0) return ceiling(x.right, key); // find ceiling in right subtree

        Node t = ceiling(x.left, key);
        if (t != null) return t; // a larger key found in left subtree
        return x; // no key is larger in left subtree, so return best found so far
    }

    /**
     * @param key item Comparable
     * @return number of keys less than Comparable
     */
    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }

    /**
     * @param k rank
     * @return Comparable of rank k
     */
    @Override
    public Key select(int k) {
        Node x = root;
        while (x != null) {
            int cmp = k - rank(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.key;
        }
        return null;
    }

    /**
     * delete smallest Comparable
     */
    @Override
    public void deleteMin() {
        if (root == null) throw new NoSuchElementException("Symbol Table Underflow!");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * delete largest Comparable
     */
    @Override
    public void deleteMax() {
        if (root == null) throw new NoSuchElementException("Symbol Table Underflow!");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * put key-value pair into the table
     * (remove key from table if value is null)
     *
     * @param key   item key
     * @param value value associated with given key
     */
    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.val = value; // update value if key is already present
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * @param key item key
     * @return value paired with key (null if key is absent)
     */
    @Override
    public Value get(Key key) {
        Node x = get(root, key);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x;
        }
        return null;
    }

    /**
     * @param key remove key (and its value) from table
     */
    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    /**
     * @param x   root node to search target key
     * @param key key to be deleted
     * @return updates all node connection using Hubbard deletion method
     */
    private Node delete(Node x, Key key) {
        if (x == null) return null;

        // traverse till search hit (when cmp == 0)
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right; // no left child
            if (x.right == null) return x.left; // no right child

            // search hit with two children
            Node t = x; // replace with successor
            x = min(t.right); // find the minimum in the right subtree
            x.right = deleteMin(t.right); // delete it and assign to min-right's right
            x.left = t.left; // assign x's left to min-right's left
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * @return is the table empty?
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return number of keys in symbol table
     */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    /**
     * @param lo Comparable lower-bound
     * @param hi Comparable upper-bound
     * @return number of keys in [lo..hi]
     */
    @Override
    public int size(Key lo, Key hi) {
        return size(get(root, root.key)) - (size(floor(root, hi)) + size(ceiling(root, lo))) + 1;
    }

    /**
     * Iterates BST in inorder traversal, which yields keys in ascending order
     *
     * @return an iterator over all keys in the table
     */
    @Override
    public Iterable<Key> keys() {
        return ordered_traversal(BinarySearchTreeST.IN_ORDER);
    }

    /**
     * @param code <br>BinarySearchTreeST.IN_ORDER: In-order traversal <br>
     *             BinarySearchTreeST.PRE_ORDER: Pre-order traversal <br>
     *             BinarySearchTreeST.POST_ORDER: Post-order traversal <br>
     *             BinarySearchTreeST.LEVEL_ORDER: Level order traversal <br>
     *             BinarySearchTreeST.REVERSE_ORDER: Reverse order traversal <br>
     * @return iterator using given order-code
     */
    public Iterable<Key> ordered_traversal(int code) {
        LinkedListQueue<Key> q = new LinkedListQueue<>();
        switch (code) {
            case BinarySearchTreeST.PRE_ORDER -> pre_order(root, q);
            case BinarySearchTreeST.POST_ORDER -> post_order(root, q);
            case BinarySearchTreeST.REVERSE_ORDER -> reverse_order(root, q);
            case BinarySearchTreeST.LEVEL_ORDER -> level_order(root, q);
            default -> in_order(root, q, null, null);
        }
        return q;
    }

    private void in_order(Node x, LinkedListQueue<Key> q, Key lo, Key hi) {
        if (x == null) return;
        int cmp1 = 0, cmp2 = 0;
        if (lo != null && hi != null) {
            cmp1 = lo.compareTo(x.key);
            cmp2 = hi.compareTo(x.key);
        }
        in_order(x.left, q, lo, hi);
        if (cmp1 <= 0 && cmp2 >= 0) q.enqueue(x.key);
        in_order(x.right, q, lo, hi);
    }

    private void pre_order(Node x, LinkedListQueue<Key> q) {
        if (x == null) return;
        q.enqueue(x.key);
        pre_order(x.left, q);
        pre_order(x.right, q);
    }

    private void post_order(Node x, LinkedListQueue<Key> q) {
        if (x == null) return;
        post_order(x.left, q);
        post_order(x.right, q);
        q.enqueue(x.key);
    }

    private void reverse_order(Node x, LinkedListQueue<Key> q) {
        if (x == null) return;
        reverse_order(x.right, q);
        q.enqueue(x.key);
        reverse_order(x.left, q);
    }

    private void level_order(Node x, LinkedListQueue<Key> q) {
        LinkedListQueue<Node> aux = new LinkedListQueue<>();
        aux.enqueue(x);
        while (!aux.isEmpty()) {
            Node current = aux.dequeue();
            q.enqueue(current.key);
            if (current.left != null) aux.enqueue(current.left);
            if (current.right != null) aux.enqueue(current.right);
        }
    }

    /**
     * @param lo Comparable lower-bound
     * @param hi Comparable upper-bound
     * @return keys in [lo..hi], in sorted order
     */
    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        LinkedListQueue<Key> q = new LinkedListQueue<>();
        in_order(root, q, lo, hi);
        return q;
    }

    /**
     * Represents a node in a tree with key and value.
     * Left sub-nodes have all keys smaller than this node, right sub-nodes have keys larger than this node.
     */
    private class Node {
        private final Key key;
        private Value val;
        private int count;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
}