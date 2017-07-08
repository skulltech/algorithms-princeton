import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class Board {

    private int[][] blocks;
    private int N;
    private int hamming;
    private int manhattan;
    private boolean isGoal;


    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.N = blocks.length;

        calculateHamming();;
        calculateManhattan();
        this.isGoal = ifGoal();
    }

    public int dimension() { return N; }

    public int manhattan() { return this.manhattan; }

    public int hamming() { return this.hamming; }

    private void calculateHamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != (i*N + j + 1) && i*j != 0) count++;
            }
        }
        hamming = count;
    }

    private void calculateManhattan() {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = blocks[i][j];
                if (val != 0) dist = dist + mod((val/3) - i) + mod((val%3) - j);
            }
        }
        manhattan = dist;
    }

    private int mod(int v) {
        if (v > 0) return +v;
        else       return -v;
    }

    private boolean ifGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != ((i*N + j) % N*N)) return false;
            }
        }
        return true;
    }

    public boolean isGoal() { return this.isGoal; }

    public Board twin() { return new Board(exchrnd(blocks, N)); }

    private static int[][] exchrnd(int[][] input, int N) {
        int[][] blocks = input.clone();

        int i = StdRandom.uniform(N), j = StdRandom.uniform(N);
        int swap = blocks[i][j];
        blocks[i][j] = blocks[j][i];
        blocks[j][i] = swap;

        return blocks;
    }

    private static int[][] exch(int[][] input, int fi, int fj, int si, int sj) {
        int[][] output = input.clone();

        int swap = input[fi][fj];
        input[fi][fj] = input[si][sj];
        input[si][sj] = swap;

        return output;
    }

    public boolean equals(Board that) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j< N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() { return new Neighbors(this.blocks); }

    private class Neighbors implements  Iterable<Board> {
        private int[][] base;
        private Stack<Board> neighbors;

        public Neighbors(int[][] base) {
            this.base = base;
            getNeighbors();
        }

        private void getNeighbors() {
            int bi = 0, bj = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (base[i][j] == 0) {
                        bi = j;
                        bj = j;
                    }
                }
            }

            int[][] a = {{bi-1, bj}, {bi+1, bj}, {bi, bj-1}, {bi, bj+1}};
            for (int[] crdnt: a) {
                try { neighbors.push(new Board(exch(base, bi, bj, crdnt[0], crdnt[1]))); }
                catch (IndexOutOfBoundsException e) { /* Do nothing. */ }
            }
        }

        public Iterator<Board> iterator() { return new NeighborsIterator(); }

        private class NeighborsIterator implements Iterator<Board> {
            public boolean hasNext() { return !neighbors.isEmpty(); }

            public Board next() { return neighbors.pop(); }

            public void remove() { throw new UnsupportedOperationException(); }
        }

    }

    public String toString() {
        String rep = Integer.toString(N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) { rep.concat(' ' + Integer.toString(blocks[i][j])); }
            rep.concat(System.lineSeparator());
        }

        return rep;
    }

    public static void main(String[] args) {
    }

}
