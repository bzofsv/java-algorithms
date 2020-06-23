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
public class MyHeap<E> {
    public class Node<E> {
        int k;
        E e;
        
        public Node(int k, E e) {
            this.k = k;
            this.e = e;
        }
    }
    
    Node<E>[] nodes;
    int size;
    
    public MyHeap(Node[] nodes, int size) {
        this.nodes = nodes;
        this.size = size;
        this.heapify();
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    private void swap(int i, int j) {
        Node<E> temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }
    
    public E min() {
        return nodes[0].e;
    }
    
    public void insert(int k, E e) {
        if(nodes.length <= size) {
            Node<E>[] data = new Node[nodes.length * 2];
            for(int i = 0; i < nodes.length; i++) {
                data[i] = nodes[i];
            }
            nodes = data;
        }
        
        Node<E> node = new Node(k, e);
        nodes[size] = node;
        size++;
        
        bubbleUp();
    }
    
    private void bubbleUp() {
        if(this.size <= 1) return;
        
        int curr = this.size - 1;
        
        while(nodes[curr] != null) {
            int p = (int) Math.floor((curr - 1) / 2);
            if(nodes[curr].k >= nodes[p].k) {
                return;
            }
             
            swap(curr, p);
            curr = p;
        }
    }
    
    public E remove() {
        
        Node<E> removed = nodes[0];
        
        nodes[0] = nodes[size - 1];
        nodes[size - 1] = null;
        
        bubbleDown(0);
        size--;
        
        return removed.e;
    }
    
    private void bubbleDown(int i) {
        if(this.size <= 1) return;
        
        int curr = i;
        while(nodes[curr] != null) {
            int c;
            if(nodes[2 * curr + 2] == null) c = 2 * curr + 1;
            else {
                c = smallerChild(curr);
                if(c == -1) return;
            }
            
            if(nodes[c].k >= nodes[curr].k) {
                return;
            }
            swap(c, curr);
            
            curr = c;
        }
    }
    
    private int smallerChild(int c) {
        int l = 2 * c + 1;
        int r = 2 * c + 2;
        
        if(l >= nodes.length) {
            return -1;
        } else if(r >= nodes.length) return l;
        
        return nodes[l].k > nodes[r].k ? r : l;
    }
    
    private Node<E>[] heapify() {
        for(int i = (this.size - 1) / 2 ; i >= 0; i--) {
            bubbleDown(i);
        }
        
        return nodes;
    } 
}
