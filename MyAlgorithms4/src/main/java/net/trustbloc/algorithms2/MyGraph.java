/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author bz
 */
public class MyGraph<V, E> {

    public class Vertex<V> {

        int k;
        V v;
        HashMap<Vertex<V>, Edge<E>> edgeMap;

        public Vertex(int k, V v, HashMap<Vertex<V>, Edge<E>> edgeMap) {
            this.k = k;
            this.v = v;
            this.edgeMap = edgeMap;
        }

        public Vertex(int k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    public class Edge<E> {

        int k;
        E e;
        int w;
        Vertex<V>[] endVertices;

        public Edge(int k, E e, int w) {
            this.k = k;
            this.e = e;
            this.w = w;
            this.endVertices = new Vertex[2];
        }

        public Edge(int k, E e) {
            this.k = k;
            this.e = e;
        }
    }

    HashMap<Integer, Vertex<V>> vertexHashMap = new HashMap<>();
    HashMap<Integer, Edge<E>> edgeHashMap = new HashMap<>();

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

    public Edge<E> getEdge(Vertex u, Vertex v) {
        return (Edge<E>) u.edgeMap.get(v);
    }

    public Vertex<V>[] endVertices(Edge e) {
        return e.endVertices;
    }

    public Vertex<V> opposite(Vertex u, Edge e) {
        if (u == e.endVertices[0]) {
            return e.endVertices[1];
        }
        return e.endVertices[0];
    }

    public int outDegree(Vertex<V> u) {
        int outdeg = 0;
        for (Edge e : this.edgeHashMap.values()) {
            if (e.endVertices[0] == u) {
                outdeg++;
            }
        }

        return outdeg;
    }

    public int inDegree(Vertex<V> u) {
        int indeg = 0;
        for (Edge e : this.edgeHashMap.values()) {
            if (e.endVertices[1] == u) {
                indeg++;
            }
        }

        return indeg;
    }

    public Collection<Edge<E>> outgoingEdges(Vertex<V> u) {
        Collection<Edge<E>> outgoing = new ArrayList<>();
        for (Edge e : this.edgeHashMap.values()) {
            if (e.endVertices[0] == u) {
                outgoing.add(e);
            }
        }

        return outgoing;
    }

    public Collection<Edge<E>> incomingEdge(Vertex<V> u) {
        Collection<Edge<E>> incoming = new ArrayList<>();
        for (Edge e : this.edgeHashMap.values()) {
            if (e.endVertices[1] == u) {
                incoming.add(e);
            }
        }

        return incoming;
    }

    public void insertVertex(int k, V v) {
        Vertex<V> vertex = new Vertex(k, v);
        this.vertexHashMap.put(k, vertex);
    }

    public void insertEdge(Vertex<V> u, Vertex<V> v, int k, E e) {
        if (this.getEdge(u, v) != null) {
            return;
        }
        Edge<E> edge = new Edge(k, e);
        this.edgeHashMap.put(k, edge);
        u.edgeMap.put(v, edge);
        v.edgeMap.put(u, edge);
    }

    public void removeVertex(int k) {
        Vertex<V> vertex = this.vertexHashMap.get(k);
        for (Edge<E> e : vertex.edgeMap.values()) {
            removeEdge(e.k);
        }
        this.vertexHashMap.remove(k);
    }

    public void removeEdge(int k) {
        Edge<E> e = this.edgeHashMap.get(k);
        for (Vertex<V> v : e.endVertices) {
            v.edgeMap.remove(this.opposite(v, e));
        }

        this.edgeHashMap.remove(k);
    }

    public HashMap<Vertex, Edge> dfs(Vertex<V> s) {

        if (s == null) {
            return null;
        }

        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Stack<Vertex> stack = new Stack<>();
        // To Vertex, from Edge
        HashMap<Vertex, Edge> dfs = new HashMap<>();

        stack.push(s);

        while (!stack.isEmpty()) {
            Vertex<V> u = stack.pop();
            for (Edge<E> e : outgoingEdges(u)) {

                Vertex<V> peer = opposite(u, e);

                if (visited.get(peer)) {
                    continue;
                }
                stack.push(peer);
                visited.put(peer, true);

                dfs.put(peer, e);
            }
        }

        return dfs;
    }

    public HashMap<Vertex, Edge> bfs(Vertex<V> s) {

        if (s == null) {
            return null;
        }

        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Queue<Vertex> q = new LinkedList<>();
        // To Vertex, from Edge
        HashMap<Vertex, Edge> bfs = new HashMap<>();

        q.add(s);

        while (!q.isEmpty()) {
            Vertex<V> u = q.remove();
            for (Edge<E> e : outgoingEdges(u)) {

                Vertex<V> peer = opposite(u, e);

                if (visited.get(peer)) {
                    continue;
                }
                q.add(peer);
                visited.put(peer, true);

                bfs.put(peer, e);
            }
        }

        return bfs;
    }

    public LinkedList<Vertex<V>> recontructPath(Vertex<V> u, Vertex<V> v) {
        if (u == null) {
            return null;
        }

        HashMap<Vertex, Edge> dfs = this.dfs(u);

        if (!dfs.containsKey(v)) {
            return null;
        }

        LinkedList<Vertex<V>> path = new LinkedList<>();
        Vertex<V> curr = v;

        while (curr != u) {

            Edge<E> e = dfs.get(curr);

            path.add(curr);
            curr = opposite(curr, e);
        }
        path.add(u);
        // TODO: reverse path

        return path;
    }

    public boolean connected(Vertex<V> u) {
        return dfs(u).size() == this.vertexHashMap.size();
    }

    public HashMap<Vertex, Edge> dfsComplete(Vertex<V> s) {

        HashMap<Vertex, Edge> dfs = dfs(s);

        for (Vertex<V> u : this.vertexHashMap.values()) {

            if (!dfs.containsKey(u)) {

                HashMap<Vertex, Edge> add = dfs(u);

                dfs.putAll(add);
            }
        }

        return dfs;
    }

    public boolean cycle(Vertex<V> u) {

        if (u == null) {
            return false;
        }

        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Stack<Vertex> stack = new Stack<>();
        // To Vertex, from Edge
        HashMap<Vertex, Edge> dfs = new HashMap<>();

        stack.push(u);

        while (!stack.isEmpty()) {
            Vertex<V> v = stack.pop();
            for (Edge<E> e : outgoingEdges(v)) {

                Vertex<V> peer = opposite(v, e);

                if (visited.get(peer)) {
                    return true;
                }
                stack.push(peer);

                dfs.put(peer, e);
            }
        }

        return false;
    }

    public void floydWarshall() {

        ArrayList<Vertex<V>> vertices = new ArrayList<>();
        for (Vertex<V> u : this.vertices()) {
            vertices.add(u);
        }

        int n = vertices.size();

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                if (i == k) {
                    continue;
                }
                for (int j = 1; j <= n; j++) {
                    if (j == k || i == j) {
                        continue;
                    }

                    Vertex<V> u = vertices.get(i);
                    Vertex<V> v = vertices.get(k);
                    Vertex<V> x = vertices.get(j);

                    if (this.containsEdge(u, v) && this.containsEdge(v, x)) {
                        if (!this.containsEdge(u, x)) {
                            this.connect(u, x);
                        }
                    }
                }
            }
        }
    }

