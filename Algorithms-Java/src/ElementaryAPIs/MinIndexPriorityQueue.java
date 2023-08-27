package ElementaryAPIs;

import Common.CommonOperation;

@SuppressWarnings("unused")
public class MinIndexPriorityQueue<Key extends Comparable<Key>> extends IndexPriorityQueue<Key> {

    /**
     * create indexed priority key with indices 0, 1, ... , N-1
     */
    public MinIndexPriorityQueue(int maxN) {
        super(maxN, new CommonOperation.MinComparator<>());
    }

    /**
     * @return a minimal key
     */
    public Key minKey() {
        return this.rootKey();
    }

    /**
     * @return a minimal key's index
     */
    public int minIndex() {
        return this.rootIndex();
    }

    /**
     * remove min key from priority queue
     *
     * @return index associated with min key
     */
    public int delMin() {
        return this.delRoot();
    }
}