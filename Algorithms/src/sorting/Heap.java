package com.algorithms.sorting;


public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N/2; k >= 1; k--) { sink(a, k, N); }
        while ( N > 1) {
            exch(a, 1, N);
            sink(a, 1, --N);
        }
    }

    private static void sink(Comparable[] a, int k, int N) {
        while(k*2 <= N) {
            int j = k*2;
            if (j < N && less(a[j], a[j+1])) j++;
            if(!less(a[k], a[j])) break;;
            exch(a, k, j);
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
