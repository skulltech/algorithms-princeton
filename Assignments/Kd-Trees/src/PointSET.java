import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;


public class PointSET {
    private TreeSet<Point2D> set = new TreeSet<>();

    public boolean isEmpty() { return set.isEmpty(); }
    public int size() { return set.size(); }
    public void insert(Point2D p) { set.add(p); }
    public boolean contains(Point2D p) { return set.contains(p); }
    public void draw() {}

    public Iterable<Point2D> range(RectHV rect) {

    }

    public Point2D nearest(Point2D p) {

    }

    public static void main(String[] args) {}
}
