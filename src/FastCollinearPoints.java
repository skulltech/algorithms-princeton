import javax.sound.sampled.Line;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] segments;

    private static boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    private static boolean hasNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i].equals(null)) return true;
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
        if (points.equals(null) || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException();

        int N = points.length;

        LineSegment[] buffer = new LineSegment[N];
        int counter = 0;
        for (int i = 0; i < N; i++) {
            Comparator<Point> slopeorder = points[i].slopeOrder();
            Arrays.sort(points, slopeorder);

            for (int j = 0; j < N; j++) {
                int c = 1;
                while (collinear(new Point[]{points[i], points[j], points[j + c]})) c++;
                if (c>2) buffer[counter++] = new LineSegment(points[i], points[j+c-1]);
            }
        }

        segments = new LineSegment[counter];
        for (int i = 0; i < counter; i++) this.segments[i] = buffer[i];
    }

    public int numberOfSegments() { return this.segments.length; }

    public LineSegment[] segments() { return this.segments; }
}
