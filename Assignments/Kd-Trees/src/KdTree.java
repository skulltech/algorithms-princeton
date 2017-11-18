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
        if (node==null) { return new Node(p, x); }

        assert (node.x == x);
        int cmp = node.compare(p);
        if      (cmp < 0) { node.left = insert(node.left, p, !x);  }
        else if (cmp > 0) { node.right = insert(node.right, p, !x); }

        return node;
    }

    public void insert(Point2D p) {
        root = insert(root, p, true);
    }


}
