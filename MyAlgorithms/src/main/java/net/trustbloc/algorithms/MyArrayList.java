package net.trustbloc.algorithms;

import java.util.Iterator;

public class MyArrayList <E> {
	
	Object []array;
	int size;
	
	public MyArrayList(E []array) {
		this.array = array;
		this.size = array.length;
	}
	
	public MyArrayList(int cap) {
		this.array = new Object[cap];
		this.size = 0;
	}
	
	public void add(E e) {
		if(this.size == this.array.length - 1) {
			Object []array2 = new Object[this.array.length * 2];
			for(int i = 0; i < this.array.length; i++) {
				array2[i] = this.array[i];
			}
			this.array = array2;
		}
		this.array[size++] = e;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int i) {
		if(i >= this.size) {
			return null;
		}
		return (E) (this.array[i]);
	}
	
	@SuppressWarnings("unchecked")
	public E rem() {
		if(this.size == 0) return null; 
		return (E) this.array[this.size-- - 1];
	}
	
	@SuppressWarnings("unchecked")
	public E rem(int i) {
		if(i >= this.size) return null;
		Object temp = this.array[i];
		this.array[i] = null;
		for(int j = i; j < size; j++) {
			this.array[j] = this.array[j + 1];
		}
		this.size--;
		return (E) temp;
	}
	
	public void addAfter(E e, int i) {

		if(this.size == this.array.length - 1) {
			Object []array2 = new Object[this.array.length * 2];
			for(int j = 0; j < this.array.length; j++) {
				array2[j] = this.array[j];
			}
		}

		if(i >= size) {
			this.add(e);
			return;
		}
		for(int j = size - 1; j > i; j--) {
			this.array[j + 1] = this.array[j];
		}
		this.array[i + 1] = e;		
	}
	
	private class MyIterator implements Iterator<E> {

		int i = 0;
		
		@Override
		public boolean hasNext() {
			return i < size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			return (E) array[i++];
		}
		
	}
	
	public Iterator<E> iterator() {
		return new MyIterator();
	}

}
