import java.util.*;

class Edge {
    int v, flow, C, rev;

    public Edge(int v, int flow, int C, int rev) {
        this.v = v;
        this.flow = flow;
        this.C = C;
        this.rev = rev;
    }
}

class Graph {
    private int V; // No. of vertices
    private List<Edge>[] adj; // Adjacency list
    private int[] level; // Stores level of graph

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<Edge>();
        }
        level = new int[V];
    }

    public void addEdge(int u, int v, int capacity) {
        Edge a = new Edge(v, 0, capacity, adj[v].size());
        Edge b = new Edge(u, 0, 0, adj[u].size());
        adj[u].add(a);
        adj[v].add(b);
    }

    private boolean BFS(int s, int t) {
        Arrays.fill(level, -1);
        level[s] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Edge e : adj[u]) {
                if (level[e.v] < 0 && e.flow < e.C) {
                    level[e.v] = level[u] + 1;
                    queue.add(e.v);
                }
            }
        }

        return level[t] >= 0;
    }

    private int sendFlow(int u, int flow, int t, int[] start) {
        if (u == t) {
            return flow;
        }

        for (; start[u] < adj[u].size(); start[u]++) {
            Edge e = adj[u].get(start[u]);

            if (level[e.v] == level[u] + 1 && e.flow < e.C) {
                int curr_flow = Math.min(flow, e.C - e.flow);
                int temp_flow = sendFlow(e.v, curr_flow, t, start);

                if (temp_flow > 0) {
                    e.flow += temp_flow;
                    adj[e.v].get(e.rev).flow -= temp_flow;
                    return temp_flow;
                }
            }
        }

        return 0;
    }

    public int DinicMaxflow(int s, int t) {
        if (s == t) {
            return -1;
        }

        int total = 0;

        while (BFS(s, t)) {
            int[] start = new int[V + 1];
            while (true) {
                int flow = sendFlow(s, Integer.MAX_VALUE, t, start);
                if (flow == 0) {
                    break;
                }
                total += flow;
            }
        }

        return total;
    }
}