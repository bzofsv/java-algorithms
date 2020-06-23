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
public class MyDoubleLinkedList<E> {
    public class Node<E> {
        E e;
        Node<E> next;
        Node<E> prev;
        
        public Node(E e, Node<E> next, Node<E> prev) {
            this.e = e;
            this.next = next;
            this.prev = prev;
        }
        
        public Node(E e) {
            this(e, null, null);
        }
    }
    
    int size;
    Node<E> head;
    Node<E> tail;
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public int size() {
        return size;
    }
    
    public E first() {
        if(this.isEmpty()) return null;
        return head.e;
    }
    
    public E last() {
        if(this.isEmpty()) return null;
        return tail.e;
    }
    
    public void addFirst(E e) {
        
        Node<E> node = new Node(e);
        
        if(this.isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }
    
    public void addLast(E e) {
        
        Node<E> node = new Node(e);
        
        if(this.isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail = node;
        }
        size++;
    }
    
    public E removeFirst() {
        
        if(this.isEmpty()) return null;
        
        Node<E> node = head;
        
        head = head.next;
        head.prev = null;
        size--;
        
        return node.e;
    }
    
    public E removeLast() {
        if(this.isEmpty()) return null;
            
        Node<E> node = tail;
        
        tail = tail.prev;
        tail.next = null;
        size--;
        
        return node.e;
    }
}