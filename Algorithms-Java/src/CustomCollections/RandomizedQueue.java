package CustomCollections;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int last;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.last = -1;
        this.queue = (Item[]) new Object[1];
    }

    /**
     * @return is the randomized queue empty?
     */
    public boolean isEmpty() {
        return this.last == -1;
    }

    /**
     * @return return the number of items on the randomized queue
     */
    public int size() {
        return this.last + 1;
    }

    /**
     * @param item add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // dynamically resize for incoming items
        if (this.size() == this.queue.length) this.resize(this.queue.length * 2);

        this.queue[++this.last] = item;
    }

    /**
     * @return remove and return a random item
     */
    public Item dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniformInt(0, this.last + 1);
        Item item = this.queue[randomIndex];
        this.exch(this.last, randomIndex);
        this.queue[this.last--] = null; // Avoids loitering

        // resize to save memory
        if (this.last < 0.25 * this.queue.length) this.resize(this.queue.length / 2);

        return item;
    }

    /**
     * @return return a random item (but do not remove it)
     */
    public Item sample() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.queue[StdRandom.uniformInt(0, this.last + 1)];
    }

    /**
     * @return return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void resize(int size) {
        if (size <= 0) return;

        Item[] temp = (Item[]) new Object[size];
        if (this.last + 1 >= 0) System.arraycopy(this.queue, 0, temp, 0, this.last + 1);

        this.queue = temp;
    }

    private void exch(int i, int j) {
        Item temp = this.queue[i];
        this.queue[i] = this.queue[j];
        this.queue[j] = temp;
    }

    private class RandomIterator implements Iterator<Item> {
        private final Item[] iteratorItems;
        private int idx;

        public RandomIterator() {
            RandomizedQueue<Item> iteratorQueue = new RandomizedQueue<Item>();
            iteratorItems = (Item[]) new Object[size()];
            for (int i = 0; i <= last; i++)
                iteratorQueue.enqueue(queue[i]);

            for (int i = 0; i <= last; i++)
                iteratorItems[i] = iteratorQueue.dequeue();

            idx = 0;
        }

        @Override
        public boolean hasNext() {
            return idx < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            return iteratorItems[idx++];
        }
    }
}