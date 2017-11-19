import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;


public class PointSET {
    private TreeSet<Point2D> set = new TreeSet<>();

    public boolean isEmpty() { return set.isEmpty(); }
    public int size() { return set.size(); }
    public void insert(Point2D p) throws IllegalArgumentException {
        if (p!=null) { set.add(p); }
        else         { throw new IllegalArgumentException(); }
    }
    public boolean contains(Point2D p) { return set.contains(p); }
    public void draw() {
        for (Point2D p: set) { p.draw(); }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null) { throw new IllegalArgumentException(); }

        ArrayList<Point2D> it = new ArrayList<>();
        for (Point2D p: set) { if (rect.contains(p)) { it.add(p); } }
        return it;
    }

    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        Double distance = Double.POSITIVE_INFINITY;

        for (Point2D current: set) {
            double dist = current.distanceTo(p);
            if (dist < distance) {
                distance = dist;
                nearest = current;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {}
}
