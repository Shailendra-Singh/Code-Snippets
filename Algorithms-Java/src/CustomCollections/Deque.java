package CustomCollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    /**
     * construct an empty deque
     */
    public Deque() {
        this.count = 0;
        this.first = this.last = null;
    }

    /**
     * @return is the deque empty?
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @return return the number of items on the deque
     */
    public int size() {
        return this.count;
    }

    /**
     * @param item add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;

        this.count++;

        if (this.size() == 1) this.last = this.first;
        else oldFirst.prev = this.first;
    }

    /**
     * @param item add the item to the back
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.prev = oldLast;

        this.count++;

        if (this.size() == 1) this.first = this.last;
        else oldLast.next = this.last;
    }

    /**
     * @return remove and return the item from the front
     */
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();

        Item item = this.first.item;
        this.first = this.first.next;

        this.count--;

        if (this.isEmpty()) this.first = this.last = null;
        else this.first.prev = null; // avoids loitering

        return item;
    }

    /**
     * @return remove and return the item from the back
     */
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();

        Item item = this.last.item;
        this.last = this.last.prev;

        this.count--;

        if (this.isEmpty()) this.first = this.last = null;
        else this.last.next = null; // avoids loitering

        return item;
    }

    /**
     * @return return an iterator over items in order from front to back
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node f = first;

        @Override
        public boolean hasNext() {
            return f != null;
        }


        @Override
        public Item next() {

            if (!this.hasNext()) throw new NoSuchElementException();

            Item item = f.item;
            f = f.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }
}