/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author bz
 */
public class MyBST<E> {
    
    public class Node<E> {
        int k;
        E e;
        Node<E> l;
        Node<E> r;
        Node<E> p;
        int h;
        
        public Node(int k, E e, Node<E> l, Node<E> r, Node<E> p, int h) {
            this.k = k;
            this.e = e;
            this.l = l;
            this.r = r;
            this.p = p;
            this.h = h;
        }
        
        public Node(int k, E e) {
            this(k, e, null, null, null, 1);
        }
    }
    
    Node<E> root;
    int height;
    int depth;
    
    public MyBST(Node<E> root, int height, int depth) {
        this.root = root;
        this.height = height;
        this.depth = depth;
    }
    
    public Collection<Node<E>> dfs() {
        
        Collection<Node<E>> dfs = new ArrayList<>();
        Stack<Node<E>> s = new Stack<>();
        
        s.add(root);
        
        while(!s.isEmpty()) {
            Node<E> node = s.pop();
            dfs.add(node);
            s.add(node.l);
            s.add(node.r);
        }
        
        return dfs;
    }
    
    public Collection<Node<E>> bfs() {
        
        Collection<Node<E>> bfs = new ArrayList<>();
        Queue<Node<E>> q = new LinkedList<>();
        
        q.add(root);
        
        while(!q.isEmpty()) {
            Node<E> node = q.remove();
            bfs.add(node);
            q.add(node.l);
            q.add(node.r);
        }
        
        return bfs;
    }
    
    public Collection<Node<E>> preorder(Node<E> node) {
        Collection<Node<E>> pre = new ArrayList<>();
        pre.add(node);
        preorder(node.l);
        preorder(node.r);
        return pre;
    }
    
    
    public Collection<Node<E>> inorder(Node<E> node) {
        Collection<Node<E>> in = new ArrayList<>();
        preorder(node.l);
        in.add(node);
        preorder(node.r);
        return in;
    }
    
    public Collection<Node<E>> postorder(Node<E> node) {
        Collection<Node<E>> post = new ArrayList<>();
        preorder(node.l);
        preorder(node.r);
        post.add(node);
        return post;
    }
    
    public E get(int k) {
        return get(this.root, k);
    }
    
    private E get(Node<E> p, int k) {
        if(p == null) return null;
        if(p.k == k) return p.e;
        if(p.k > k) return get(p.l, k);
        else return get(p.r, k);
    }
    
    public void put(int k, E e) {
        Node<E> add = new Node(k, e);
        put(add, this.root);
    }
    
    private void put(Node<E> add, Node<E> node) {
        
        if(add.k == node.k) {
            node.e = add.e;
            return;
        }  
        
        if(add.k < node.k) {
            
            if(node.l == null) {
                node.l = add;
                add.p = node;
                Node<E> curr = node;
                Node<E> sibling;
                
                while(curr.p != null) {
                    
                    if(curr.p.l == curr) sibling = curr.p.r;
                    else sibling = curr.p.l;
                    
                    if(sibling == null) curr.p.h++;
                    else if(sibling.h < curr.h) curr.p.h++;
                    curr = curr.p;
                }
            } else {
                put(add, node.l);
            }
            
        } else if(add.k > node.k) {
            
            if(node.r == null) {
                node.r = add;
                add.p = node;
                Node<E> curr = node;
                Node<E> sibling;
                while(curr.p != null) {
                    
                    if(curr.p.l == curr) sibling = curr.p.r;
                    else sibling = curr.p.l;
                   
                    if(sibling == null) curr.p.h++;
                    else if(sibling.h < curr.h) curr.p.h++;
                    curr = curr.p;
                }
            } else {
                put(add, node.r);
            }
        }
    }
    
    public E rem(int k) {
        return rem(k, this.root);
    }
    
    private E rem(int k, Node<E> node) {
        
        if(node == null) return null;
        
        if(k == node.k) {
            
            Node<E> rep = node.r; 
            
            if(rep == null) {
                
                if(node.p.l == node) {
                    node.p.l = node.l;
                    node.p.h--;
                } else {
                    node.p.r = node.l;
                    node.p.h--;
                }
                
                Node<E> curr = node.p;
                Node<E> sibling;
                
                while(curr.p != null) {
                    
                    if(curr.p.l == curr) sibling = curr.p.r;
                    else sibling = curr.p.l;
                    
                    if(sibling == null) curr.p.h--;
                    else if(sibling.h < curr.h) curr.p.h--;
                    curr = curr.p;
                }
                        
                return node.e;
            }
            
            while(rep.l != null) rep = rep.l;
           
            if(rep.p.l == rep) rep.p.l = rep.r;
            else rep.p.r = rep.r;
            
            Node<E> repp = rep.p;
            
            rep.p = node.p;
            rep.l = node.l;
            rep.r = node.r;
            rep.h = node.h;
            
            Node<E> curr = repp;
            Node<E> sibling;
                
            while(curr.p != null) {
                
                    if(curr.p.l == curr) sibling = curr.p.r;
                    else sibling = curr.p.l;
                    
                if(sibling == null) curr.p.h--;
                else if(sibling.h < curr.h) curr.p.h--;
                curr = curr.p;
            }
            
            return node.e;
            
        } else if(k < node.k) {
            if(node.l == null) return null;
            
            return rem(k, node.l);
        } else if(k > node.k) {
            if(node.r == null) return null;
            
            return rem(k, node.r);
        }
        
        return null;
    }   
}
