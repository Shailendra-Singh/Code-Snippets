package ElementaryAPIs;

import Common.CommonOperation;

@SuppressWarnings({"rawtypes", "unused"})
public class MinPriorityQueue<Key extends Comparable<Key>> extends PriorityQueue<Key> {
    public MinPriorityQueue() {
        this(null);
    }

    public MinPriorityQueue(Comparable[] keys) {
        super(keys, new CommonOperation.MinComparator<>());
    }

    /**
     * @return a minimal key
     */
    public Key getMin() {
        return this.getRoot();
    }

    /**
     * remove min key from priority queue
     *
     * @return a minimal key
     */
    public Key delMin() {
        return this.delRoot();
    }
}