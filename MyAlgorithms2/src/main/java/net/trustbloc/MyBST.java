package net.trustbloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
        
        public Node(int k, E e, Node<E> l, Node<E> r, Node<E> p) {
            this(k, e, l, r, p, 1);
        }
        
        public Node(int k, E e, int h) {
            this.k = k;
            this.e = e;
            this.h = h;
        }
        
        public Node(int k, E e) {
            this(k, e, 1);
        }
    }
    
    Node<E> root;
    int h;
    int size;
    
    public MyBST() {}
    
    public Collection<E> dfs() {
        
        Collection<E> ret = new ArrayList<>();
        Stack<Node<E>> s = new Stack<>();
        
        s.add(root);
        
        while(!s.isEmpty()) {
            
            Node<E> node = s.pop();
            ret.add(node.e);
            s.add(node.l);
            s.add(node.r);
        }
        
        return ret;
    }
    
    public Collection<E> bfs() {
        
        Collection<E> ret = new ArrayList<>();
        Queue<Node<E>> q = new LinkedList<>();
        
        q.add(root);
        
        while(!q.isEmpty()) {
            
            Node<E> node = q.remove();
            ret.add(node.e);
            q.add(node.r);
            q.add(node.l);
        }
        
        return ret;
    }
    
    public Collection<E> preorder(Node<E> root) {
        Collection<E> ret = new ArrayList<>();
        preorder(root, ret);
        
        return ret;
    }
    
    private void preorder(Node<E> root, Collection<E> ret) {
        
        if(root == null) return;
        
        ret.add(root.e);
        preorder(root.l, ret);
        preorder(root.r, ret);
    }
    
    public Collection<E> postorder(Node<E> root) {
        Collection<E> ret = new ArrayList<>();
        postorder(root, ret);
        
        return ret;
    }
    
    private void postorder(Node<E> root, Collection<E> ret) {
        
        if(root == null) return;
        
        postorder(root.l, ret);
        postorder(root.r, ret);
        ret.add(root.e);
    }
    
    public Collection<E> inorder(Node<E> root) {
        Collection<E> ret = new ArrayList<>();
        inorder(root, ret);
        
        return ret;
    }
    
    private void inorder(Node<E> node, Collection<E> ret) {
        
        if(node == null) return;
        
        inorder(node.l, ret);
        ret.add(node.e);
        inorder(node.r, ret);
    }
    
    public E get(int k) {
        
        if(root == null) return null;
        
        return get(k, root);
    }
    
    private E get(int k, Node<E> node) {
        
        if(node == null) return null;
        
        if(k == node.k) return node.e;
        
        if(k < node.k) return get(k, node.l);
        
        if(k > node.k) return get(k, node.r);
        
        return null;
    }
    
    public void put(int k, E e) {

        Node<E> add = new Node<>(k, e);

        put(add, root);
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
                    sibling = curr.p.l == curr ? curr.p.r : curr.p.l;
                    if(sibling == null) curr.p.h++;
                    else if(sibling.h < curr.h) curr.p.h++;
                    curr = curr.p;
                }
    
                rebalance(add);
    
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
                    sibling = curr.p.l == curr ? curr.p.r : curr.p.l;
                    if(sibling == null) curr.p.h++;
                    else if(sibling.h < curr.h) curr.p.h++;
                    curr = curr.p;
                }
                
                rebalance(add);
                
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
            
            Node<E> rep = node.r; // rep replaces node after deletion
            
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
                    sibling = curr.p.l == curr ? curr.p.r : curr.p.l;
                    if(sibling == null) curr.p.h--;
                    else if(sibling.h < curr.h) curr.p.h--;
                    curr = curr.p;
                }
                
                rebalance(node.p);
                        
                return node.e;
            }
            
            // rep != null
            
            while(rep.l != null) rep = rep.l; // rep has the smallest key greater than node
           
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
                sibling = curr.p.l == curr ? curr.p.r : curr.p.l;
                if(sibling == null) curr.p.h--;
                else if(sibling.h < curr.h) curr.p.h--;
                curr = curr.p;
            }
                
            rebalance(repp);
            
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
    
    private void rotate(Node<E> z, Node<E> y, Node<E> x) {
        
        if(z.r == y) {
            
            if(y.r == x) {
                
                y.p = z.p;
                
                z.p = y;
                z.r = y.l;
                z.h--; 
                
                y.l = z;
                y.h++;
                
                x.h++;
            
            } else {
                
                x.p = z.p;
                
                z.p = x;
                z.r = x.l;
                z.h -= 2;
                
                y.p = x;
                y.l = x.r;
                y.h--;
                
                x.l = z;
                x.r = y;
                x.h++;
            }
            
        } else {
            
            if(y.l == x) {
                
                y.p = z.p;
                
                z.p = y;
                z.l = y.r;
                z.h--;
                
                y.r = z;
                y.h++;
                
                x.h++;
            
            } else {
                
                x.p = z.p;
                
                z.p = x;
                z.l = x.r;
                z.h -= 2;
                
                y.p = x;
                y.r = x.l;
                y.h--;
                
                x.r = z;
                x.l = y;
                x.h++;
            
            }
        }
    }
    
    private void rebalance(Node<E> node) {
        
        Node<E> sibling;
        if(node.p.l == node) sibling = node.p.r;
        else sibling = node.p.l;
        
        while(Math.abs(node.h - sibling.h) < 2) {
            node = node.p;
        }
        
        Node<E> z = node.p;
        
        Node<E> y = z.l.h > z.r.h ? z.l : z.r;
        
        Node<E> x = y.l.h > y.r.h ? y.l : y.r;
        
        rotate(z, y, x);
    }
}
