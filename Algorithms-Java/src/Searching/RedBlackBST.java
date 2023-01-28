package Searching;

/**
 * Left-leaning Red-Black Binary Search Tree Symbol-Table API Implementation
 *
 * @param <Key>   Comparable Key
 * @param <Value> Value associated with Key
 */
@SuppressWarnings({"DuplicatedCode", "unused"})
public class RedBlackBST<Key extends Comparable<Key>, Value> extends BinarySearchTreeST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * put key-value pair into the table
     * (remove key from table if value is null)
     *
     * @param key   item key
     * @param value value associated with given key
     */
    @Override
    public void put(Key key, Value value) {
        this.root = this.put(root, key, value);
        this.root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value) {
        if (h == null) return new Node(key, value);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else h.val = value; // update value if key is already present

        // Red-Black tree operations to maintain perfect black-height
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h); // lean left
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // balance 4-node
        if (isRed(h.left) && isRed(h.right)) flipColors(h); // split 4-node

        h.count = 1 + size(h.left) + size(h.right);

        return h;
    }

    /**
     * delete smallest Comparable
     */
    @Override
    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * delete largest Comparable
     */
    @Override
    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left)) h = rotateRight(h);
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    /**
     * @param key remove key (and its value) from table
     */
    @Override
    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null)) return null;
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    /**
     * (Temporarily) Introduces disruption in red-black tree property on the way down.
     * Assuming that h is red and both h.left and h.left.left are black, make h.left
     * or one of its children red.
     *
     * @param h Node to move left
     * @return h
     */
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    /**
     * (Temporarily) Introduces disruption in red-black tree property on the way down.
     * Assuming that h is red and both h.right and h.right.left are black, make h.right
     * or one of its children red.
     *
     * @param h Node to move right
     * @return h
     */
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) h = rotateRight(h);
        return h;
    }

    /**
     * Fixes the temporary disruption introduced in red-black tree property on the way up
     *
     * @param h current node
     * @return h
     */
    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);

        // Red-Black tree operations to maintain perfect black-height on the way up
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h); // lean left
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // balance 4-node
        if (isRed(h.left) && isRed(h.right)) flipColors(h); // split 4-node

        h.count = 1 + size(h.left) + size(h.right);

        return h;
    }

    /**
     * Orient a (temporarily) right-leaning red link to lean left.
     * Maintains symmetric order and perfect balance by keeping red links left-leaning.
     *
     * @param h Node whose right link is red
     */
    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.count = h.count;
        h.count = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * Orient a left-leaning red link to (temporarily) lean right.
     * Temporary state corresponds to 2-3 Tree's four-link node creation.
     *
     * @param h Node whose left link is red
     */
    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.count = h.count;
        h.count = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * Recolors to split a (temporary) 4-node.
     *
     * @param h incident node
     */
    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = h.right.color = BLACK;
    }

    private boolean isRed(Node x) {
        if (x == null) return BLACK; // null links are black
        return x.color == RED;
    }
}