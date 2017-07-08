package com.algorithms.priorityqueue;


public class MaxPQ< Key extends Comparable<Key> > {

    private Key[] pq;
    private int N = 0;

    public MaxPQ(int capacity) { pq = (Key[]) new Comparable[capacity]; }

    public boolean isEmpty() { return N == 0; }

    public void insert(Key x) {
        pq[++N] = x;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1];
        exch(pq, 1, N--);
        sink(1);
        pq[N+1] = null;
        return max;
    }

    private void swim(int k) {
        while (k > 2 && less(pq[k/2], pq[k])) {
            exch(pq, k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while(k*2 <= N) {
            int j = k*2;
            if (j < N && less(pq[j], pq[j+1])) j++;
            if(!less(pq[k], pq[j])) break;;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
