package net.trustbloc;

public class MyHeap<E> {
    
    public class Node<E> {
        int k;
        E e;
        Node<E> l;
        Node<E> p;
        Node<E> r;
        int i;
        
        public Node(int k, E e, Node<E> l, Node<E> p, Node<E> r) {
            this.k = k;
            this.e = e;
            this.l = l;
            this.p = p;
            this.r = r;
        }
        
        public Node(int k, E e) {
            this(k, e, null, null, null);
        }
    }
    
    Node<E> root;
    int size;
    Node []data;
    int cap;
    
    public MyHeap(Node<E> root, int size, Node<E> []data) {
        this.root = root;
        this.size = size;
        this.data = new Node[cap];
    }
    
    private void swap(Node<E> node1, Node<E> node2) {
        Node<E> temp = node1;
        data[node1.i] = node2;
        data[node2.i] = temp;
    }
    
    private void bubbleUp(Node<E> node) {
        
        while(node != null) {
            if(node.k < node.p.k) {
                swap(node, node.p);
            }
            node = node.p;
        }
    }
    
    private void bubbleDown(Node<E> node) {
        
        if(node == null) {
            return;
        }
        
        Node<E> curr = node;
        Node<E> currc;
        while(curr != null) {
            
            if(node.l == null && node.r == null) {
                return;
            }
            
            if(curr.r == null) {
                currc = curr.l;
            } else {
                if(curr.l.k < curr.r.k) currc = curr.l;
                else currc = curr.r;
            }
            
            Node<E> temp = currc;
            
            if(curr.k <= currc.k) return;
            else {
                swap(curr, currc);
            }
            
            curr = temp;
        }
    }
    
    public void insert(int k, E e) {
        
        Node<E> add = new Node(k, e);
        Node<E> last = data[size];
        
        if(last.p.l == last) {
            
            last.p.r = add;
            add.p = last.p;
            data[size++] = add;
            bubbleUp(add);
            return;
        
        } else {
            
            if(last.p.p.l == last.p) {
                last.p.p.r.l = add;
                add.p = last.p.p.r;
            } else {
                last.p.p.l.l = add;
                add.p = last.p.p.l;
            }
            
            Node<E> []ndata = new Node[cap * 2];
            for(int i = 0; i < cap; i++) {
                ndata[i] = data[i];
            }
            data = ndata;
            
            bubbleUp(add);
            
            return;
        }
    }
    
    public E remove() {
        
        Node<E> last = data[size - 1];
        
        Node<E> ret = data[0];
        
        if(last.p.l == last) {
            last.p.l = null;
        } else {
            last.p.r = null;
        }
        
        data[0] = last;
        data[size - 1] = null;
        
        last.r = root.r;
        last.l = root.l;
        root = last;
        
        size--;
        
        bubbleDown(root);
        
        return ret.e; 
    }
    
    public void heapify() {
        
        for(int i = (this.size - 1) / 2; i >= 0; i--) {
            this.bubbleDown(this.data[i]);
        }
    }
}





