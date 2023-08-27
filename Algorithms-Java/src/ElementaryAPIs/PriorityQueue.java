package ElementaryAPIs;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static Common.CommonOperation.exch;
import static Common.CommonOperation.less;

@SuppressWarnings({"unused", "rawtypes", "unchecked", "SameParameterValue"})
public class PriorityQueue<Key> implements Iterable<Key> {

    private static final int MIN_SIZE = 9;
    protected Comparator<Key> comparator;
    protected Key[] pq;
    protected int N;

    public PriorityQueue() {
        initialize(null, null);
    }

    public PriorityQueue(Comparable[] keys) {
        initialize(keys, null);
    }

    public PriorityQueue(Comparator<Key> comparator) {
        initialize(null, comparator);
    }

    public PriorityQueue(Object[] keys, Comparator<Key> comparator) {
        initialize(keys, comparator);
    }

    protected void initialize(Object[] keys, Comparator<Key> comparator) {
        this.comparator = comparator;
        this.pq = (Key[]) new Object[MIN_SIZE];
        if (keys == null) return;
        for (Object k : keys) insert((Key) k);
    }

    public void insert(Key item) {
        if (this.N + 1 == this.pq.length) this.resize(this.pq.length * 2 - 1);
        this.pq[++this.N] = item;
        swim(this.N);
    }

    protected Key delRoot() {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty!");

        Key item = this.pq[1];
        exch(pq, 1, this.N);
        pq[this.N--] = null; // avoids loitering
        sink(1);

        if (this.pq.length > MIN_SIZE && this.N < this.pq.length * 0.25) this.resize(this.pq.length / 2 + 1);
        return item;
    }

    protected Key getRoot() {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty!");
        return this.pq[1];
    }

    public int size() {
        return this.N;
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    private void swim(int k) {
        while (k > 1 && less(pq[k], pq[k / 2], comparator)) {
            exch(pq, k / 2, k);
            k /= 2;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(pq[j + 1], pq[j], comparator)) j++;
            if (!less(pq[j], pq[k], comparator)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private void resize(int sz) {
        Key[] temp = (Key[]) new Object[sz];
        if (N > 0) System.arraycopy(this.pq, 1, temp, 1, N);
        this.pq = temp;
    }

    /**
     * @return Priority Queue Iterator
     */
    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return new PriorityQueueIterator();
    }

    private class PriorityQueueIterator implements Iterator<Key> {

        private final int ORIGINAL_LENGTH = PriorityQueue.this.size();
        private final Key[] temp;
        private final int length;
        private int idx;

        public PriorityQueueIterator() {
            this.length = PriorityQueue.this.size();
            this.temp = (Key[]) new Object[length + 1];
            System.arraycopy(PriorityQueue.this.pq, 1, this.temp, 1, this.length);
            idx = 1;
        }

        @Override
        public boolean hasNext() {
            if (ORIGINAL_LENGTH != PriorityQueue.this.size())
                throw new UnsupportedOperationException("Priority Queue cannot be modified while iterating.");

            return idx <= length;
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();

            return temp[idx++];
        }
    }
}