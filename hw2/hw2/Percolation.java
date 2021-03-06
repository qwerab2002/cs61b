package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grids;
    private WeightedQuickUnionUF openings;
    private WeightedQuickUnionUF perc;
    private int N;
    private int head;
    private int tail;
    private int numOpen;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grids = new boolean[N * N]; //0 means blocked, 1 means open
        openings = new WeightedQuickUnionUF(N * N + 1); //with one extra node to detect fullness
        perc = new WeightedQuickUnionUF(N * N + 2); //with two extra nodes to detect percolation
        head = N * N; //all row 0 nodes connect to
        tail = N * N + 1; //all row N-1 connect to
        //creates an extra top row and an extra bottom row
        numOpen = 0;
    }               // create N-by-N grid, with all sites initially blocked

    private int idx(int row, int col) {
        return row * N + col;
    }
    private void connectNeighbors(int row, int col) {
        if (row + 1 < N && grids[idx(row + 1, col)]) {
            openings.union(idx(row, col), idx(row + 1, col));
            perc.union(idx(row, col), idx(row + 1, col));
        }
        if (row - 1 >= 0 && grids[idx(row - 1, col)]) {
            openings.union(idx(row, col), idx(row - 1, col));
            perc.union(idx(row, col), idx(row - 1, col));
        }
        if (col + 1 < N && grids[idx(row, col + 1)]) {
            openings.union(idx(row, col), idx(row, col + 1));
            perc.union(idx(row, col), idx(row, col + 1));
        }
        if (col - 1 >= 0 && grids[idx(row, col - 1)]) {
            openings.union(idx(row, col), idx(row, col - 1));
            perc.union(idx(row, col), idx(row, col - 1));
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
        if (row == 0) {
            openings.union(idx(row, col), head);
            perc.union(idx(row, col), head);
        }
        if (row == N - 1) {
            perc.union(idx(row, col), tail);
        }
        grids[idx(row, col)] = true;
        numOpen++;
    }      // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return grids[idx(row, col)];
    } // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return openings.connected(head, idx(row, col));
    }  // is the site (row, col) full?
    public int numberOfOpenSites() {
        return numOpen;
    }           // number of open sites
    public boolean percolates() {
        return perc.connected(head, tail);
    }             // does the system percolate?
    public static void main(String[] args) {
    }  // use for unit testing (not required, but keep this here for the autograder)
}
