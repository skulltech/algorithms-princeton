package com.algorithms.stack;


public class ArrayStackOfStrings {

    private String[] stack;
    private int N = 0;

    public ArrayStackOfStrings(int capacity) {
        stack = new String[capacity];
    }

    public boolean isEmpty() {
        return (N == 0);
    }

    public void push(String item) {
        stack[N++] = item;
    }

    public String pop() {
        String item = stack[--N];
        stack[N] = null;
        return item;
    }
}
