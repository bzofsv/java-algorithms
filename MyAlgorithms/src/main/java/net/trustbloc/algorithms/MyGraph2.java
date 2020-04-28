package net.trustbloc.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MyGraph2 {
	
	public static class Vertex {
		int id;
		Object d;
		HashMap<Vertex, Edge> edgeMap = new HashMap<>();
		
		public Vertex(int id, Object d, HashMap<Vertex, Edge> edgeMap) {
			this.id = id;
			this.d = d;
			this.edgeMap = edgeMap;
		}
		
		public Vertex(int id, Object d) {
			this(id, d, null);
		}
		
		public Vertex(int id) {
			this(id, null);
		}
	}
	
	public static class Edge {
		int id;
		Vertex[] v = new Vertex[2];
		Object d;
		boolean directed;
		int weight;
		
		public Edge(int id, Vertex v1, Vertex v2, Object d, boolean directed, int weight) {
			this.id = id;
			this.v[0] = v1;
			this.v[1] = v2;
			this.d = d;
			this.directed = directed;
			this.weight = weight;
			
		}
		
		public Edge(int id, Vertex v1, Vertex v2, Object d, int weight) {
			this(id, v1, v2, d, false, weight);
		}
		
		public Edge(int id, Vertex v1, Vertex v2, Object d) {
			this(id, v1, v2, d, 0);
		}
		
		public Edge(int id, Vertex v1, Vertex v2) {
			this(id, v1, v2, null);
		}
	}
	
	HashMap<Integer, Vertex> vertexHashMap = new HashMap<>();
	HashMap<Integer, Edge> edgeHashMap = new HashMap<>();
	
	public MyGraph2() {}
	
	public int numVertices() {
		return this.vertexHashMap.size();
	}
	
	public Collection<Vertex> vertices() {
		return this.vertexHashMap.values();
	}
	
	public int numEdges() {
		return this.edgeHashMap.size();
	}
	
	public Collection<Edge> edges() {
		return this.edgeHashMap.values();
	}
	
	public Vertex[] endVertices(Edge e) {
		return e.v;
	}
	
	public Edge getEdge(Vertex u, Vertex v) {
		return u.edgeMap.get(v);
	}
	
	public Vertex opposite(Vertex u, Edge e) {
		return e.v[0] == u ? e.v[1] : e.v[0];
	}
	
	public int outDegree(Vertex v) {
		int deg = 0;
		for(Edge e : v.edgeMap.values()) {
			if(v == e.v[0]) {
				deg++;
			}
		}
		
		return deg;
	}
	
	public int inDegree(Vertex v) {
		int deg = 0;
		for(Edge e : v.edgeMap.values()) {
			if(v == e.v[1]) {
				deg++;
			}
		}
		
		return deg;
	}
	
	public Collection<Edge> outgoingEdges(Vertex v) {
		ArrayList<Edge> el = new ArrayList<>();
		for(Edge e : v.edgeMap.values()) {
			if(v == e.v[0]) {
				el.add(e);
			}		
		}
		return el;
	}
	
	public Collection<Edge> incomingEdges(Vertex v) {
		ArrayList<Edge> el = new ArrayList<>();
		for(Edge e : v.edgeMap.values()) {
			if(v == e.v[1]) {
				el.add(e);
			}		
		}
		return el;
	}
	
	public Vertex insertVertex(int id, Object x) {
		Vertex v = new Vertex(id, x);
		this.vertexHashMap.put(id, v);
		return v;
	}
	
	public Edge insertEdge(Vertex u, Vertex v, int id, Object x) {
		Edge e = new Edge(id, u, v, x);
		
		u.edgeMap.put(v, e);
		v.edgeMap.put(u, e);
		this.edgeHashMap.put(id, e);
		
		return e;
	}
	
	public void removeVertex(Vertex v) {
		for(Edge e : v.edgeMap.values()) {
			Vertex u = opposite(v, e);
			u.edgeMap.remove(v);
			this.edgeHashMap.remove(e.id);
		}
		this.vertexHashMap.remove(v.id);
	}
	
	public void removeEdge(Edge e) {
		e.v[0].edgeMap.remove(e.v[1]);
		e.v[1].edgeMap.remove(e.v[0]);
		this.edgeHashMap.remove(e.id);
	}
	
	public Collection<Edge> dfs(Vertex u) {
		
		// spanning tree of connected vertices
		LinkedList<Edge> l = new LinkedList<>();
		
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		Stack<Vertex> s = new Stack<>();
		s.add(u);
		while(!s.isEmpty()) {
			Vertex v = s.pop();
			visited.put(v, true);
			for(Edge e : outgoingEdges(v)) {
				if(!visited.get(opposite(v, e))) {
					s.add(opposite(v, e));
					l.add(e);
				}
			}
		}
		
		return l;
	}
	
	public List<Edge> path(Vertex u, Vertex v) {
		
		// route to a vertex from an edge
		HashMap<Vertex, Edge> route = new HashMap<>();
		
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		
		Stack<Vertex> s = new Stack<>();
		
		s.add(u);
		
		while(!s.isEmpty()) {
			Vertex x = s.pop();
			visited.put(x, true);
			for(Edge e : outgoingEdges(x)) {
				if(!visited.get(opposite(x, e))) {
					s.add(opposite(x, e));
					route.put(opposite(x, e), e);
				}
			}
		}
		
		if(!visited.get(v)) return null;
		
		//path from u to v
		LinkedList<Edge> l = new LinkedList<>();
						
		Vertex x = v;
		while(x != u) {
			Edge e = route.get(x);
			l.add(e);
			x = opposite(x, e);
		} 
	
		return l;
	}

	public boolean connected() {
		
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		Stack<Vertex> s = new Stack<>();
		Vertex u = (Vertex) this.vertexHashMap.values().toArray()[0];
		s.add(u);
		while(!s.isEmpty()) {
			Vertex v = s.pop();
			visited.put(v, true);
			for(Edge e : outgoingEdges(v)) {
				if(!visited.get(opposite(v, e))) {
					s.add(opposite(v, e));
				}
			}
		}
		
		return visited.size() == this.vertexHashMap.size();
	}
	
	public boolean stronglyConnected() {
		
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		Stack<Vertex> s = new Stack<>();
		Vertex u = (Vertex) this.vertexHashMap.values().toArray()[0];
		s.add(u);
		while(!s.isEmpty()) {
			Vertex v = s.pop();
			visited.put(v, true);
			for(Edge e : outgoingEdges(v)) {
				if(!visited.get(opposite(v, e))) {
					s.add(opposite(v, e));
				}
			}
		}
		
		if(visited.size() != this.vertexHashMap.size()) return false;
		
		visited.clear();
		s.clear();
		
		u = (Vertex) this.vertexHashMap.values().toArray()[0];
		s.add(u);
		while(!s.isEmpty()) {
			Vertex v = s.pop();
			visited.put(v, true);
			for(Edge e : incomingEdges(v)) {
				if(!visited.get(opposite(v, e))) {
					s.add(opposite(v, e));
				}
			}
		}
		
		return visited.size() == this.vertexHashMap.size();
	}
	
	public boolean cyclic() {
		
		HashMap<Vertex, Edge> route = new HashMap<>();
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		Stack<Vertex> s = new Stack<>();
		Vertex u = (Vertex) this.vertexHashMap.values().toArray()[0];
		s.add(u);
		while(!s.isEmpty()) {
			Vertex v = s.pop();
			visited.put(v, true);
			for(Edge e : outgoingEdges(v)) {
				if(!visited.get(opposite(v, e))) {
					s.add(opposite(v, e));
					route.put(opposite(v, e), e);
				} else {
					Vertex x = opposite(v, route.get(v));
					while(x != u) {
						if(x == opposite(v, e)) return true;
						x = opposite(x, route.get(x));
					}
					
				}			
			}
		}
		
		return false;
	}
	
	public List<Vertex> bfs(Vertex u) {
		
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		Queue<Vertex> q = new LinkedList<>();
		List<Vertex> l = new LinkedList<>();
		
		if(u == null) return null;
		
		q.add(u);
		
		while(!q.isEmpty()) {
			Vertex v = q.remove();
			l.add(v);
			for(Edge e : this.outgoingEdges(v)) {
				if(!visited.get(this.opposite(v, e))) {
					q.add(this.opposite(v, e));
				}
			}
		}
		
		return l;
	}
	
	public HashMap<Integer, Edge> floydWarshall() {
		
		HashMap<Integer, Edge> edgeHashMap2 = new HashMap<>();
		
		for(Integer key : this.edgeHashMap.keySet()) {
			edgeHashMap2.put(key, this.edgeHashMap.get(key));
		}
		
		int nv = this.vertexHashMap.size();
		for(int k = 0; k < nv; k++) {
			for(int i = 0; i < nv; i++) {
				for(int j = 0; j < nv; j++) {
					Vertex vi = this.vertexHashMap.get(i);
					Vertex vk = this.vertexHashMap.get(k);
					Vertex vj = this.vertexHashMap.get(j);
					Edge eik = vi.edgeMap.get(vk);
					Edge ekj = vk.edgeMap.get(vj);
					if(eik != null && ekj != null) {
						Edge eij = new Edge(new Random().nextInt(), vi, vj);
						vi.edgeMap.put(vj,  eij);
						edgeHashMap2.put(eij.id, eij);
					}
				}
			}
		}
		
		return edgeHashMap2;
	}
	
	public List<Vertex> topoSort() {
		
		HashMap<Vertex, Integer> incount = new HashMap<>();
		Stack<Vertex> ready = new Stack<>();
		List<Vertex> topo = new LinkedList<>();
		
		for(Vertex u : this.vertices()) {
			incount.put(u, this.inDegree(u));
			
			if(incount.get(u) == 0) {
				ready.add(u);
			}
		}
		
		while(!ready.isEmpty()) {
			Vertex v = ready.pop();
			topo.add(v);
			
			for(Edge e : this.outgoingEdges(v)) {
				Vertex w = this.opposite(v, e);
				if(incount.get(w) - 1 == 0) {
					ready.push(w);
					topo.add(w);
				}
				
				incount.put(w, incount.get(w) - 1);
			}
		}
		
		return topo;
	}
	
	public HashMap<Vertex, Integer> shortestPath(Vertex s) {
		
		class VD {
			Vertex u;
			int d;
			VD(Vertex u, int d) {
				this.u = u;
				this.d = d;
			}
		}
		
		class VDComparator implements Comparator<VD> {
			
			public int compare(VD vd1, VD vd2) {
				
				if(vd1.d < vd2.d) return 1;
				if(vd1.d > vd2.d) return -1;
				return 0;
			}
		}
		
		PriorityQueue<VD> pq = new PriorityQueue<>(this.vertexHashMap.size(), new VDComparator());
		HashMap<Vertex, Integer> cloud = new HashMap<>();
		HashMap<Vertex, Integer> d = new HashMap<>();
		HashMap<Vertex, VD> pqp = new HashMap<>();
		
		// every vertex except s has weight of max value
		for(Vertex v : this.vertices()) {
			VD vd = new VD(v, Integer.MAX_VALUE);
			if(v == s) {
				vd.d = 0;
			}	
			
			d.put(v, vd.d);
			pqp.put(v, vd);
			pq.add(vd);
		}
		
		while(!pq.isEmpty()) {
			VD vdu = pq.remove();
			for(Edge e : this.outgoingEdges(vdu.u)) {
				Vertex v = this.opposite(vdu.u, e);
				int nd = e.weight + d.get(vdu.u);
				if(d.get(v) > nd) {
					d.put(v, nd);
					VD vdv =pqp.get(v);
					pq.remove(vdv);
					vdv.d = nd;
					pq.add(vdv);
					pqp.put(v, vdv);
				}
			}
		}
		for(Vertex key : d.keySet()) {
			cloud.put(key, d.get(key));
		}
		
		return cloud;
	} 
	
	public Collection<Edge> constructPath(Vertex s) {
		class VD {
			Vertex u;
			int d;
			VD(Vertex u, int d) {
				this.u = u;
				this.d = d;
			}
		}
		
		class VDComparator implements Comparator<VD> {
			
			public int compare(VD vd1, VD vd2) {
				
				if(vd1.d < vd2.d) return 1;
				if(vd1.d > vd2.d) return -1;
				return 0;
			}
		}
		
		PriorityQueue<VD> pq = new PriorityQueue<>(this.vertexHashMap.size(), new VDComparator());
		//Vertex to shortest incoming Edge
		HashMap<Vertex, Edge> path = new HashMap<>();
		HashMap<Vertex, Integer> cloud = new HashMap<>();
		HashMap<Vertex, Integer> d = new HashMap<>();
		HashMap<Vertex, VD> pqp = new HashMap<>();
		
		// every vertex except s has weight of max value
		for(Vertex v : this.vertices()) {
			VD vd = new VD(v, Integer.MAX_VALUE);
			if(v == s) {
				vd.d = 0;
			}	
			
			d.put(v, vd.d);
			pqp.put(v, vd);
			pq.add(vd);
		}
		
		while(!pq.isEmpty()) {
			VD vdu = pq.remove();
			for(Edge e : this.outgoingEdges(vdu.u)) {
				Vertex v = this.opposite(vdu.u, e);
				int nd = e.weight + d.get(vdu.u);
				if(d.get(v) > nd) {
					d.put(v, nd);
					VD vdv =pqp.get(v);
					pq.remove(vdv);
					vdv.d = nd;
					pq.add(vdv);
					pqp.put(v, vdv);
					path.put(v, e);
				}
			}
		}
		for(Vertex key : d.keySet()) {
			cloud.put(key, d.get(key));
		}
		
		return path.values();
	}
	
	public List<Edge> prim(Vertex s) {
		
		class VD {
			Vertex u;
			int d;
			VD(Vertex u, int d) {
				this.u = u;
				this.d = d;
			}
		}
		
		class VDComparator implements Comparator<VD> {
			
			public int compare(VD vd1, VD vd2) {
				
				if(vd1.d < vd2.d) return 1;
				if(vd1.d > vd2.d) return -1;
				return 0;
			}
		}
		
		// spanning tree
		List<Edge> st = new LinkedList<>();
				
		PriorityQueue<VD> pq = new PriorityQueue<>(this.vertexHashMap.size(), new VDComparator());
		HashMap<Vertex, VD> pqp = new HashMap<>();
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		
		HashMap<Vertex, Edge> route = new HashMap<>();
		
		// every vertex except s has weight of max value
		for(Vertex v : this.vertices()) {
			VD vd = new VD(v, Integer.MAX_VALUE);
			if(v == s) {
				vd.d = 0;
			}	
			
			pqp.put(v, vd);
			pq.add(vd);
		}
		
		while(!pq.isEmpty()) {
			VD vdu = pq.remove();
			Edge x = route.get(vdu.u);
			st.add(x);
			visited.put(vdu.u, true);
			
			for(Edge e : this.outgoingEdges(vdu.u)) {
				
				Vertex v = this.opposite(vdu.u, e);
				
				if (visited.get(v)) continue;
	
				if(pqp.get(v).d > e.weight) {
					VD vdv = pqp.get(v);
					pq.remove(vdv);
					vdv.d = e.weight;
					pq.add(vdv);
					pqp.put(v, vdv);
				}
			}
		}
		
		return st;
	}
	
	public List<Edge> kruskal() {
		
		class Partition<E> {
		
			class Cluster<E> {
				// leader is first in values
				ArrayList<E> values = new ArrayList<>();
				
				public Cluster(E l, Collection<E> values) {
					this.values.add(l);
					this.values.addAll(values);
				}

				public Cluster(E l) {
					this(l, null);
				}
			}
			
			HashMap<E, Cluster<E>> map = new HashMap<>();
			
			public Partition() {}
			
			public void add(Cluster<E> c, E e) {
				c.values.add(e);
				map.put(e, c);
			}
			
			public Cluster<E> makeCluster(E e) {
				Cluster<E> c = new Cluster<E>(e);
				map.put(e, c);
				return c;
			}
			
			public Cluster<E> union(Cluster<E> a, Cluster<E> b) {
				Cluster<E> r = a;
				if(a.values.size() < b.values.size()) {
					r = b;
					r.values.addAll(a.values);
				} else r.values.addAll(b.values); 
				
				return r;
			}
			
			public E find(E e) {
				Cluster<E> c = map.get(e);
				if(c == null) return null;
				return c.values.get(0);
			}
                }
		
		class ew {
                    Edge e;
                    int w;

                    public ew(Edge e, int w) {
                            this.e = e;
                            this.w = w;
                    }
		}
                
                class ewComparator implements Comparator<ew> {	
                        public int compare(ew ew1, ew ew2) {

                                if(ew1.w < ew2.w) return 1;
                                if(ew1.w > ew2.w) return -1;
                                return 0;
                        }
                }
                
		PriorityQueue<ew> pq = new PriorityQueue<>(this.edgeHashMap.size(), new ewComparator());
		// to be returned "r"
		List<Edge> r = new LinkedList<>();
		Partition<Vertex> part = new Partition<>();
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		
		for(Vertex v : this.vertexHashMap.values()) {
			part.makeCluster(v);
		}
		
		for(Edge e : this.edgeHashMap.values()) {
			pq.add(new ew(e, e.weight));
		}
		
		while(visited.size() < this.vertexHashMap.size() && !pq.isEmpty()) {
			
			ew ew = pq.remove();
			
			if(part.find(this.endVertices(ew.e)[0]) == part.find(this.endVertices(ew.e)[1])) {
				continue;
			} else {
				Partition<Vertex>.Cluster<Vertex> c1 = part.map.get(this.endVertices(ew.e)[0]);
				Partition<Vertex>.Cluster<Vertex> c2 = part.map.get(this.endVertices(ew.e)[1]);
				part.union(c1, c2);
				r.add(ew.e);
				visited.put(ew.e.v[0], true);
				visited.put(ew.e.v[1],true);
			}
		}
		
		return r;
	}
}





