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
public class MyAVL<E> {

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

        public Node() {
        }
    }

    Node<E> root;

    public MyAVL(Node<E> root) {
        this.root = root;
    }

    public Collection<Node<E>> dfs() {

        Collection<Node<E>> dfs = new ArrayList<>();
        Stack<Node<E>> s = new Stack<>();

        s.add(root);

        while (!s.isEmpty()) {
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

        while (!q.isEmpty()) {
            Node<E> node = q.remove();
            bfs.add(node);
            q.add(node.l);
            q.add(node.r);
        }

        return bfs;
    }

    public Collection<Node<E>> preorder(Node<E> node) {
        Collection<Node<E>> pre = new ArrayList<>();
        return preorder(this.root, pre);
    }

    private Collection<Node<E>> preorder(Node<E> node, Collection<Node<E>> pre) {
        pre.add(node);
        preorder(node.l, pre);
        preorder(node.r, pre);
        return pre;
    }

    public Collection<Node<E>> inorder(Node<E> node) {
        Collection<Node<E>> in = new ArrayList<>();
        return inorder(this.root, in);
    }

    private Collection<Node<E>> inorder(Node<E> node, Collection<Node<E>> in) {
        inorder(node.l, in);
        in.add(node);
        inorder(node.r, in);
        return in;
    }

    public Collection<Node<E>> postorder(Node<E> node) {
        Collection<Node<E>> post = new ArrayList<>();
        return postorder(this.root, post);
    }

    private Collection<Node<E>> postorder(Node<E> node, Collection<Node<E>> post) {
        postorder(node.l, post);
        postorder(node.r, post);
        post.add(node);
        return post;
    }

    public E get(int k) {
        return get(this.root, k);
    }

    private E get(Node<E> p, int k) {
        if (p == null) {
            return null;
        }
        if (p.k == k) {
            return p.e;
        }
        if (p.k > k) {
            return get(p.l, k);
        } else {
            return get(p.r, k);
        }
    }

    public void put(int k, E e) {
        Node<E> add = new Node(k, e);
        put(add, this.root);
    }

    private void put(Node<E> add, Node<E> node) {

        if (add.k == node.k) {
            node.e = add.e;
            return;
        }

        if (add.k < node.k) {

            if (node.l == null) {
                node.l = add;
                add.p = node;
                Node<E> curr = node;
                Node<E> sibling;

                while (curr.p != null) {

                    if (curr.p.l == curr) {
                        sibling = curr.p.r;
                    } else {
                        sibling = curr.p.l;
                    }

                    if (sibling == null) {
                        curr.p.h++;
                    } else if (sibling.h < curr.h + 1) {
                        curr.p.h++;
                    }
                    curr = curr.p;
                }

                restructure(node);

            } else {
                put(add, node.l);
                return;
            }

        } else if (add.k > node.k) {

            if (node.r == null) {
                node.r = add;
                add.p = node;
                Node<E> curr = node;
                Node<E> sibling;
                while (curr.p != null) {

                    if (curr.p.l == curr) {
                        sibling = curr.p.r;
                    } else {
                        sibling = curr.p.l;
                    }

                    if (sibling == null) {
                        curr.p.h++;
                    } else if (sibling.h < curr.h + 1) {
                        curr.p.h++;
                    }
                    curr = curr.p;
                }

                restructure(node);
            } else {
                put(add, node.r);
                return;
            }
        }
    }

    public E rem(int k) {
        return rem(k, this.root);
    }

    private E rem(int k, Node<E> node) {

        if (node == null) {
            return null;
        }

        if (k == node.k) {

            if (node.r == null) {

                if (node.p.l == node) {
                    node.p.l = node.l;
                    node.p.h--;
                } else {
                    node.p.r = node.l;
                    node.p.h--;
                }

                Node<E> curr = node.p;
                Node<E> sibling;

                while (curr.p != null) {

                    if (curr.p.l == curr) {
                        sibling = curr.p.r;
                    } else {
                        sibling = curr.p.l;
                    }

                    if (sibling == null) {
                        curr.p.h--;
                    } else if (sibling.h < curr.h) {
                        curr.p.h--;
                    }
                    curr = curr.p;
                }

                restructure(node.p);

                return node.e;
            } else {
                // node.r != null
                Node<E> rep = node.r;
                if(rep.l == null) {
                    rep.p.r = rep.r;
                } else {
                    while (rep.l != null) {
                        rep = rep.l;
                    }
                    
                    rep.p.l = rep.r;
                }
                
                Node<E> repp = rep.p;

                rep.p = node.p;
                rep.l = node.l;
                rep.r = node.r;
                rep.h = node.h;

                Node<E> curr = repp;
                Node<E> sibling;

                while (curr.p != null) {

                    if (curr.p.l == curr) {
                        sibling = curr.p.r;
                    } else {
                        sibling = curr.p.l;
                    }

                    if (sibling == null) {
                        curr.p.h--;
                    } else if (sibling.h < curr.h + 1) {
                        curr.p.h--;
                    }
                    curr = curr.p;
                }

                restructure(node);

                return node.e;
            }

        } else if (k < node.k) {
            return rem(k, node.l);
        } else if (k > node.k) {
            return rem(k, node.r);
        }

        return null;
    }

    private Node<E> tallerChild(Node<E> node) {
        if (node.l.h > node.r.h) {
            return node.l;
        }
        return node.r;
    }

    private void restructure(Node<E> node) {
        Node<E> curr = node;
        Node<E> z = new Node();

        while (curr != null) {
            Node<E> sibling = curr == curr.p.l ? curr.p.r : curr.p.l;
            if (Math.abs(node.h - sibling.h) > 1) {
                z = node.p;
                break;
            }
            curr = curr.p;
        }

        Node<E> y = tallerChild(z);
        Node<E> x = tallerChild(y);

        rotate(x, y, z);
    }

    private void rotate(Node<E> x, Node<E> y, Node<E> z) {

        if (z.l == y) {
            if (y.l == x) {

                z.l = y.r;
                y.r = z;
                y.h++;
                x.h++;
                z.h--;

            } else {

                y.r = x.l;
                z.l = x.r;
                x.l = y;
                x.r = z;
                x.h++;
                y.h--;
                z.h -= 2;

            }
        } else if (z.r == y) {
            if (y.r == x) {

                z.r = y.l;
                y.l = z;
                y.h++;
                x.h++;
                z.h--;

            } else {

                y.l = x.r;
                z.r = x.l;
                x.r = y;
                x.l = z;
                x.h++;
                y.h--;
                z.h -= 2;

            }
        }
    }
}
