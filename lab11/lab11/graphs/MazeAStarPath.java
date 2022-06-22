package lab11.graphs;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int tX = maze.toX(t);
        int tY = maze.toY(t);
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        return Math.abs(vX - tX) + Math.abs(vY - tY);
    }
    private class NodeComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            if (n1.dis == n2.dis) {
                return distTo[n1.id] - distTo[n2.id];
            }
            return n1.dis - n2.dis;
        }
    }
    private PriorityQueue<Node> p = new PriorityQueue<>(new NodeComparator());
    private class Node {
        int id;
        int dis;
        Node(int id, int dis) {
            this.id = id;
            this.dis = dis;
        }
    }
    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        if (!p.isEmpty()) {
            Node n = p.poll();
            return n.id;
        }
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        while (!targetFound) {
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    p.add(new Node(w, distTo[w] + h(w)));
                }
            }
            v = findMinimumUnmarked();
            marked[v] = true;
            drawNode(v);
            announce();
            if (v == t) {
                targetFound = true;
            }
        }

    }
    private void drawNode(int v) {
        int minDist = maze.N() * maze.N();
        int min = 0;
        for (int w : maze.adj(v)) {
            if (marked[w] && distTo[w] < minDist) {
                min = w;
                minDist = distTo[w];
            }
        }
        edgeTo[v] = min;
        distTo[v] = minDist + 1;
    }
    @Override
    public void solve() {
        astar(s);
    }

}

