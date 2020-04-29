package net.trustbloc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
}
