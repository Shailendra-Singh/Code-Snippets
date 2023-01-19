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

    @Override
    protected Node put(Node h, Key key, Value value) {
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