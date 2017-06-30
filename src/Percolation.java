import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int length;
    private int topVirtualNode;
    private int bottomVirtualNode;
    private int openSites;

    private WeightedQuickUnionUF uf;
    private boolean isOpenRec[];


    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        uf = new WeightedQuickUnionUF((n * n) + 2);
        isOpenRec = new boolean[(n * n) + 2];
        length = n;
        topVirtualNode = n * n;
        bottomVirtualNode = (n * n) + 1;
        openSites = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                isOpenRec[index(i + 1, j + 1)] = false;
            }
        }
    }

    public static void main(String[] args) {
    }

    private int index(int row, int col) {
        if (row <= 0 || row > length || col <= 0 || col > length)
            throw new IllegalArgumentException();

        return ((row - 1) * length) + col - 1;
    }

    public void open(int row, int col) {
        int site = index(row, col);
        if (!isOpenRec[site]) {
            isOpenRec[site] = true;
            openSites++;
        }

        try {
            int left = index(row, col - 1);
            if (isOpenRec[left] && !uf.connected(site, left)) uf.union(site, left);
        } catch (IllegalArgumentException e) {
        }

        try {
            int right = index(row, col + 1);
            if (isOpenRec[right] && !uf.connected(site, right)) uf.union(site, right);
        } catch (IllegalArgumentException e) {
        }

        try {
            int up = index(row - 1, col);
            if (isOpenRec[up] && !uf.connected(site, up)) uf.union(site, up);
        } catch (IllegalArgumentException e) {
            if (!uf.connected(site, topVirtualNode)) uf.union(site, topVirtualNode);
        }

        try {
            int down = index(row + 1, col);
            if (isOpenRec[down] && !uf.connected(site, down)) uf.union(site, down);
        } catch (IllegalArgumentException e) {
            if (!uf.connected(site, bottomVirtualNode)) uf.union(site, bottomVirtualNode);
        }
    }

    public boolean isOpen(int row, int col) {
        int site = index(row, col);
        return isOpenRec[site];
    }

    public boolean isFull(int row, int col) {
        int site = index(row, col);
        return uf.connected(topVirtualNode, site);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(topVirtualNode, bottomVirtualNode);
    }
}
