package net.trustbloc.algorithms;

import java.util.*;

public class MyGraph {

    public static class Vertex {
        int k; //key
        Object v; //value
        //k: another endpoint, v: edge
        HashMap<Integer, Edge> edgeHashMap = new HashMap<>(); //hash map

        public Vertex(int k, Object v) {
            this.k = k;
            this.v = v;
        }

        public Vertex(int k) {
            this.k = k;
        }
    }

    public static class Edge {
        int k;
        Vertex e1, e2;
        boolean directed;
        float weight;

        public Edge(int k, Vertex e1, Vertex e2, boolean directed, float weight) {
            this.k = k;
            this.e1 = e1;
            this.e2 = e2;
            this.directed = directed;
            this.weight = weight;
        }

        public Edge(int k, Vertex e1, Vertex e2) {
            this(k, e1, e2, false, 0);
        }

        public Edge(int k, Vertex e1, Vertex e2, float weight) {
            this(k, e1, e2, false, weight);
        }

        public Edge(int k, Vertex e1, Vertex e2, boolean directed) {
            this(k, e1, e2, directed, 0);
        }
    }

    // k: vertex k, value: vertex object
    HashMap<Integer, Vertex> vertexHashMap = new HashMap<>();
    // k: edge k, value: edge object
    HashMap<Integer, Edge> edgeHashMap = new HashMap<>();

    public MyGraph() {
    }

    public Iterator<Edge> edges() {
        return this.edgeHashMap.values().iterator();
    }

    public Iterator<Vertex> vertices() {
        return this.vertexHashMap.values().iterator();
    }

    public int numVertices() {
        return this.vertexHashMap.size();
    }

    public int numEdges() {
        return this.edgeHashMap.size();
    }

    public Edge getEdge(int u, int v) {
        return this.vertexHashMap.get(u).edgeHashMap.get(v);
    }

    public Vertex[] endVertices(int e) {
        Edge edge = this.edgeHashMap.get(e);
        if (edge == null) return null;

        Vertex[] vs = new Vertex[2];
        vs[0] = edge.e1;
        vs[1] = edge.e2;

        return vs;
    }

    public Vertex opposite(int v, int e) {
        Edge edge = this.edgeHashMap.get(e);
        if (edge == null) return null;

        if (edge.e1.k == v) {
            return this.vertexHashMap.get(edge.e2.k);
        }
        return this.vertexHashMap.get(edge.e1.k);
    }

    public int outDeg(int v) {
        Vertex vertex = this.vertexHashMap.get(v);
        if (vertex == null) return -1;

        int outd = 0;

        for (Edge e : vertex.edgeHashMap.values()) {
            if (v == e.e1.k) outd++;
        }
        return outd;
    }

    public int inDeg(int v) {
        Vertex vertex = this.vertexHashMap.get(v);
        if (vertex == null) return -1;

        int ind = 0;

        for (Edge e : vertex.edgeHashMap.values()) {
            if (v == e.e2.k) ind++;
        }
        return ind;
    }

    public Iterator<Edge> outgoingEdges(int v) {
        Vertex vertex = this.vertexHashMap.get(v);
        if (vertex == null) return null;

        ArrayList<Edge> edges = new ArrayList<Edge>();

        for (Edge e : vertex.edgeHashMap.values()) {
            if (v == e.e1.k) edges.add(e);
        }
        return edges.iterator();
    }

    public Iterator<Edge> incomingEdges(int v) {
        Vertex vertex = this.vertexHashMap.get(v);
        if (vertex == null) return null;

        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Edge e : vertex.edgeHashMap.values()) {
            if (v == e.e2.k) edges.add(e);
        }

