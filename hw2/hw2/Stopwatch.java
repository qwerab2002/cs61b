package hw2;

public class Stopwatch {

    private final long start;

    /**
     * Initializes a new stopwatch.
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
    }


    /**
     * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
     *
     * @return elapsed CPU time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;

    }
        public static void main(String[] args) {
            Stopwatch timer1 = new Stopwatch();
            PercolationFactory pf = new PercolationFactory();
            PercolationStats ps = new PercolationStats(10, 20, pf);
            System.out.println(ps.mean());
            System.out.println(ps.stddev());
            System.out.println(ps.confidenceHigh());
            System.out.println(ps.confidenceLow());
            double time1 = timer1.elapsedTime();
            System.out.println("" + time1 + "seconds");
        }
    }
