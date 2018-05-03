import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int t;
    private double[] threshold;
    private double mean;
    private double stddev;
    public PercolationStats(int n, int t) {                   
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("should be greater than 0");
        }
        
        this.n = n;
        this.t = t;
        this.threshold = new double[t];

        for (int trial = 0; trial < t; trial++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            this.threshold[trial] = ((double )percolation.numberOfOpenSites()) / (n * n);
        }
        
        this.mean = StdStats.mean(this.threshold);
        this.stddev = StdStats.stddev(this.threshold);
    }
    
    public double mean() {
        return this.mean;
    }
    
    public double stddev() {
        return this.stddev;
    }
    
    public double confidenceLo() {
        return this.mean() - (1.96 * (this.stddev() / Math.sqrt(this.t)));
    }
    
    public double confidenceHi() {
        return this.mean() + (1.96 * (this.stddev() / Math.sqrt(this.t)));
    }
    
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("usage: n t");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);               
        
        System.out.printf("mean = %f\n", stats.mean());
        System.out.printf("stddev = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}