        return edges.iterator();
    }

    public Vertex insertVertex(int v) {
        Vertex vertex = new Vertex(v);
        this.vertexHashMap.put(v, vertex);

        return vertex;
    }

    public Edge insertEdge(int u, int v, int e) {
        Vertex e1 = this.vertexHashMap.get(u);
        Vertex e2 = this.vertexHashMap.get(v);
        Edge edge = new Edge(e, e1, e2);
        this.edgeHashMap.put(e, edge);

        e1.edgeHashMap.put(v, edge);
        e2.edgeHashMap.put(u, edge);

        return edge;
    }

    public void removeVertex(int v) {
        Vertex vertex = this.vertexHashMap.remove(v);

        Iterator<Edge> edges = vertex.edgeHashMap.values().iterator();
        while (edges.hasNext()) {
            Edge e = edges.next();
            int u;
            if (e.e1.k != v) {
                u = e.e1.k;
            } else {
                u = e.e2.k;
            }
            this.vertexHashMap.get(u).edgeHashMap.remove(v);
            this.edgeHashMap.remove(e);
        }
    }

    public HashMap<Vertex, Edge> dfs(int u) {
        boolean cyclic = false;
        HashMap<Vertex, Edge> forest = new HashMap<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(u);
        while (!stack.empty()) {
            Vertex vertex = this.vertexHashMap.get(stack.pop());
            if (visited.get(vertex.k) == true) continue;
            visited.put(vertex.k, true);
            Set<Integer> peers = vertex.edgeHashMap.keySet();
            stack.addAll(peers);
            for (int peer : peers) {
                Edge edge = vertex.edgeHashMap.get(peer);
                forest.put(this.vertexHashMap.get(peer), edge);
                if (visited.get(peer) != null) {
                    cyclic = true;
                }
            }
        }

        return forest;
    }

    public void bfs(int u) {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(u);
        while (q.iterator().hasNext()) {
            Vertex vertex = this.vertexHashMap.get(q.iterator().next());
            if (visited.get(vertex.k) == true) continue;
            visited.put(vertex.k, true);
            Set<Integer> peers = vertex.edgeHashMap.keySet();
            q.addAll(peers);
        }
    }

    public LinkedList<Edge> path(int u, int v) {

        LinkedList<Edge> path = new LinkedList<>();

        HashMap<Vertex, Edge> forest = this.dfs(u);

        Vertex vertex = this.vertexHashMap.get(v);

        if (forest.get(vertex) == null) return null;

        while (vertex.k != u) {
            Edge edge = forest.get(vertex);
            path.addFirst(edge);
            vertex = this.opposite(vertex.k, edge.k);
        }

        return path;
    }

    public boolean connected() {

        HashMap<Integer, Boolean> visited = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        stack.push((Integer) this.vertexHashMap.keySet().toArray()[0]);
        while (!stack.empty()) {
            Vertex vertex = this.vertexHashMap.get(stack.pop());
            if (visited.get(vertex.k) == true) continue;
            visited.put(vertex.k, true);
            Set<Integer> peers = vertex.edgeHashMap.keySet();
            stack.addAll(peers);
        }

        return visited.size() == this.numVertices();
    }

    public HashMap<Integer, Edge> transitiveClosure() {
        HashMap<Integer, Edge> emap = new HashMap<>();
        emap.putAll(this.edgeHashMap);

        Set<Integer> vertices = this.vertexHashMap.keySet();
        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                if (i == k || this.getEdge(i, k) == null) continue;
                for (int j = 0; j < vertices.size(); j++) {
                    if (j == i || j == k || this.getEdge(j, k) == null) continue;
                    Edge edge = new Edge(this.edgeHashMap.size(),
                            this.vertexHashMap.get(i), this.vertexHashMap.get(j));
                    emap.put(edge.k, edge);
                }
            }
        }

        return emap;
    }

    public LinkedList<Vertex> topoSort() {
        LinkedList<Vertex> topo = new LinkedList<>();
        Stack<Vertex> ready = new Stack<>();
        // key : vertex.key, value : inDegree
        HashMap<Integer, Integer> inCount = new HashMap<>();

        while (this.vertices().hasNext()) {
            Vertex u = this.vertices().next();
            inCount.put(u.k, this.inDeg(u.k));
            if (inCount.get(u.k) == 0) {
                ready.push(u);
            }
        }

        while (!ready.isEmpty()) {
            Vertex u = ready.pop();
            topo.add(u);
            while (this.outgoingEdges(u.k).hasNext()) {
                Edge edge = this.outgoingEdges(u.k).next();
                Vertex v = this.opposite(u.k, edge.k);
                inCount.put(v.k, inCount.get(v.k) - 1);
                if (inCount.get(v.k) == 0) {
                    ready.push(v);
                }
            }
        }

        return topo;
    }

    //Dijkstra
    public HashMap<Integer, Integer> shortestPath(int s) {

        class PQEntry {
            public int d;
            public Vertex v;

            PQEntry(int d, Vertex v) {
                this.d = d;
                this.v = v;
            }
        }

        class Comparator implements java.util.Comparator<PQEntry> {
            @Override
            public int compare(PQEntry e1, PQEntry e2) {
                if (e1.d == e2.d) return 0;
                if (e1.d < e2.d) return -1;
                return 1;
            }
        }

        // key: Vertex.k, value: distance from start
        HashMap<Integer, Integer> d = new HashMap<>();
        HashMap<Integer, Integer> cloud = new HashMap<>();
        PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>(this.numVertices(), new Comparator());

        // key: Vertex.k, value: the corresponding PQEntry
        HashMap<Integer, PQEntry> eloc = new HashMap<>();

        while (this.vertices().hasNext()) {
            Vertex v = this.vertices().next();
            if (s == v.k) {
                d.put(v.k, 0);
                PQEntry e = new PQEntry(0, v);
                pq.add(e);
                eloc.put(v.k, e);
            } else {
                d.put(v.k, Integer.MAX_VALUE);
                PQEntry e = new PQEntry(Integer.MAX_VALUE, v);
                pq.add(e);
                eloc.put(v.k, e);
            }
        }

        while (!pq.isEmpty()) {
            PQEntry entry = pq.remove();
            Vertex u = entry.v;
            cloud.put(u.k, entry.d);
            while (this.outgoingEdges(u.k).hasNext()) {
                Edge edge = this.outgoingEdges(u.k).next();
                Vertex v = this.opposite(u.k, edge.k);
                if (d.get(u.k) + edge.weight < d.get(v.k)) {
                    int smallerD = d.get(u.k) + (int) edge.weight;
                    d.put(v.k, smallerD);
                    PQEntry e = eloc.get(v.k);
                    pq.remove(e);
                    e.d = smallerD;
                    pq.add(e);
                }
            }
        }

        return cloud;
    }

    public Collection<Edge> shortestPathTree(int s) {

        class PQEntry {
            public int d;
            public Vertex v;

            PQEntry(int d, Vertex v) {
                this.d = d;
                this.v = v;
            }
        }

        class Comparator implements java.util.Comparator<PQEntry> {
            @Override
            public int compare(PQEntry e1, PQEntry e2) {
                if (e1.d == e2.d) return 0;
                if (e1.d < e2.d) return -1;
                return 1;
            }
        }

        // key: Vertex.k, value: distance from start
        HashMap<Integer, Integer> d = new HashMap<>();
        PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>(this.numVertices(), new Comparator());

        // key: Vertex.k, value: the corresponding PQEntry
        HashMap<Integer, PQEntry> eloc = new HashMap<>();

        while (this.vertices().hasNext()) {
            Vertex v = this.vertices().next();
            if (s == v.k) {
                d.put(v.k, 0);
                PQEntry e = new PQEntry(0, v);
                pq.add(e);
                eloc.put(v.k, e);
            } else {
                d.put(v.k, Integer.MAX_VALUE);
                PQEntry e = new PQEntry(Integer.MAX_VALUE, v);
                pq.add(e);
                eloc.put(v.k, e);
            }
        }

        HashMap<Integer, Edge> graph = new HashMap<>();

        while (!pq.isEmpty()) {
            PQEntry entry = pq.remove();
            Vertex u = entry.v;
            while (this.outgoingEdges(u.k).hasNext()) {
                Edge edge = this.outgoingEdges(u.k).next();
                Vertex v = this.opposite(u.k, edge.k);
                if (d.get(u.k) + edge.weight < d.get(v.k)) {
                    int smallerD = d.get(u.k) + (int) edge.weight;
                    d.put(v.k, smallerD);
                    PQEntry e = eloc.get(v.k);
                    pq.remove(e);
                    e.d = smallerD;
                    pq.add(e);

                    graph.put(v.k, edge);
                }
            }
        }

        return graph.values();
    }

    public HashMap<Vertex, Edge> shortestPathTree(int s, Map<Integer, Integer> d) {
        HashMap<Vertex, Edge> tree = new HashMap<>();
        for (int v : d.keySet()) {
            if (s == v) continue;
            while (this.incomingEdges(v).hasNext()) {
                Edge edge = this.incomingEdges(v).next();
                Vertex u = this.opposite(v, edge.k);
                if (d.get(v) == d.get(u) + edge.weight) {
                    tree.put(this.vertexHashMap.get(v), edge);
                    break;
                }
            }
        }

        return tree;
    }

    public Collection<Edge> primMST() {

        class PQEntry {
            public int d;
            public Vertex v;

            PQEntry(int d, Vertex v) {
                this.d = d;
                this.v = v;
            }
        }

        class Comparator implements java.util.Comparator<PQEntry> {
            @Override
            public int compare(PQEntry e1, PQEntry e2) {
                if (e1.d == e2.d) return 0;
                if (e1.d < e2.d) return -1;
                return 1;
            }
        }

        int s = (int) this.vertexHashMap.keySet().toArray()[0];

        // key: Vertex.k, value: distance from start
        HashMap<Integer, Integer> d = new HashMap<>();
        PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>(this.numVertices(), new Comparator());

        // key: Vertex.k, value: the corresponding PQEntry
        HashMap<Integer, PQEntry> eloc = new HashMap<>();

        while (this.vertices().hasNext()) {
            Vertex v = this.vertices().next();
            if (s == v.k) {
                d.put(v.k, 0);
                PQEntry e = new PQEntry(0, v);
                pq.add(e);
                eloc.put(v.k, e);
            } else {
                d.put(v.k, Integer.MAX_VALUE);
                PQEntry e = new PQEntry(Integer.MAX_VALUE, v);
                pq.add(e);
                eloc.put(v.k, e);
            }
        }

        HashMap<Integer, Edge> graph = new HashMap<>();

        while (!pq.isEmpty()) {
            PQEntry entry = pq.remove();
            Vertex u = entry.v;
            while (this.outgoingEdges(u.k).hasNext()) {
                Edge edge = this.outgoingEdges(u.k).next();
                Vertex v = this.opposite(u.k, edge.k);
                if (edge.weight < d.get(v.k)) {
                    d.put(v.k, (int) edge.weight);
                    PQEntry e = eloc.get(v.k);
                    pq.remove(e);
                    e.d = (int) edge.weight;
                    pq.add(e);

                    graph.put(v.k, edge);
                }
            }
        }

        return graph.values();
    }

    public Collection<Edge> kruskalMST() {

        class PQEntry {
            public int w;
            public Edge e;

            PQEntry(int w, Edge e) {
                this.w = w;
                this.e = e;
            }
        }

        class Comparator implements java.util.Comparator<PQEntry> {
            @Override
            public int compare(PQEntry e1, PQEntry e2) {
                if (e1.w == e2.w) return 0;
                if (e1.w < e2.w) return -1;
                return 1;
            }
        }


        PriorityQueue<PQEntry> pq = new PriorityQueue<>(this.numEdges(), new Comparator());
        //key: vertex.k
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Partition<Vertex> partition= new Partition<>();
        //key: edge.k
        HashMap<Integer, Edge> tree = new HashMap<>();
        //key: Edge.k
        HashMap<Integer, PQEntry> eloc = new HashMap<>();
        //key: vertex.k
        HashMap<Integer, Partition.Node<Vertex>> positions = new HashMap<>();

        while(this.edges().hasNext()) {
            Edge edge = this.edges().next();
            PQEntry entry = new PQEntry((int) edge.weight, edge);
            pq.add(entry);
        }

        while(this.vertices().hasNext()) {
            Vertex vertex = this.vertices().next();
            positions.put(vertex.k, partition.makeCluster(vertex));
        }

        while(visited.size() < this.vertexHashMap.size() && !pq.isEmpty()) {
            PQEntry entry = pq.remove();
            Edge edge = entry.e;
            Vertex []ep = this.endVertices(edge.k);

            if(positions.get(ep[0].k).hashCode() != positions.get(ep[1].k).hashCode()) {
                tree.put(edge.k, edge);
                partition.union(positions.get(ep[0].k), positions.get(ep[1].k));
            }
        }

        return tree.values();
    }
}
