package ElementaryAPIs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

@SuppressWarnings({"unused", "unchecked"})
public abstract class IndexPriorityQueue<Key extends Comparable<Key>> {

    private final Comparator<Key> comparator;
    /**
     * items with properties
     */
    private final Key[] keys;
    /**
     * binary heap using 1-based indexing
     */
    private final Integer[] pq;
    /**
     * inverse: qp[pq[i]] = pq[qp[i]] = i
     */
    private final Integer[] qp;
    /**
     * number of  elements on PQ
     */
    private int N;

    /**
     * create indexed priority key with indices 0, 1, ... , N-1
     */
    public IndexPriorityQueue(int maxN, Comparator<Key> comparator) {
        this.comparator = comparator;
        this.keys = (Key[]) new Comparable[maxN + 1];
        this.pq = new Integer[maxN + 1];
        this.qp = new Integer[maxN + 1];
        Arrays.fill(qp, -1);
    }

    /**
     * associate key with index i
     *
     * @param i   index
     * @param key key associated with index i
     */
    public void insert(int i, Key key) {
        this.N++;
        this.qp[i] = this.N;
        this.pq[this.N] = i;
        this.keys[i] = key;
        swim(N);
    }

    /**
     * decrease key for index i
     *
     * @param i   index
     * @param key key associated with index i
     */
    public void changeKey(int i, Key key) {
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * @param i index
     * @return is i an index on priority queue?
     */
    public boolean contains(int i) {
        return this.qp[i] != -1;
    }

    /**
     * remove i and its associated key
     *
     * @param i index
     */
    public void delete(int i) {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty!");

        int index = qp[i];
        exch(index, N--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    protected Key rootKey() {
        return keys[pq[1]];
    }

    protected int rootIndex() {
        return pq[1];
    }

    protected int delRoot() {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty!");

        int indexOfRoot = pq[1];
        exch(1, N--);
        sink(1);
        keys[pq[N + 1]] = null;
        qp[pq[N + 1]] = -1;

        return indexOfRoot;
    }

    /**
     * @return is the priority queue empty?
     */
    public boolean isEmpty() {
        return this.N == 0;
    }

    /**
     * @return number of entries in the priority queue
     */
    public int size() {
        return this.N;
    }

    /**
     * @param i index
     * @return key associated with index i
     */
    public Key keyOf(int i) {
        return keys[pq[i]];
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= this.N) {
            int j = 2 * k;
            if (j < this.N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return comparator.compare(keys[pq[j]], keys[pq[i]]) < 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
}