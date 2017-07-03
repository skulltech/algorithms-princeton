import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int length;
    private int topnode;
    private int bottomnode;
    private int openSites = 0;

    private WeightedQuickUnionUF uf;
    private boolean isOpenSite[];


    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        uf = new WeightedQuickUnionUF((n * n) + 2);
        isOpenSite = new boolean[(n * n) + 2];
        length = n;
        topnode = n * n;
        bottomnode = (n * n) + 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) isOpenSite[index(i + 1, j + 1)] = false;
        }
    }

    private int index(int row, int col) {
        if (row <= 0 || row > length || col <= 0 || col > length)
            throw new IllegalArgumentException();

        return ((row - 1) * length) + col - 1;
    }

    public void open(int row, int col) {
        int site = index(row, col);
        if (!isOpenSite[site]) {
            isOpenSite[site] = true;
            openSites++;
        }

        try {
            int left = index(row, col - 1);
            if (isOpenSite[left] && !uf.connected(site, left)) uf.union(site, left);
        } catch (IllegalArgumentException e) {
        }

        try {
            int right = index(row, col + 1);
            if (isOpenSite[right] && !uf.connected(site, right)) uf.union(site, right);
        } catch (IllegalArgumentException e) {
        }

        try {
            int up = index(row - 1, col);
            if (isOpenSite[up] && !uf.connected(site, up)) uf.union(site, up);
        } catch (IllegalArgumentException e) {
            if (!uf.connected(site, topnode)) uf.union(site, topnode);
        }

        try {
            int down = index(row + 1, col);
            if (isOpenSite[down] && !uf.connected(site, down)) uf.union(site, down);
        } catch (IllegalArgumentException e) {
            if (!uf.connected(site, bottomnode)) uf.union(site, bottomnode);
        }
    }

    public boolean isOpen(int row, int col) {
        int site = index(row, col);
        return isOpenSite[site];
    }

    public boolean isFull(int row, int col) {
        int site = index(row, col);
        return uf.connected(topnode, site);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(topnode, bottomnode);
    }
}
