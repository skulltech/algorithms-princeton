package com.algorithms.sorting;


public class MergeBU {

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        int N = a.length;

        for (int size = 1; size < N; size = size*2) {
            for (int i = 0; i < N; i = i + size) merge(a, aux, i, i+size-1, Math.min(i+size+size-1, N-1));
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        for (int i = lo; i < hi; i++) aux[i] = a[i];

        int i = lo, j = mid + 1;
        for (int k = lo; k < hi; k ++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i ++) {
            if (less(a[i+1], a[i])) return false;
        }
        return true;
    }
}
