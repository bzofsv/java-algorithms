package net.trustbloc.algorithms;

import java.util.Iterator;

public class MyDoubleLinkedList<K, V> {
	
	public class Node<K, V> {
		Node<K, V> next;
		Node<K, V> prev;
		K k;
		V v;
		
		public Node(K k, V v, Node<K, V> prev, Node<K, V> next) {
			this.k = k;
			this.v = v;
			this.prev = prev;
			this.next = next;
		}
	}
	
	Node<K, V> head;
	
	public MyDoubleLinkedList() {
		this.head = null;
	}
	
	public void add(K k, V v) {
		Node<K, V> n = new Node<>(k, v, null, null);
		
		if(head == null) {
			head = n;
			return;
		}
		
		n.next = head;
		head.prev = n;
		head = n;
		return;
	}
	
	public V rem(K k) {
		if(head == null) return null;
		
		Node<K, V> curr = head;
		while(curr != null) {
			if(k.hashCode() == curr.k.hashCode()) {
				curr.prev.next = curr.next;
				curr.next.prev = curr.prev;
				return curr.v;
			}
		}
		
		return null;
	}
	
	public V get(K k) {
		if(head == null) return null;
		
		Node<K, V> curr = head;
		while(curr != null) {
			if(k.hashCode() == curr.k.hashCode()) {
				return curr.v;
			}
		}
		
		return null;
	}
	
	public void push(K k, V v) {
		add(k, v);
	}
	
	public V pop() {
		
		if(head == null) return null;
		
		V v = head.v;
		head = head.next;
		head.prev = null;
		return v;
	}
	
	public V remTail() {
		if(head == null) return null;
		
		Node<K, V> curr = head;
		while(curr.next != null) {
			curr = curr.next;
		}
		V v = curr.v;
		curr.prev.next = null;
		return v;
	}
	
	private class MyIterator implements Iterator<V> {
		
		Node<K, V> curr;
		
		public MyIterator() {
			this.curr = head;
		}
		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public V next() {
			V v = curr.v;
			curr = curr.next;
			return v;
		}
		
	}
	
	public Iterator<V> iterator() {
		return new MyIterator();
	}
}
