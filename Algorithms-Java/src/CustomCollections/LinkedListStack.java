package CustomCollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListStack<Item> implements Iterable<Item> {

    private Node root;
    private int N;

    public int size() {
        return this.N;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void push(Item item) {
        Node oldRoot = this.root;
        this.root = new Node();
        this.root.item = item;
        this.root.next = oldRoot;
        this.N++;
    }

    public Item pop() {
        if (this.isEmpty())
            throw new UnsupportedOperationException("Stack is empty!");
        Item poppedItem = root.item;
        root = root.next;
        this.N--;
        return poppedItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LastInFirstOutIterator();
    }

    private class Node {
        Item item;
        Node next;
    }

    private class LastInFirstOutIterator implements Iterator<Item> {
        private Node r = root;

        @Override
        public boolean hasNext() {
            return this.r != null;
        }

        @Override
        public Item next() {

            if (!hasNext())
                throw new NoSuchElementException();

            Item nextItem = this.r.item;
            this.r = this.r.next;
            return nextItem;
        }
    }
}