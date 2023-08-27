package ElementaryAPIs;

import Common.CommonOperation;

@SuppressWarnings({"rawtypes", "unused"})
public class MaxPriorityQueue<Key extends Comparable<Key>> extends PriorityQueue<Key> {
    public MaxPriorityQueue() {
        this(null);
    }

    public MaxPriorityQueue(Comparable[] keys) {
        initialize(keys, new CommonOperation.MaxComparator<>());
    }

    /**
     * @return a maximal key
     */
    public Key getMax() {
        return this.getRoot();
    }

    /**
     * remove min key from priority queue
     *
     * @return a maximal key
     */
    public Key delMax() {
        return this.delRoot();
    }
}