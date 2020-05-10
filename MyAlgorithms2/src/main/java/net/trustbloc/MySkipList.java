/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author bz
 */
public class MySkipList<E> {
    
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
    
    Node<E> s;
    int cap;
    int h = 10;
    
    public MySkipList(int size) {
        
        this.h = Math.max((int) (3 * Math.ceil(Math.log(size))), 10);
        
        Node pl = null; // previous left
        Node pr = null; // previous right
        
        for(int i = 0; i < h; i++) {
            
            Node ls = new Node(Integer.MIN_VALUE, null); // left sentinel
            Node rs = new Node(Integer.MAX_VALUE, null); // right sentinel
    
            ls.r = rs;
            rs.l = ls;
            
            if(i == 0) {
                s = ls;
                continue;
            }
            pl = ls;
            pr = rs;
        }
    }
    
    public E get(int k) {
        
        Node<E> curr = s;
        
        while(curr.b != null) {
            
            curr = curr.b;
            
            while(curr.k <= k) {
                curr = curr.r;
            }
            
            curr = curr.l; // <= k
            
            if (curr.k == k) return curr.e;
        }
        
        if (curr.k == k) return curr.e;        
        return null;
    }
    
    public void insert(int k, E e) {
        
        Node<E> node = new Node(k, e);
        ArrayList<Node<E>> pos = new ArrayList<>();
        
        Node<E> curr = s;
        
        while(curr.b != null) {
            
            pos.add(curr);
            curr = curr.b;
            
            while(curr.k <= k) {
                curr = curr.r;
            }
            
            curr = curr.l;
        }
        
        //curr.k <= k @ bottom
        
        if(curr.k == k) {            
            while(curr != null) {
                curr.e = e;
                curr = curr.a;
            }
            return;
        }
        
        int nh = 0; // node height @ bottom
        
        Random ran = new Random();
        
        for(int i = 0; i < h; i++) {
            
            Node<E> prev = pos.get(h - i); //TOOD get last
           
            node.l = prev;
            node.r = prev.r;
            prev.r.l = node;
            prev.r = node;
            
            if(!ran.nextBoolean()) break;
        }    
    }
    
    public E remove(int k) {
        
        Node<E> curr = s;
        
        while(curr.b != null) {
      
            curr = curr.b;
            
            while(curr.k <= k) {
                curr = curr.r;
            }
            
            curr = curr.l;
        }
        
        if(curr.k != k) return null;
        
        while(curr != null) {
            curr.l.r = curr.r;
            curr.r.l = curr.l;
            curr = curr.a;
        }
        
        return curr.e;
    }
}
