import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;


public class FastCollinearPoints {

    private LineSegment[] segments;

    private static boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    private static boolean hasNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return true;
        }
        return false;
    }

    private static boolean collinear(Point[] points) {
        for (int p = 1; p < points.length - 1; p++) {
            if (points[0].slopeTo(points[p]) != points[0].slopeTo(points[p+1])) return false;
        }
        return true;
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null || hasNull(points) || hasDuplicate(points)) throw new IllegalArgumentException();

        Stack<Point[]> segmentstack = new Stack<>();
        int N = points.length;
        Arrays.sort(points);
        Point[] basePoints = points.clone();

        for (Point base : basePoints) {
            Comparator<Point> slopeorder = base.slopeOrder();
            Arrays.sort(points, slopeorder);

            for (int j = 1; j < N; j++) {
                int c = 1;
                while (j + c < N && collinear(new Point[]{base, points[j], points[j + c]})) { c++; }
                if (c > 2) {
                    segmentstack.push(new Point[]{base, points[j + c - 1]});
                }
            }
        }

        int size = segmentstack.size();
        Point[][] segments = new Point[size][2];
        for (int i = 0; i < size; i++) segments[i] = segmentstack.pop();

        this.segments = filterSegments(segments);
    }

    private static LineSegment[] createSegments(Point[][] segments) {
        int N = segments.length;
        LineSegment[] created = new LineSegment[N];

        for (int i = 0; i < N; i++) created[i] = new LineSegment(segments[i][0], segments[i][1]);
        return created;
    }

    private static LineSegment[] filterSegments(Point[][] segments) {
        Stack<LineSegment> filtered = new Stack<>();
        int N = segments.length;

        for (int i = 0, c = 0; i < N; i++, c = 0) {
            Point[] cur = segments[i], comp, longest = cur;
            if (cur == null) { continue; }

            while (c < N) {
                comp = segments[c];

                if (comp != null && isSame(cur, comp)) {
                    Point[] ps = {longest[0], longest[1], comp[0], comp[1]};
                    Arrays.sort(ps);
                    if(longest[0] != ps[0] || longest[1] != ps[3]) longest = new Point[] {ps[0], ps[3]};
                    segments[c] = null;
                }

                c++;
            }
            segments[i] = null;
            filtered.push(new LineSegment(longest[0], longest[1]));
        }

        int size = filtered.size();
        LineSegment[] maximals = new LineSegment[size];
        for (int i = 0; i < size; i++) maximals[i] = filtered.pop();
        return maximals;
    }

    private static boolean isSame(Point[] v, Point[] w) {
        if      (abs(v[0].slopeTo(v[1])) != abs(w[0].slopeTo(w[1])))           return false;
        else if (v[0] == w[0] || v[1] == w[1] || v[0] == w[1] || v[1] == w[0]) return true;
        else if (v[0].slopeTo(w[0]) != v[0].slopeTo(w[1]))                     return false;

        return true;
    }

    private static double abs(double a) {
        if (a > +0.00) return +a;
        else           return -a;
    }

    public int numberOfSegments() {
        return this.segments.length;
    }

    public LineSegment[] segments() {
        return this.segments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
