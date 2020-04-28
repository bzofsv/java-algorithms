package net.trustbloc.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MyAVL2<V> {
	
	public class Node<V> {
		Node<V> p;
		Node<V> l;
		Node<V> r;
		
		int k;
		V   v;
		
		int h;
		
		public Node(int k, V v, Node<V> p, Node<V> l, Node<V> r, int h) {
			
			this.k = k;
			this.v = v;
			
			this.p = p;
			this.l = l;
			this.r = r;
			
			this.h = h;
		}
		
		public Node(int k, V v) {
			this(k, v, null, null, null, 1);
		}
		
	}
	
	Node<V> root;
		
	int size;
	
	public MyAVL2(Node<V> root, int size) {
		this.root = root;
		this.size = size;
	}
	
	public MyAVL2() {
		this(null, 0);
	}
	
	public V search(int k) {
		return this.search(k, root);
	}
	
	private V search(int k, Node<V> root) {
		
		if(k == root.k) return root.v;
		
		if(k < root.k) {
			return this.search(k, root.l);
		}
		
		if(k > root.k) {
			return this.search(k, root.r);
		}
		
		return null;
	}
	
	public void insert(int k, V v) {
		
		Node<V> x, y, z;
		Node<V> node = new Node<>(k, v);
		boolean inserted = this.insert(node, root);	
	
		if(!inserted) return; // no need to rebalance
	
		if(node.h == sibling(node).h) return;
		
		if(sibling(node) == null || node.h > sibling(node).h) {
			Node<V> p = node.p;
			while(p != null) {
				p.h++;
				int sibh = sibling(p) == null ? 0 : sibling(p).h;  
				if(Math.abs(p.h - sibh) > 1) {
					break;
				}
				p = p.p;
			}
			z = p.p;
			z.h++;
			y = tallerChild(z);
			x = tallerChild(y);
			rebalance(x, y, z);
		}
		
		return; //sibling height greater, so no op
	}
	
	private void rebalance(Node<V> x, Node<V> y, Node<V> z) {
		
		if(z.l== y && y.l == x) {
			z.l = y.r;
			y.r = z;
			y.p = z.p;
			if(z == z.p.r) z.p.r = y;
			else z.p.l = y;
			z.p = y;
			
			z.h -= 2;
			
			return;
		}

		if(z.r == y && y.r == x) {
			z.r = y.l;
			y.l = z;
			y.p = z.p;
			if(z == z.p.r) z.p.r = y;
			else z.p.l = y;
			z.p = y;
			
			z.h -= 2;
			
			return;
		}

		if(z.l == y && y.r == x) {
			y.r = x.l;
			z.l = x.r;
			x.r = z;
			x.l = y;
			if(z == z.p.r) z.p.r = x;
			else z.p.l = x;
			z.p = x;
			y.p = x;
			
			z.h -= 2;
			x.h++;
			y.h--;
			
			return;
		} 
		
		if(z.r == y && y.l == x) {
			z.r = x.l;
			y.l = x.r;
			x.r = y;
			x.l = z;
			if(z == z.p.r) z.p.r = x;
			else z.p.l = x;
			z.p = x;
			y.p = x;
			
			z.h -= 2;
			x.h++;
			y.h--;
			
			return;
		}
		
		return;
	}
	
	private Node<V> tallerChild(Node<V> p) {
		if(p.l == null) {
			if(p.r == null) {
				return null;
			}
			return p.r;
		}
		if(p.r == null) return p.l;
		
		if(p.r.h == p.l.h) {
			if(p.p.r == p) return p.r;
			return p.l;
		}
		
		if(p.r.h > p.l.h) return p.r;
		return p.l;
	}
	
	private Node<V> sibling(Node<V> node) {
		if(node.p.r == node) return node.p.l;
		return node.p.r;
	}
	
	private boolean insert(Node<V> node, Node<V> root) {
		
		if(node.k == root.k) {
			root.v = node.v;
			return false; //replaced
		}
		
		if(node.k < root.k) {
			if(root.l == null) {
				root.l = node;
				node.p = root;
				return true;
			}
			return insert(node, root.l);
		}
		
		if(node.k > root.k) {
			if(root.r == null) {
				root.r = node;
				node.p = root;
				return true;
			}	
			return insert(node, root.r);
		}
		
		return false;
	}
	
	private boolean hbalanced(Node<V> node) {
		if(node == null) return true;
		
		int lh = node.l == null ? 0 : node.l.h;
		int rh = node.r == null ? 0 : node.r.h;
		
		if(Math.abs(lh - rh) > 1) return false;
		
		return true;
	}
	
	public V remove(int k) {
		return remove(k, root);
	}
	
	private V remove(int k, Node<V> node) {
		
		if(node == null) return null;
		
		if(k == node.k) {
			// 1/ with no children
			// 2/ with one child
			// 3/ with two children
			if(node.l == null && node.r == null) {
				if(node.p.r == node) {
					node.p.r = null;
					
					// adjust height
					Node<V> p = node.p;
					while(p != null) {
						p.h--;
						if(!hbalanced(p)) break;
						p = p.p;
					}
					
					Node<V> x, y, z;
					z = p;
					y = tallerChild(z);
					x = tallerChild(y);
					rebalance(x, y, z);
					
					return node.v;
				} else {
					node.p.l = null;
					
					// adjust height
					Node<V> p = node.p;
					while(p != null) {
						p.h--;
						if(!hbalanced(p)) break;
						p = p.p;
					}
					
					Node<V> x, y, z;
					z = p;
					y = tallerChild(z);
					x = tallerChild(y);
					rebalance(x, y, z);
					
					return node.v;
				}
			}
			
			if(node.l == null && node.r != null) {
				node.r.p = node.p;
				if(node == node.p.r) {
					node.p.r = node.r;
				} else {
					node.p.l = node.r;
				}
				
				// adjust height
				Node<V> p = node.p;
				while(p != null) {
					p.h--;
					if(!hbalanced(p)) break;
					p = p.p;
				}
				
				Node<V> x, y, z;
				z = p;
				y = tallerChild(z);
				x = tallerChild(y);
				rebalance(x, y, z);
				
				return node.v;
			} 
			
			if(node.l != null && node.r == null) {
				node.l.p = node.p;
				if(node == node.p.r) {
					node.p.r = node.l;
				} else {
					node.p.l = node.l;
				}
				
				// adjust height
				Node<V> p = node.p;
				while(p != null) {
					p.h--;
					if(!hbalanced(p)) break;
					p = p.p;
				}
				
				Node<V> x, y, z;
				z = p;
				y = tallerChild(z);
				x = tallerChild(y);
				rebalance(x, y, z);
				
				return node.v;
			}
			
			if(node.l != null && node.r == null) {
				
				Node<V> temp = node;
				temp = temp.r;
				while(temp.l != null) {
					temp = temp.l;
				}
				
				if(temp.r == null) {
					// temp is leaf
					Node<V> p = temp.p;
					
					node.l.p = temp;
					node.r.p = temp;
					temp.p = node.p;
					if(node == node.p.r) {
						node.p.r = temp;
					} else {
						node.p.l = temp;
					}
					temp.p.l = null;
				
					// adjust height
					temp.h = node.h;
					while(p != null) {
						p.h--;
						if(!hbalanced(p)) break;
						p = p.p;
					}
					
					Node<V> x, y, z;
					z = p;
					y = tallerChild(z);
					x = tallerChild(y);
					rebalance(x, y, z);
					
					return node.v;
				} else {
					// temp has right child
					temp.p.l = temp.r;
					temp.r.p = temp.p;
					
					node.l.p = temp;
					node.r.p = temp;
					
					Node<V> p = temp.p;
					temp.p = node.p;
					if(node == node.p.r) {
						node.p.r = temp;
					} else {
						node.p.l = temp;
					}
					
					// adjust height
					temp.h = node.h;
					while(p != null) {
						p.h--;
						if(!hbalanced(p)) break;
						p = p.p;
					}
					
					Node<V> x, y, z;
					z = p;
					y = tallerChild(z);
					x = tallerChild(y);
					rebalance(x, y, z);
					
					return node.v;
				}
			}
			
		} 
		
		if(k < node.k) {
			return remove(k, node.l);
		} 

		if (k > node.k) {
			return remove(k, node.r);
		}
		
		return null;
	}
	
	public List<V> sort() {
		ArrayList<V> list = new ArrayList<>();
		return sort(root, list);
	}
	
	private List<V> sort(Node<V> node, List<V> list) {
		if(node == null) return list;
		
		sort(node.l, list);
		list.add(node.v);
		sort(node.r, list);
		return list;
	}
	
	public List<V> inorder(Node<V> node, List<V> list) {
		if(node == null) return list;
		
		inorder(node.l, list);
		list.add(node.v);
		inorder(node.r, list);
		return list;
	}
	
	public List<V> preorder(Node<V> node, List<V> list) {
		if(node == null) return list;
		
		list.add(node.v);
		preorder(node.l, list);
		preorder(node.r, list);
		return list;
	}
	
	public List<V> postorder(Node<V> node, List<V> list) {
		if(node == null) return list;
		
		postorder(node.l, list);
		postorder(node.r, list);
		list.add(node.v);
		return list;
	}
	
	//TODO breadth-first and depth first
	public List<V> dft(List<V> list) {
		Stack<Node<V>> s = new Stack<>();
		s.push(root);
		while(!s.isEmpty()) {
			Node<V> n = s.pop();
			list.add(n.v);
			if(n.r != null) { 
				s.push(n.r);
			}
			if(n.l != null) {
				s.push(n.l);
			}
		}
		
		return list;
	}
	
	public List<V> bft(List<V> list) {
		Queue<Node<V>> q = new LinkedList<>();
		q.add(root);
		while(!q.isEmpty()) {
			Node<V> n = q.remove();
			list.add(n.v);
			if(n.l != null) {
				q.add(n.l);
			}
			if(n.r != null) {
				q.add(n.r);
			}
		}
		
		return list;
	}
	

}
