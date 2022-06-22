package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        //  Your code here!
        int s = 0;
        distTo[s] = 0;
        edgeTo[s] = s;
        findCycle(0);
    }

    // Helper methods go here
    private int t = maze.N() * maze.N() - 1;
    private int[] linkTo = new int[maze.N() * maze.N()];
    private boolean cycle = false;
    private void findCycle(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (cycle) {
                return;
            }
            if (!marked[w]) {
                linkTo[w] = v;
                distTo[w] = distTo[v] + 1;
                announce();
                findCycle(w);
            } else if (w != linkTo[v]) {
                linkTo[w] = v;
                drawCycle(w);
                announce();
                cycle = true;
                return;
            }
        }
    }
    private void drawCycle(int w) {
        int i = w;
        do {
            edgeTo[i] = linkTo[i];
            i = linkTo[i];
        } while (i != w);
    }
}

