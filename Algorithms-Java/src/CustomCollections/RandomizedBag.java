package CustomCollections;

import java.util.Iterator;

/**
 * Stores item in a bag. Retrieval is randomized for practical operations.
 *
 * @param <Item> type of item
 */
public class RandomizedBag<Item> implements Iterable<Item> {

    private final RandomizedQueue<Item> randomizedQueue;

    public RandomizedBag() {
        this.randomizedQueue = new RandomizedQueue<>();
    }

    /**
     * @param item adds an item to the bag
     */
    public void add(Item item) {
        this.randomizedQueue.enqueue(item);
    }

    /**
     * @return whether the bag is empty or not
     */
    public boolean isEmpty() {
        return this.randomizedQueue.isEmpty();
    }

    /**
     * @return return the number of items in the bag
     */
    public int size() {
        return this.randomizedQueue.size();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<Item> iterator() {
        return this.randomizedQueue.iterator();
    }
}