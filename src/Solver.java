import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;


public class Solver {

    private int moves;
    private boolean isSolvable;
    private Stack<Board> solution = new Stack<>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Board> pq = new MinPQ<>(new ManhattanComparator());
        pq.insert(initial);

        Board twin = initial.twin();
        MinPQ<Board> tpq = new MinPQ<>(new ManhattanComparator());
        tpq.insert(twin);

        Board search = initial, tsearch = twin;
        while(!search.isGoal() || !tsearch.isGoal()) {
            search = pq.delMin();
            tsearch = tpq.delMin();
            solution.push(search);
            moves = moves + 1;

            for (Board neighbor: search.neighbors()) {
                if (!search.equals(neighbor)) pq.insert(neighbor);
            }
            for (Board tneighbor: tsearch.neighbors()) {
                if (!tsearch.equals(tneighbor)) tpq.insert(tneighbor);
            }
        }

        if (search.isGoal()) isSolvable = true;
        else                 isSolvable = false;
    }

    private class ManhattanComparator implements Comparator<Board> {

        public int compare(Board v, Board w) {
            if      (v.manhattan() < w.manhattan()) return -1;
            else if (v.manhattan() < w.manhattan()) return +1;
            else                                    return  0;
        }
    }

    public boolean isSolvable() { return this.isSolvable; }

    public int moves() { return this.moves; }

    public Iterable<Board> solution() { return new solution(); }

    private class solution implements Iterable<Board> {

        public Iterator<Board> iterator() { return new SolutionBoardsIterator(); }

        private class SolutionBoardsIterator implements Iterator<Board> {

            public boolean hasNext() { return !Solver.this.solution.empty(); }

            public Board next() { return Solver.this.solution.pop(); }

            public void remove() { throw new UnsupportedOperationException(); }
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
