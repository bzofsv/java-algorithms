package net.trustbloc;

public class MyDoubleLinkedList<E> {
    
    public class Node<E> {
        
        E e;
        Node<E> prev;
        Node<E> next;
        
        public Node(E e, Node<E> prev, Node<E> next) {
            this.e = e;
            this.next = prev;
            this.prev = next;
        }
        
        public Node(E e) {
            this(e, null, null);
        }
    }
    
    int size;
    Node<E> head;
    Node<E> tail;
    
    public MyDoubleLinkedList() {
        this.head = null;
        this.tail = null;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public int size() {
        return size;
    }
    
    public E first() {
        if(head == null) return null;
        return head.e;
    }
    
    public E last() {
        if(tail == null) return null;
        return tail.e;
    }
    
    public void addFirst(E e) {
        
        Node<E> node = new Node(e);
        
        if(head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
    }
    
    public void addLast(E e) {
        
        Node<E> node = new Node(e);
        
        if(tail == null) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail = node;
        }
    }
    
    public E removeFirst() {
        
        if(head == null) {
            return null;
        }
        
        Node<E> ret = head;
        
        head = head.next;
        head.prev = null;
        
        return ret.e;
    }
    
    public E removeLast() {
        
        if(tail == null) {
            return null;
        }
        
        Node<E> ret = tail;
        
        tail = tail.prev;
        tail.next= null;
        
        return ret.e;
    }
}
