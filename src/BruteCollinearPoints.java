public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    private static boolean collinear(Point[] points) {
        for (int p = 0; p < points.length - 1; p++) {
            if (points[0].slopeTo(points[p]) != points[0].slopeTo(points[p])) return false;
        }
        return true;
    }

    private static boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    public BruteCollinearPoints(Point[] points) {
        if (points.equals(null) || hasDuplicate(points)) throw new IllegalArgumentException();

        this.points = points;
        int N = points.length;

        int counter = 0;
        LineSegment[] buffer = new LineSegment[N];
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                for (int k = j; k < N; k++) {
                    for (int l = k; l < N; l++) {
                        Point[] ps = {points[i], points[j], points[k], points[l]};
                        if (collinear(ps)) buffer[counter++] = new LineSegment(points[i], points[l]);
                    }
                }
            }
        }

        segments = new LineSegment[counter];
        for (int i = 0; i < counter; i++) segments[i] = buffer[i];
    }

    public int numberOfSegments() { return segments.length; }

    public LineSegment[] segments() { return segments; }
}
