package ElementaryAPIs;

import Common.CommonOperation;

@SuppressWarnings("unused")
public class MaxIndexPriorityQueue<Key extends Comparable<Key>> extends IndexPriorityQueue<Key> {

    /**
     * create indexed priority key with indices 0, 1, ... , N-1
     */
    public MaxIndexPriorityQueue(int maxN) {
        super(maxN, new CommonOperation.MaxComparator<>());
    }

    /**
     * @return a maximal key
     */
    public Key maxKey() {
        return this.rootKey();
    }

    /**
     * @return a maximal key's index
     */
    public int maxIndex() {
        return this.rootIndex();
    }

    /**
     * remove max key from priority queue
     *
     * @return index associated with max key
     */
    public int delMax() {
        return this.delRoot();
    }
}