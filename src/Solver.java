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

        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));

        Board twin = initial.twin();
        MinPQ<SearchNode> tpq = new MinPQ<>();
        tpq.insert(new SearchNode(twin, 0, null));

        SearchNode finl = solve(pq, tpq);

        if (isSolvable) {
            this.moves = finl.moves;
            buildSolution(finl);
        }
        else {
            this.moves = -1;
        }
    }

    private void buildSolution(SearchNode finl) {
        while (finl.previous != null) {
            solution.push(finl.board);
            finl = finl.previous;
        }
        solution.push(finl.board);
    }

    private SearchNode solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> tpq) {
        SearchNode node = pq.min(), tnode = pq.min();

        while(!node.board.isGoal() || !tnode.board.isGoal()) {
            node = pq.delMin();
            tnode = tpq.delMin();

            for (Board neighbor: node.board.neighbors()) {
                if (node.previous == null || !node.previous.board.equals(neighbor))
                    if (neighbor.isGoal()) {
                        isSolvable = true;
                        return new SearchNode(neighbor, node.moves+1, node);
                    }
                    pq.insert(new SearchNode(neighbor, node.moves+1, node));
            }
            for (Board tneighbor: tnode.board.neighbors()) {
                if (tnode.previous == null || !tnode.previous.board.equals(tneighbor))
                    if (tneighbor.isGoal()) {
                        isSolvable = false;
                        return new SearchNode(tneighbor, tnode.moves+1, tnode);
                    }
                    tpq.insert(new SearchNode(tneighbor, tnode.moves+1, tnode));
            }
        }

        if (node.board.isGoal()) {
            isSolvable = true;
            return node;
        }
        else {
            isSolvable = false;
            return tnode;
        }
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
