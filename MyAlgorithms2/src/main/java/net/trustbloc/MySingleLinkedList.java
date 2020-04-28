package net.trustbloc;

public class MySingleLinkedList<E> {
    
    public class Node<E> {
        
         E e;
         Node next;
         
         public Node(E e, Node next) {
             this.e = e;
             this.next = next;
         }
         
         public Node(E e) {
             this(e, null);
         }
    }
    
    int size;
    Node head;
    
    
    public void addFirst(E e) {
        
        Node<E> node = new Node<>(e);
        
        if(head == null) head = node;
        else {
            node.next = head;
            head = node;
        }
        size++;
    }
    
    public void addLast(E e) {
        
        Node<E> node = new Node<>(e);
        
        if(head == null) {
            head = node;
        } else {
            Node<E> tail = head;
            while(tail.next != null) {
                tail = tail.next;
            }
            tail.next = node;
        }
        size++;
    }
    
    public Node<E> removeFirst() {
        if(head == null) return null;
        
        Node<E> ret = head;
        head = head.next;
        size--;
        
        return ret;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public int size() {
        return size;
    }
    
    public Node<E> first() {
        return head;
    }
    
    public Node<E> last() {
        
        if(head == null) return null;
        
        Node<E> tail = head;
        while(tail.next != null) {
            tail = tail.next;
        }
        
        return tail;
    }
}
