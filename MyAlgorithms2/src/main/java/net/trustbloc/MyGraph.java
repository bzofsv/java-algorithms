package net.trustbloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class MyGraph<V, E> {

    public class Vertex<V> {

        int k;
        V v;
        // peer -> edge
        HashMap<Vertex, Edge> edgeMap;

        public Vertex(int k, V v) {
            this.k = k;
            this.v = v;
            this.edgeMap = new HashMap<>();
        }
    }

    public class Edge<E> {

        int k;
        E e;
        Vertex<V>[] endVertices;
        boolean directed;
        int weight;

        public Edge(int k, E e, Vertex<V> u, Vertex<V> v, int weight, boolean directed) {
            this.k = k;
            this.e = e;
            this.endVertices = new Vertex[2];
            this.endVertices[0] = u; // 33-34: from u to v
            this.endVertices[1] = v;
            this.weight = weight;
            this.directed = directed;
        }

        public Edge(int k, E e, Vertex<V> u, Vertex<V> v, int weight) {
            this(k, e, u, v, weight, false);
        }

        public Edge(int k, E e, Vertex<V> u, Vertex<V> v) {
            this(k, e, u, v, 0);
        }
    }

    HashMap<Integer, Vertex<V>> vertexHashMap;
    HashMap<Integer, Edge<E>> edgeHashMap;

    public MyGraph() {
        this.vertexHashMap = new HashMap<Integer, Vertex<V>>();
        this.edgeHashMap = new HashMap<Integer, Edge<E>>();
    }

    public int numVertices() {
        return this.vertexHashMap.size();
    }

    public Collection<Vertex<V>> vertices() {
        return this.vertexHashMap.values();
    }

    public int numEdges() {
        return this.edgeHashMap.size();
    }

    public Collection<Edge<E>> edges() {
        return this.edgeHashMap.values();
    }

    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        return u.edgeMap.get(v);
    }

    public Vertex<V>[] endVertices(Edge<E> e) {
        return e.endVertices;
    }

    public Vertex<V> opposite(Vertex<V> u, Edge<E> e) {
        return e.endVertices[0] == u ? e.endVertices[1] : e.endVertices[0];
    }

    public int outDegree(Vertex<V> u) {

        int outDegree = 0;

        for(Edge<E> e : u.edgeMap.values()) {
            if(!e.directed || e.endVertices[0] == u) outDegree++;
        }

        return outDegree;
    }

    public int inDegree(Vertex<V> u) {

        int inDegree = 0;

        for(Edge<E> e : u.edgeMap.values()) {
            if(!e.directed || e.endVertices[1] == u) inDegree++;
        }

        return inDegree;
    }

    public Collection<Edge<E>> outgoingEdges(Vertex<V> u) {

        Collection<Edge<E>> outgoingEdges = new ArrayList<>();

        for(Edge<E> e : u.edgeMap.values()) {
            if(!e.directed || e.endVertices[0] == u) outgoingEdges.add(e);
        }

        return outgoingEdges;
    }

    public Collection<Edge<E>> incomingEdges(Vertex<V> u) {

        Collection<Edge<E>> incomingEdges = new ArrayList<>();

        for(Edge<E> e : u.edgeMap.values()) {
            if(!e.directed || e.endVertices[1] == u) incomingEdges.add(e);
        }

        return incomingEdges;
    }

    public Vertex<V> insertVertex(int k, V v) {
        Vertex<V> vertex = new Vertex<>(k, v);
        this.vertexHashMap.put(k, vertex);
        return vertex;
    }

    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, int k, E e) {

        Edge<E> edge = new Edge<>(k, e, u, v);

        u.edgeMap.put(v, edge);
        v.edgeMap.put(u, edge);
        this.edgeHashMap.put(k, edge);

        return edge;
    }

    public void removeVertex(int k) {

        Vertex<V> vertex = this.vertexHashMap.remove(k);

        if(vertex == null) return;

        for(Edge<E> e : vertex.edgeMap.values()) {

            Vertex<V> peer = this.opposite(vertex, e);

            vertex.edgeMap.remove(peer);
            peer.edgeMap.remove(vertex);

            this.edgeHashMap.remove(e.k);
        }
    }

    public void removeEdge(int k) {

        Edge<E> edge = this.edgeHashMap.remove(k);

        Vertex<V> u = edge.endVertices[0];
        Vertex<V> v = edge.endVertices[1];

        u.edgeMap.remove(v);
        v.edgeMap.remove(u);
    }

    public HashMap<Vertex, Edge> dfs(Vertex<V> s) {

        if(s == null) return null;
        
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Stack<Vertex> stack = new Stack<>();
        HashMap<Vertex, Edge> ret = new HashMap<>();
        
        stack.push(s);
        
        while(!stack.isEmpty()) {
            Vertex<V> u = stack.pop();
            for(Edge<E> e : outgoingEdges(u)) {
                
                Vertex<V> peer = opposite(u, e);
                
                if(visited.get(peer)) continue;
                stack.push(peer);
                
                ret.put(peer, e);
            }
        }
        
        return ret;
    }
    
    public boolean reachable(Vertex<V> s, Vertex<V> u) {
        return dfs(s).containsKey(u);
    }
    
    public LinkedList<Vertex> recontructPath(Vertex<V> u, Vertex<V> v) {
        HashMap<Vertex, Edge> dfs = this.dfs(u);
        
        if(u == null) return null;
        if(!dfs.containsKey(v)) return null;
        
        LinkedList<Vertex> path = new LinkedList<>(); 
        Vertex<V> curr = v;
        
        while(curr != u) {
            Edge<E> e = dfs.get(curr);
            
            path.add(curr);
            
            curr = opposite(curr, e);
            
        }
        
        return path;
    }
    
    public boolean connected(Vertex<V> s) {
        return dfs(s).size() == this.vertexHashMap.size();
    }
    
    public HashMap<Vertex, Edge> dfsComplete(Vertex<V> s) {
        
        HashMap<Vertex, Edge> dfs = dfs(s);
        LinkedList<Vertex> dangling = new LinkedList<>();
        
        for(Vertex<V> u : this.vertexHashMap.values()) {
            
            if(!dfs.containsKey(u)) {
                
                HashMap<Vertex, Edge> add = dfs(u);
                
                for(Vertex<V> v : add.keySet()) {
                    dfs.put(v, add.get(v));
                }
            }
        }
        
        return dfs;
    }
    
    public HashMap<Vertex, Edge> bfs(Vertex<V> s) {

        if(s == null) return null;
        
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Queue<Vertex> q = new LinkedList<>();
        HashMap<Vertex, Edge> ret = new HashMap<>();
        
        q.add(s);
        
        while(!q.isEmpty()) {
            Vertex<V> u = q.remove();
            for(Edge<E> e : outgoingEdges(u)) {
                
                Vertex<V> peer = opposite(u, e);
                
                if(visited.get(peer)) continue;
                q.add(peer);
                
                ret.put(peer, e);
            }
        }
        
        return ret;
    }
    
    public void floydWarshall() {
        
        for(Vertex<V> i : this.vertexHashMap.values()) {
            for(Vertex<V> j : this.vertexHashMap.values()) {
                if(i == j) continue;
                for(Vertex<V> k : this.vertexHashMap.values()) {
                    if(i == k || j == k) continue;
                    if(getEdge(j, k) == null) continue;
                    if(getEdge(k, i) == null) continue;
                    Edge<E> e = new Edge(this.edgeHashMap.size() + 1, null, i, j);
                    i.edgeMap.put(j, e);
                    j.edgeMap.put(i, e);
                }
            }
        }
    }
    
    public LinkedList<Vertex> topo() throws Exception {
        
        class PQEntry {

            Vertex u;
            int inCount;

            PQEntry(Vertex u) {
                this.u = u;
                this.inCount = inDegree(u);
            }
        }
        
        class InCountComparator implements Comparator<PQEntry> {

            @Override
            public int compare(PQEntry u, PQEntry v) {
                if(u.inCount < v.inCount) return -1;
                else if(u.inCount == v.inCount) return 0;
                else return 1;
            }
        }

        PriorityQueue<PQEntry> pq = new PriorityQueue<>(new InCountComparator());
        HashMap<Vertex, PQEntry> PQEFinder = new HashMap<>();
        
        for(Vertex<V> u : this.vertexHashMap.values()) {
            PQEntry pqe = new PQEntry(u);
            pq.add(pqe);
            PQEFinder.put(u, pqe);
        }
        
        HashMap<Vertex<V>, Boolean> visited = new HashMap<>();
        
        LinkedList<Vertex> topo = new LinkedList<>();
        
        while(!pq.isEmpty()) {
            
            PQEntry pqe = pq.remove();
            
            if(visited.get(pqe.u)) throw new Exception("cyclic graph");
            
            topo.add(pqe.u);
            
            Iterator<Edge<E>> it = this.outgoingEdges(pqe.u).iterator();
            while(it.hasNext()) {
                Vertex<V> peer = this.opposite(pqe.u, it.next());
                PQEntry PQpeer = PQEFinder.get(peer); 
                pq.remove(PQpeer);
                PQpeer.inCount--;
                pq.add(PQpeer);
            }
        }
        
        return topo;
    }
    
    public HashMap<Vertex, Integer> shortestPath(Vertex<V> s) {
        
        class PQEntry {           
            Vertex u;
            int d; // distance
            PQEntry(Vertex u, int d) {
                this.u = u;
                this.d = d;
            }
        }
        
        class DistanceComparator implements Comparator<PQEntry> {
            @Override
            public int compare(PQEntry pq1, PQEntry pq2) {
                if(pq1.d < pq2.d) return -1;
                else if(pq1.d == pq2.d) return 0;
                else return 1;
            }
        }
        
        PriorityQueue<PQEntry> pq = new PriorityQueue<>(new DistanceComparator());
        HashMap<Vertex, Integer> shortestPathMap = new HashMap<>();
        HashMap<Vertex, PQEntry> PQEFinder = new HashMap<>();
        
        for(Vertex<V> u : this.vertexHashMap.values()) {
            PQEntry pqes = new PQEntry(s, 0);
            PQEntry pqe = new PQEntry(u, Integer.MAX_VALUE);
            if(u == s) {
                pq.add(pqes);
                PQEFinder.put(s, pqes);
            }
            else {
                pq.add(pqe);
                PQEFinder.put(u, pqe);
            }
        }
        
        while(!pq.isEmpty()) {
            
            PQEntry pqe = pq.remove();
            shortestPathMap.put(pqe.u, pqe.d);
            
            Iterator<Edge<E>> it = this.outgoingEdges(pqe.u).iterator();
            while(it.hasNext()) {
                
                Edge<E> e = it.next();
                
                PQEntry peer = PQEFinder.get(opposite(pqe.u, e));
                if((pqe.d + e.weight <= peer.d)) {
                    pq.remove(peer);
                    peer.d = pqe.d + e.weight;
                    pq.add(peer);
                }
            }
        }
        
        return shortestPathMap;
    }
    
    public HashMap<Vertex<V>, Edge<E>> shortestPathTree(Vertex<V> s) {
        
        HashMap<Vertex, Integer> shortestPath = this.shortestPath(s);
        HashMap<Vertex<V>, Edge<E>> shortestPathTree = new HashMap<>(); 
        
        Iterator<Vertex<V>> it = this.vertexHashMap.values().iterator();
        while(it.hasNext()) {
            
            Vertex<V> curr = it.next();
            if(curr == s) continue;
            
            Iterator<Edge<E>> eit = this.outgoingEdges(it.next()).iterator();
            
            while(eit.hasNext()) {
                
                Edge<E> peeredge = eit.next();
                Vertex<V> peer = this.opposite(curr, peeredge);
                
                if(shortestPath.get(peer) == peeredge.weight + shortestPath.get(curr)) {
                    shortestPathTree.put(peer, peeredge);
                }
            }
        }
        
        return shortestPathTree;
    }
    
    public HashMap<Vertex<V>, Edge<E>> prim(Vertex<V> s) {
        
        class PQEntry {   
            Vertex<V> u;
            int w;
            PQEntry(Vertex<V> u, int w) {
                this.u = u;
                this.w = w;
            }
        }
        
        class DistanceComparator implements Comparator<PQEntry> {
            @Override
            public int compare(PQEntry pq1, PQEntry pq2) {
                if(pq1.w < pq2.w) return -1;
                else if(pq1.w == pq2.w) return 0;
                else return 1;
            }
        }
        
        HashMap<Vertex<V>, PQEntry> PQEFinder = new HashMap<>();
        HashMap<Vertex<V>, Edge<E>> mst = new HashMap<>();
        PriorityQueue<PQEntry> pq = new PriorityQueue<>(new DistanceComparator());
        
        for(Vertex<V> u : this.vertexHashMap.values()) {
            PQEntry pqe = new PQEntry(u, Integer.MAX_VALUE);
            if(u == s) {
                pqe.w = 0;
            }
            pq.add(pqe);
            PQEFinder.put(u, pqe);
        }
        
        while(!pq.isEmpty()) {
            
            PQEntry pqe = pq.remove();
            
            for(Edge<E> e : this.outgoingEdges(pqe.u)) {
                
                PQEntry peer = PQEFinder.get(opposite(pqe.u, e));
                if(e.weight <= peer.w) {
                    pq.remove(peer);
                    peer.w = e.weight;
                    pq.add(peer);
                }
                
                mst.put(peer.u, e);
            }
        }
        
        return mst;
    }
    
    public LinkedList<Edge<E>> kruskal(Vertex<V> s) {
        
        class Cluster {
            Vertex<V> leader;
            LinkedList<Vertex<V>> entries;
            
            Cluster(Vertex<V> leader) {
                this.leader = leader;
                this.entries.add(leader);
            }
            Vertex<V> getLeader() {
                return leader;
            }
            
            void merge(Cluster c) {
                Iterator<Vertex<V>> it = c.entries.iterator();
                while(it.hasNext()) {
                    
                    Vertex<V> u = it.next();
                    this.entries.add(u);
                }
                
                c.leader = this.leader;
            }
        }
        
        class WeightComparator implements Comparator<Edge<E>> {

            @Override
            public int compare(Edge<E> e1, Edge<E> e2) {
                if(e1.weight < e2.weight) return -1;
                else if(e1.weight == e2.weight) return 0;
                else return 1;
            }
        }
        
        LinkedList<Edge<E>> mst = new LinkedList<>();
        PriorityQueue<Edge<E>> pq = new PriorityQueue<>(new WeightComparator());
        HashMap<Vertex<V>, Cluster> ClusterFinder = new HashMap<>();
        
        for(Vertex<V> u : this.vertexHashMap.values()) {
            Cluster c = new Cluster(u);
            ClusterFinder.put(u, c);
        }
        
        for(Edge<E> e : this.edgeHashMap.values()) {
            pq.add(e);
        }
        
        //while(mst.size() != this.vertexHashMap.size() - 1) {            
        while(mst.size() != this.vertexHashMap.size() - 1 && !pq.isEmpty()) {
           
            Edge<E> e = pq.remove();
            
            Cluster c1 = ClusterFinder.get(e.endVertices[0]);
            Cluster c2 = ClusterFinder.get(e.endVertices[1]); 
            
            if(c1.leader == c2.leader) {
                continue;
            }
            
            c1.merge(c2);
            
            mst.add(e);
        }
        
        return mst;
    }
}
