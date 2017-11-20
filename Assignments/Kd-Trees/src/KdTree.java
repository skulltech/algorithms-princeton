import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;


public class KdTree {
    private Node root;
    private int N;

    private class Node {
        boolean vertical;
        Node left, right;
        Point2D point;

        private Node(Point2D p, boolean vertical) {
            point = p;
            this.vertical = vertical;
        }

        private int compare(Point2D p) {
            if (point.compareTo(p)==0) { return 0; }

            if (vertical) {
                if      (p.x() < point.x()) { return -1; }
                else if (p.x() > point.x()) { return  1; }
                else                        { return -1; }
            }
            else {
                if      (p.y() < point.y()) { return -1; }
                else if (p.y() > point.y()) { return  1; }
                else                        { return -1; }
            }
        }

        private int compare(RectHV rect) {
            if      ((!vertical && rect.ymax() < point.y()) || (vertical && rect.xmax() < point.x())) { return -1; }
            else if ((!vertical && rect.ymin() > point.y()) || (vertical && rect.xmin() > point.x())) { return  1; }
            else                                                                        { return  0; }
        }

        private double verticalDist(Point2D p) {
            if (vertical) { return Math.abs(p.x() - point.x()); }
            else          { return Math.abs(p.y() - point.y()); }
        }
    }

    public int size()        { return N;      }
    public boolean isEmpty() { return (N==0); }

    private Node insert(Node node, Point2D p, boolean vertical) {
        if (node==null) {
            this.N++;
            return new Node(p, vertical);
        }

        assert (node.vertical == vertical);
        int cmp = node.compare(p);
        if      (cmp < 0) { node.left  = insert(node.left,  p, !vertical); }
        else if (cmp > 0) { node.right = insert(node.right, p, !vertical); }

        return node;
    }

    public void insert(Point2D p) {
        root = insert(root, p, false);
    }

    public boolean contains(Point2D p) {
        Node node = root;

        while (node!=null) {
            int cmp = node.compare(p);
            if      (cmp < 0) { node = node.left;  }
            else if (cmp > 0) { node = node.right; }
            else              { return true;       }
        }

        return false;
    }

    private void draw(Node node) {
        if (node==null) return;

        this.draw(node.left);
        node.point.draw();
        this.draw(node.right);
    }

    public void draw() { this.draw(this.root); }

    private void range(ArrayList<Point2D> list, Node node, RectHV rect) {
        if (node==null) return;

        int cmp = node.compare(rect);

        if      (cmp > 0) { range(list, node.right, rect); }
        else if (cmp < 0) { range(list, node.left,  rect); }
        else {
            if (rect.contains(node.point)) { list.add(node.point); }
            range(list, node.left, rect);
            range(list, node.right, rect);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null) { throw new IllegalArgumentException(); }

        ArrayList<Point2D> it = new ArrayList<>();
        this.range(it, this.root, rect);
        return it;
    }

    private class Champion {
        private Point2D point;
        private Double distance;

        private Champion(Point2D point, Double distance) {
            this.point = point;
            this.distance = distance;
        }
    }


    public Point2D nearest(Point2D p) {
        if (isEmpty()) { return null; }
        return this.nearest(this.root, new Champion(this.root.point, Double.POSITIVE_INFINITY), p).point;
    }

    private Champion nearest(Node node, Champion champ, Point2D p) {
        if (node==null) return champ;

        Double dist = node.point.distanceTo(p);
        if (dist < champ.distance) {
            champ.distance = dist;
            champ.point = node.point;
        }

        int cmp = node.compare(p);
        if (cmp < 0) {
            champ = nearest(node.left, champ, p);
//            System.out.println(node.point);
//            System.out.println(p);
//            System.out.println(node.verticalDist(p));
//            System.out.println(champ.distance);
            if (node.verticalDist(p) < champ.distance) {
                champ = nearest(node.right, champ, p);
            }
        }
        else if (cmp > 0) {
            champ = nearest(node.right, champ, p);
            if (node.verticalDist(p) < champ.distance) { champ = nearest(node.left, champ, p); }
        }
        return champ;
    }

    public static void main(String[] args) {
        KdTree kdt = new KdTree();
        PointSET ps = new PointSET();

        String filename = "input10.txt";
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            ps.insert(p);
        }

        System.out.println(kdtree.nearest(new Point2D(.9, .5)));
        System.out.println(ps.nearest(new Point2D(.9, .5)));

/*        kdt.insert(new Point2D(1.0, 2.0));
        kdt.insert(new Point2D(15.0, 7.0));
        kdt.insert(new Point2D(7.0, 12.0));
        kdt.insert(new Point2D(4.0, 3.0));
        kdt.insert(new Point2D(3.0, 9.0));
        kdt.insert(new Point2D(-1.0, 20.0));

        System.out.println(kdt.size());
        System.out.println(kdt.contains(new Point2D(1.0, 2.0)));
        System.out.println(kdt.contains(new Point2D(14.0, 7.0)));
        System.out.println(kdt.contains(new Point2D(7.0, 12.0)));
        System.out.println(kdt.contains(new Point2D(4.2, 3.0)));
        System.out.println(kdt.contains(new Point2D(3.0, 9.0)));
        System.out.println(kdt.contains(new Point2D(-1.0, 20.0)));

        RectHV rect = new RectHV(-5, -2, 10, 10);
        System.out.println(kdt.range(rect));*/
    }
}
