import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private WeightedQuickUnionUF uf;
    private boolean[] open;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("should be greater than 0");
        }
        gridSize = n;
        this.open = new boolean[n*n + 2];
        this.uf = new WeightedQuickUnionUF(n*n + 2);
        for (int i = 0; i < n * n + 2; i++) {
           open[i] = false;
        }
        
        
        // top
        for (int i = 1; i <= n; i++) {  
            this.uf.union(0, this.index(1, i));
        }                                                                      
        // bottom
        for (int i = 1; i <= n; i++) {      
            this.uf.union(n*n + 2 - 1, this.index(n, i));
        }
    }
    
    public void open(int row, int col) {
        if (row <= 0 || row > this.gridSize || col <= 0 || col > this.gridSize) {
            throw new IllegalArgumentException("out of index");
        }
        
        this.open[this.index(row, col)] = true;
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {                                
                if (row + i <= 0 || row + i > this.gridSize) continue;
                if (col + j <= 0 || col + j > this.gridSize) continue;
                //  only top down left right
                if (i == -1 && (j == -1 || j == 1)) continue;
                if (i == 0 && j == 0) continue;
                if (i == 1 && (j == -1 || j == 1)) continue;
                
                if (isOpen(row + i, col + j)) {
                    this.uf.union(this.index(row + i, col + j), this.index(row, col));
                }
            }
                                           
        }
    }
                                           
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > this.gridSize || col <= 0 || col > this.gridSize) {
            throw new IllegalArgumentException("out of index");
        }
        
        return open[this.index(row, col)];
    }
    
    public boolean isFull(int row, int col) {
        return this.isOpen(row, col) && this.uf.connected(0, this.index(row, col));
    }
    
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean o : this.open) {
            if (o) count++;
        }
        
        return count;
    }
    
    public boolean percolates()  {
        if (this.gridSize == 1) {
            return this.isFull(1, 1);
        } else {
            return this.uf.connected(0, this.open.length - 1);
        }
    }
    
    private int index(int row, int col) {
        return (row - 1) * this.gridSize + col;
    }
    
    public static void main(String[] args) {
        System.out.println("hello");
        Percolation percolation;
        percolation = new Percolation(6);
        percolation.open(1,6);
        percolation.open(2,6);
        percolation.open(3,6);
        percolation.open(4,6);
        percolation.open(5,6);
        percolation.open(5,5);
        percolation.open(4,4);
        percolation.open(3,4);
        percolation.open(6,6);
        System.out.println("result");
        System.out.println(percolation.isFull(3, 4));
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
    }
}