/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms2;

/**
 *
 * @author bz
 */
public class MyCircularLinkedList<E> {
    public class Node<E> {
        E e;
        Node<E> next;
        
        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
        
        public Node(E e) {
            this(e, null);
        }
    }
    
    Node<E> tail;
    int size;
    
    public boolean isEmpty() {
        return tail == null;
    }
    
    public int size() {
        return this.size;
    }
    
    public E first() { 
        if(isEmpty()) return null;
        return tail.next.e;
    }
    
    public E last() {
        if(isEmpty()) return null;
        return tail.e;
    }
    
    public void addFirst(E e) {
        Node<E> node = new Node(e);
        node.next = tail.next;
        tail.next = node;
        if(isEmpty()) tail.next = tail;
        size++;
    }
    
    public void addLast(E e) {
        Node<E> node = new Node(e);
        node.next = tail.next;
        tail.next = node;
        tail = node;
        if(isEmpty()) tail.next = tail;
        size++;
    }
    
    public E removeFirst() {
        if(isEmpty()) return null;
        E e = tail.next.e;
        tail.next = tail.next.next;
        return e;
    }
    
    public void rotate() {
        if(isEmpty()) return;
        tail = tail.next;
    }
    
    public void reverse() {
  
        if(this.size < 2) return;
  
        Node<E> curr = tail;
        Node<E> prev = tail.next;
        
        do {
            prev.next = curr;
            curr = prev;
            prev = curr.next;
        } while(curr != tail);
    }
}
