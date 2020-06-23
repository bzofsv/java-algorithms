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
public class MySingleLinkedList<E> {
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
    
    Node<E> head;
    Node<E> tail;
    int size;
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public E first() {
        if(isEmpty()) return null;
        return head.e;
    }
    
    public E last() {
        if(isEmpty()) return null;
        return tail.e;
    }
    
    public void addFirst(E e) {
        Node<E> node = new Node(e, this.head);
        this.head = node;
        if(isEmpty()) this.tail = node;
        this.size++;
    }
    
    public void addLast(E e) {
        Node<E> node = new Node(e);
        this.tail.next = node;
        this.tail = node;
        if(isEmpty()) this.head = node;
        this.size++;
    }
    
    public E removeFirst() {
        if(isEmpty()) return null;
        
        E e = this.head.e;
        this.head = this.head.next;
        if(size == 1) tail = null;
        this.size--;
        return e;
    }
}
