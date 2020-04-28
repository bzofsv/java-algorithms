/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms;

import java.util.Random;

/**
 *
 * @author wang
 */
public class MySkipList2<E> {
    
    public class Node<E> {
        
        int k;
        E e;
        Node l;
        Node r;
        Node a;
        Node b;
        
        public Node(int k, E e, Node l, Node r, Node a, Node b) {
            this.k = k;
            this.e = e;
            this.l = l;
            this.r = r;
            this.a = a;
            this.b = b;
        }
        
        public Node(int k, E e) {
            this(k, e, null, null, null, null);
        }
    }
    
    int h = 10;
    
    // leftmost @ level h
    Node s = null;
    
    public MySkipList2(int cap) {
        
        h = (int) Math.max((double) h, Math.log(cap));
        
        Node al = null;
        Node ar = null;
        
        for (int i = 0; i < h; i++){
            Node l = new Node(Integer.MIN_VALUE,null);
            Node r = new Node(Integer.MAX_VALUE,null);
            l.r = r;
            r.l = l;
            if (i == 0) s = l;
            else {
                l.a = al;
                r.a = ar;
                al.b = l;
                ar.b = r;
            }
            al = l;
            ar = r;
        }
    }
    
    public E search(int k) {
        
        Node<E> cur = s;
        do {
            cur = cur.b;
            
            while(cur.k < k) {
                cur = cur.r;
            }
            cur = cur.l;
            
        } while(cur.b != null);
        
        do {
            cur = cur.r;
        } while(cur.k < k);
        
        cur = cur.l;
        
        if(cur.k == k) return cur.e; 
        
        return null;
    }
    
    public void insert(int k, E e) {
        
        Node<E> cur = s;
        do {
            cur = cur.b;
            
            while(cur.k < k) {
                cur = cur.r;
            }
            cur = cur.l;
            
        } while(cur.b != null);
        
        do {
            cur = cur.r;
        } while(cur.k < k);
        
        cur = cur.l;
        
        if(cur.k == k) {
            while(cur != null) {
                cur.e = e;
                cur = cur.a;
            }
            
            return; 
        } 
        
        Node<E> node = new Node(k, e);
        node.l = cur;
        node.r = cur.r;
        cur.r.l = node;
        cur.r = node;
        
        for (int th =  h - 2; th > 0; th--) {
            
            if (!new Random().nextBoolean()) break;
            
            Node<E> anode = new Node(k, e);
            
            node.a = anode;
            anode.b = node;
            
            node = anode;
        }
    }
    
    public void delete(int k) {
        
        Node<E> cur = s;
        do {
            cur = cur.b;
            
            while(cur.k < k) {
                cur = cur.r;
            }
            cur = cur.l;
            
        } while(cur.b != null);
        
        do {
            cur = cur.r;
        } while(cur.k < k);
        
        cur = cur.l;
        
        if(cur.k != k) return;
        
        while(cur != null) {
            cur.l.r = cur.r;
            cur.r.l = cur.l;
            cur = cur.a;
        }
    }
}
