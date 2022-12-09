package CustomCollections;

import java.util.Iterator;

public class LinkedListBag<Item> implements Iterable<Item>{

    private Node first;

    public void add(Item item){
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        first.next = oldFirst;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class Node {
        Item item;
        Node next;
    }

    private class ListIterator implements Iterator<Item>{

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
