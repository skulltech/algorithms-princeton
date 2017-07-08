import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.Stack;


public class Solver {

    private int moves;
    private boolean isSolvable;
    private Stack<Board> solution = new Stack<>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Board> pq = new MinPQ<>();
        pq.insert(initial);

        Board twin = initial.twin();
        MinPQ<Board> twinpq = new MinPQ<>();
        twinpq.insert(twin);

        Board search = initial, twinsearch = twin;
        while(!search.isGoal() || !twinsearch.isGoal()) {
            search = pq.delMin();
            twinsearch = twinpq.delMin();
            solution.push(search);
            moves = moves + 1;

            for (Board neighbor: search.neighbors()) {
                if (!search.equals(neighbor)) pq.insert(neighbor);
            }
            for (Board twinneighbor: twinsearch.neighbors()) {
                if (!twinsearch.equals(twinneighbor)) twinpq.insert(twinneighbor);
            }
        }

        if (search.isGoal()) isSolvable = true;
        else                 isSolvable = false;
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
