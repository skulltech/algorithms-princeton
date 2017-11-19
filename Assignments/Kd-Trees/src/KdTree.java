import edu.princeton.cs.algs4.Point2D;


public class KdTree {
    private Node root;
    private int N;

    private class Node {
        boolean x;
        Node left, right;
        Point2D point;

        private Node(Point2D p, boolean x) {
            point = p;
            x = x;
        }

        private int compare(Point2D p) {
            if      ((x && p.x() < point.x()) || (!x && p.y() < point.y())) { return -1; }
            else if ((x && p.x() > point.x()) || (!x && p.y() > point.y())) { return  1; }
            else                                                            { return  0; }
        }
    }

    public int size()        { return N;      }
    public boolean isEmpty() { return (N==0); }

    private Node insert(Node node, Point2D p, boolean x) {
        if (node==null) {
            this.N++;
            return new Node(p, x);
        }

        assert (node.x == x);
        int cmp = node.compare(p);
        if      (cmp < 0) { node.left  = insert(node.left, p, !x);  }
        else if (cmp > 0) { node.right = insert(node.right, p, !x); }

        return node;
    }

    public void insert(Point2D p) {
        root = insert(root, p, true);
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
        this.draw(node.left);
        node.point.draw();
        this.draw(node.right);
    }

    public void draw() { this.draw(this.root); }

    public static void main(String[] args) {
        KdTree kdt = new KdTree();

        kdt.insert(new Point2D(1.0, 2.0));
        kdt.insert(new Point2D(15.0, 7.0));
        kdt.insert(new Point2D(7.0, 12.0));
        kdt.insert(new Point2D(4.6, 3.0));
        kdt.insert(new Point2D(3.5, 9.0));
        kdt.insert(new Point2D(-1.0, 20.0));

        System.out.println(kdt.size());
        System.out.println(kdt.contains(new Point2D(1.0, 2.0)));
        System.out.println(kdt.contains(new Point2D(15.0, 7.0)));
        System.out.println(kdt.contains(new Point2D(7.0, 12.0)));
        System.out.println(kdt.contains(new Point2D(4.6, 3.0)));
        System.out.println(kdt.contains(new Point2D(3.5, 9.0)));
        System.out.println(kdt.contains(new Point2D(-1.0, 20.0)));
    }
}
