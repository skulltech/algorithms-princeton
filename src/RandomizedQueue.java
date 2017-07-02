import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size = 0, last = -1, N = 1;

    public RandomizedQueue() {
        queue = (Item[]) new Object[N];
    }

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }

    public void enqueue(Item item) {
        queue[++last] = item;
        size++;

        if (last+1 == N) {
            if (size < N/2) defrag(N);
            else defrag(N * 2);
        }
    }

    public Item dequeue() {
        int rand = StdRandom.uniform(last + 1);
        Item item = queue[rand];
        queue[rand] = null;
        size--;

        if (size < N/4) defrag(N/2);

        return item;
    }

    public Item sample() { return queue[StdRandom.uniform(last + 1)]; }

    private void defrag(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int counter = 0;
        for (int i = 0; i < N; i++) {
            if (queue[i] != null) { copy[counter++] = queue[i]; }
        }
        queue = copy;
    }

    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] items = (Item[]) new Object[N];
        private int current = 0, count = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < last + 1; i++) {
                if (queue[i] != null) { items[count++] = queue[i]; }
            }
        }

        public boolean hasNext() { return current != count-1; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (isEmpty()) throw new NoSuchElementException();
            return items[current++];
        }
    }

    public static void main(String[] args) {}
}