    private boolean containsEdge(Vertex<V> u, Vertex<V> v) {
        return u.edgeMap.containsKey(v);
    }

    private void connect(Vertex<V> u, Vertex<V> v) {
        this.insertEdge(u, v, this.edgeHashMap.size() + 1, null);
    }

    public LinkedList<Vertex> topo() {

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
                if (u.inCount < v.inCount) {
                    return -1;
                } else if (u.inCount == v.inCount) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }

        PriorityQueue<PQEntry> pq = new PriorityQueue<>(new InCountComparator());
        HashMap<Vertex, PQEntry> PQEFinder = new HashMap<>();

        for (Vertex<V> u : this.vertexHashMap.values()) {
            PQEntry pqe = new PQEntry(u);
            pq.add(pqe);

            PQEFinder.put(u, pqe);
        }

        HashMap<Vertex<V>, Boolean> visited = new HashMap<>();

        LinkedList<Vertex> topo = new LinkedList<>();

        while (!pq.isEmpty()) {

            PQEntry pqe = pq.remove();

            if (visited.get(pqe.u)) {
                return null;
            }

            topo.add(pqe.u);

            Iterator<Edge<E>> it = this.outgoingEdges(pqe.u).iterator();
            while (it.hasNext()) {

                Vertex<V> peer = this.opposite(pqe.u, it.next());
                PQEntry PQpeer = PQEFinder.get(peer);

                pq.remove(PQpeer);
                PQpeer.inCount--;

                pq.add(PQpeer);
            }
        }

