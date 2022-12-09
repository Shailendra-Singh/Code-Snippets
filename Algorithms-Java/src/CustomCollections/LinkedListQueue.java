package CustomCollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListQueue<Item> implements Iterable<Item> {

    private Node first, last;
    private int N;

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.N;
    }

    public void enqueue(Item item) {
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        if (this.isEmpty())
            this.first = this.last;
        else
            oldLast.next = last;
        this.N++;
    }

    public Item dequeue() {
        if (this.isEmpty())
            throw new UnsupportedOperationException("Queue is empty!");

        Item dequeuedItem = first.item;
        first = first.next;
        this.N--;
        return dequeuedItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new FirstInFirstOutIterator();
    }

    private class Node {
        Item item;
        Node next;
    }

    private class FirstInFirstOutIterator implements Iterator<Item> {

        private Node f = first;

        @Override
        public boolean hasNext() {
            return this.f != null;
        }

        @Override
        public Item next() {

            if (!hasNext())
                throw new NoSuchElementException();

            Item dequeuedItem = this.f.item;
            this.f = this.f.next;
            return dequeuedItem;
        }
    }
}