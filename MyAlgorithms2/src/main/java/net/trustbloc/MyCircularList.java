package net.trustbloc;

public class MyCircularList<E> {
    
    public class Node<E> {
        E e;
        Node<E> next;
        
        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }
    
    Node<E> tail;
    int size;
    
    public Node<E> first() {
        return tail.next;
    }
    
    public Node<E> last() {
        return tail;
    }
    
    public boolean isEmpty() {
        if(tail == null) return true;
        return false;
    }
    
    public void rotate() {
        if(tail == null) return;
        tail = tail.next;
    }
    
    public void addFirst(E e) {
        Node<E> node = new Node<>(e, null);
        node.next = tail.next;
        tail.next = node;
        size++;
    }
    
    public void addLast(E e) {
        Node<E> node = new Node(e, null);
        Node<E> head = tail.next;
        tail.next = node;
        node.next = head;
        tail = tail.next;
        size++;
    }
    
    public Node<E> removeFirst() {
        
        if(tail == null) return null;
        
        Node<E> head = tail.next;
        
        tail.next = head.next;
        
        if(head == tail) tail = null;
 
        size--;
        
        return head;
    }
    
}