        return topo;
    }

    public HashMap<Vertex<V>, Integer> shortestPath(Vertex<V> s) {

        class PQEntry {

            Vertex u;
            int d; // distance from s

            PQEntry(Vertex u) {
                this.u = u;
                this.d = Integer.MAX_VALUE;
            }
        }

        class DistanceComparator implements Comparator<PQEntry> {

            @Override
            public int compare(PQEntry u, PQEntry v) {
                if (u.d > v.d) {
                    return 1;
                }
                if (u.d == v.d) {
                    return 0;
                }
                return -1;
            }
        }

        PriorityQueue<PQEntry> pq = new PriorityQueue<>(new DistanceComparator());
        HashMap<Vertex<V>, Integer> shortestPath = new HashMap<>();
        HashMap<Vertex<V>, PQEntry> pqeFinder = new HashMap<>();

        PQEntry pqes = new PQEntry(s);
        pqes.d = 0;
        pq.add(pqes);
        pqeFinder.put(s, pqes);

        for (Vertex<V> u : this.vertexHashMap.values()) {
            if (u == s) {
                continue;
            }
            PQEntry pqe = new PQEntry(u);
            pq.add(pqe);
            pqeFinder.put(u, pqe);
        }

        while (!pq.isEmpty()) {

            PQEntry pqe = pq.remove();
            shortestPath.put(pqe.u, pqe.d);

            Iterator<Edge<E>> it = this.outgoingEdges(pqe.u).iterator();
            while (it.hasNext()) {

                Edge<E> e = it.next();

                PQEntry peer = pqeFinder.get(opposite(pqe.u, e));
                if ((pqe.d + e.w <= peer.d)) {
                    pq.remove(peer);
                    peer.d = pqe.d + e.w;
                    pq.add(peer);
                }
            }
        }

        return shortestPath;
    }

    public LinkedList<Edge<E>> prim(Vertex<V> s) {

        class PQEntry {

            Vertex<V> u;
            int d;

            PQEntry(Vertex<V> u) {
                this.u = u;
                this.d = Integer.MAX_VALUE;
            }
        }

        class DistanceComparator implements Comparator<PQEntry> {

            @Override
            public int compare(PQEntry u, PQEntry v) {
                if (u.d > v.d) {
                    return 1;
                }
                if (u.d == v.d) {
                    return 0;
                }
                return -1;
            }
        }

        PriorityQueue<PQEntry> pq = new PriorityQueue(new DistanceComparator());
        HashMap<Vertex<V>, PQEntry> pqeFinder = new HashMap<>();
        LinkedList<Edge<E>> mst = new LinkedList<>();
        HashMap<PQEntry, Boolean> visited = new HashMap<>();

        for (Vertex<V> u : this.vertexHashMap.values()) {
            PQEntry pqe = new PQEntry(u);
            if (u == s) {
                pqe.d = 0;
            }
            pqeFinder.put(u, pqe);
            pq.add(pqe);
        }

        while (!pq.isEmpty()) {
            PQEntry pqe = pq.remove();

            if (visited.get(pqe)) {
                continue;
            }

            Vertex<V> u = pqe.u;

            for (Edge<E> e : this.outgoingEdges(u)) {

                Vertex<V> peer = this.opposite(u, e);
                PQEntry pqepeer = pqeFinder.get(peer);
                visited.put(pqepeer, true);

                if (e.w < pqepeer.d) {

                    pq.remove(pqepeer);
                    pqepeer.d = e.w;
                    mst.push(e);
                    pq.add(pqepeer);
                }
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

            void merge(Cluster c) {
                for (Vertex<V> u : c.entries) {

                    this.entries.add(u);
                }
                c.leader = this.leader;
            }
        }

        class WeightComparator implements Comparator<Edge<E>> {

            @Override
            public int compare(Edge<E> e1, Edge<E> e2) {
                if (e1.w > e2.w) {
                    return 1;
                }
                if (e1.w == e2.w) {
                    return 0;
                }
                return -1;
            }
        }

        LinkedList<Edge<E>> mst = new LinkedList<>();
        PriorityQueue<Edge<E>> pq = new PriorityQueue<>(new WeightComparator());
        HashMap<Vertex<V>, Cluster> ClusterFinder = new HashMap<>();

        for (Vertex<V> u : this.vertexHashMap.values()) {
            Cluster c = new Cluster(u);
            ClusterFinder.put(u, c);
        }

        for (Edge<E> e : this.edgeHashMap.values()) {
            pq.add(e);
        }

        while (mst.size() != this.vertexHashMap.size() - 1 && !pq.isEmpty()) {

            Edge<E> e = pq.remove();

            Cluster c1 = ClusterFinder.get(e.endVertices[0]);
            Cluster c2 = ClusterFinder.get(e.endVertices[1]);

            if (c1.leader == c2.leader) { // if same cluster
                continue;
            }

            c1.merge(c2);

            mst.add(e);
        }

        return mst;
    }
}
