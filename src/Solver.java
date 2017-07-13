import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.Stack;


public class Solver {

    private final int moves;
    private final boolean isSolvable;
    private Stack<Board> solution = new Stack<>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));

        Board twin = initial.twin();
        MinPQ<SearchNode> tpq = new MinPQ<>();
        tpq.insert(new SearchNode(twin, 0, null));

        SolutionObj sol = solve(pq, tpq);
        this.moves = sol.moves;
        this.isSolvable = sol.isSolvable;
        this.solution = buildSolution(sol.solution);
    }

    private Stack<Board> buildSolution(SearchNode finl) {
        Stack<Board> sol = new Stack<>();

        while (finl.previous != null) {
            sol.push(finl.board);
            finl = finl.previous;
        }
        sol.push(finl.board);

        return sol;
    }

    private class SolutionObj {

        public final SearchNode solution;
        public final int moves;
        public final boolean isSolvable;

        private SolutionObj(SearchNode solution, int moves, boolean isSolvable) {
            this.moves = moves;
            this.isSolvable = isSolvable;
            this.solution = solution;
        }
    }

    private SolutionObj solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> tpq) {
        SearchNode node = pq.min(), tnode = pq.min();

        while(!node.board.isGoal() || !tnode.board.isGoal()) {
            node = pq.delMin();
            tnode = tpq.delMin();

            for (Board neighbor: node.board.neighbors()) {
                if (neighbor.isGoal()) {
                    node = new SearchNode(neighbor, node.moves+1, node);
                    return new SolutionObj(node, node.moves, true);
                }
                else if (node.previous == null || !node.previous.board.equals(neighbor))
                    pq.insert(new SearchNode(neighbor, node.moves+1, node));
            }
            for (Board tneighbor: tnode.board.neighbors()) {
                if (tneighbor.isGoal()) {
                    return new SolutionObj(null, -1, false);
                }
                else if (tnode.previous == null || !tnode.previous.board.equals(tneighbor))
                    tpq.insert(new SearchNode(tneighbor, tnode.moves+1, tnode));
            }
        }

        if (node.board.isGoal()) { return new SolutionObj(node, node.moves, true); }
        else                     { return new SolutionObj(null, -1, false); }
    }

    private class SearchNode implements Comparable<SearchNode>{

        private Board board;
        private int moves;
        private SearchNode previous;
        public int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if      (this.priority > that.priority) return +1;
            else if (this.priority < that.priority) return -1;
            else                                        return  0;
        }
    }

    public boolean isSolvable() { return this.isSolvable; }

    public int moves() { return this.moves; }

    public Iterable<Board> solution() {
        if (moves != -1) return new solution();
        else             return null;
    }

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
