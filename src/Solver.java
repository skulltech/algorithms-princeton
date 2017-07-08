import java.util.Iterator;

public class Solver {

    private int moves;
    private boolean isSolvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();


    }

    public boolean isSolvable() { return this.isSolvable; }

    public int moves() { return this.moves; }

    public Iterable<Board> solution() { return new solution(); }

    private class solution implements Iterable<Board> {

        public Iterator<Board> iterator() { return new SolutionBoardsIterator(); }

        private class SolutionBoardsIterator implements Iterator<Board> {

            public boolean hasNext() {
                return false;
            }

            public Board next() {
                return null;
            }

            public void remove() { throw new UnsupportedOperationException(); }
        }
    }
}
