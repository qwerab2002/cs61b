package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private int N;
    private double[] rates;
    private static double onePercolation(int N, PercolationFactory pf) {
        int count = 0;
        Percolation perc = pf.make(N);
        while (!perc.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            if (perc.isOpen(row, col)) {
                continue;
            }
            perc.open(row, col);
            count++;
        }
        return (double) count / (N * N);
    }
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        rates = new double[T];
        for (int i = 0; i < T; i++) {
            rates[i] = onePercolation(N, pf);
        }
    }   // perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(rates);
    }                                           // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(rates);
    }                                   // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(rates.length);
    }                              // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(rates.length);
    }                                 // high endpoint of 95% confidence interval
}
