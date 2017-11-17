package com.algorithms.sorting;

import edu.princeton.cs.algs4.StdRandom;


public class Random {

    public static void shuffle(Object[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int random = StdRandom.uniform(i + 1);
            exch(a, i, random);
        }
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
