package CustomCollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item> {

    @SuppressWarnings("FieldMayBeFinal")
    private int N;
    private Item[] stackArray;

    public ResizingArrayStack() {
        this.N = 0;
        //noinspection unchecked
        this.stackArray = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return this.N;
    }

    private void resie(int newSize) {
        //noinspection unchecked
        Item[] resizedStackArray = (Item[]) new Object[newSize];
        //noinspection ManualArrayCopy
        for (int i = 0; i < this.N; i++)
            resizedStackArray[i] = this.stackArray[i];
        this.stackArray = resizedStackArray;
    }

    public void push(Item item) {
        if (this.N == this.stackArray.length) this.resie(this.N * 2);
        this.stackArray[this.N++] = item;
    }

    public Item pop() {
        if(this.isEmpty())
            throw new UnsupportedOperationException("Stack is empty!");

        Item poppedItem = this.stackArray[--this.N];
        this.stackArray[this.N] = null; // Avoid loitering (orphaned object due to decrementing N)
        if (this.N > 0 && this.N < (1 / 4.0) * this.stackArray.length) this.resie(this.stackArray.length / 2);
        return poppedItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if(i==0)
                throw new NoSuchElementException();
            return stackArray[--i];
        }
    }
}