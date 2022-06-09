package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] grids;
    private WeightedQuickUnionUF openings;
    private int N;
    private int[] topRowOpen;
    private int topRowCount;
    private int[] bottomRowOpen;
    private int bottomRowCount;
    private int numOpen;
    private boolean isPercolated;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grids = new int[N * N]; //0 means blocked, 1 means open
        openings = new WeightedQuickUnionUF(N * N);
        numOpen = 0;
        topRowOpen = new int[N];
        topRowCount = 0;
        bottomRowOpen = new int[N];
        bottomRowCount = 0;
        isPercolated = false;
    }               // create N-by-N grid, with all sites initially blocked

    private int idx(int row, int col) {
        return row * N + col;
    }
    private void connectNeighbors(int row, int col) {
        if (row + 1 < N && this.isOpen(row + 1, col)) {
            openings.union(idx(row, col), idx(row + 1, col));
        }
        if (col + 1 < N && this.isOpen(row, col + 1)) {
            openings.union(idx(row, col), idx(row, col + 1));
        }
        if (row - 1 >= 0 && this.isOpen(row - 1, col)) {
            openings.union(idx(row, col), idx(row - 1, col));
        }
        if (col - 1 >= 0 && this.isOpen(row, col - 1)) {
            openings.union(idx(row, col), idx(row, col - 1));
        }
    }
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        connectNeighbors(row, col);
        grids[idx(row, col)] = 1;
        if (row == 0) {
            topRowOpen[topRowCount] = idx(row, col);
            topRowCount++;
        }
        if (row == N - 1) {
            bottomRowOpen[bottomRowCount] = idx(row, col);
            bottomRowCount++;
        }
        if ((!isPercolated) && isFull(row, col)) {
            for (int i = 0; i < bottomRowCount; i++) {
                if (openings.connected(idx(row, col), bottomRowOpen[i])) {
                    isPercolated = true;
                }
            }
        }
        numOpen++;
    }      // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return grids[idx(row, col)] > 0;
    } // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            return false;
        }
        if (row == 0) {
            return true;
        }
        for (int i = 0; i < topRowCount; i++) {
            if (openings.connected(idx(row, col), topRowOpen[i])) {
                return true;
            }
        }
        return false;
    }  // is the site (row, col) full?
    public int numberOfOpenSites() {
        return numOpen;
    }           // number of open sites
    public boolean percolates() {
        return isPercolated;
    }             // does the system percolate?
    public static void main(String[] args) {
    }  // use for unit testing (not required, but keep this here for the autograder)

}
