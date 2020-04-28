package net.trustbloc.algorithms;

public class MyBST<E> {

    public static class Node<E> {
        Node p;
        Node l;
        Node r;
        int k;
        E e;

        public Node(Node p, Node l, Node r, int k, E e) {
            this.p = p;
            this.l = l;
            this.r = r;
            this.k = k;
            this.e = e;
        }
    }
    Node<E> root;
    public Node search(Node node, int k) {
        if(node == null) return null;
        if(node.k == k) return node;

        if(node.k > k) {
            return search(node.l, k);
        }
        if(node.k < k) {
            return search(node.r, k);
        }

        return null;
    }
    public void insert(Node node, int k, E e) {
        if(node == null) return;
        if(node.k == k) {
            node.e = e;
            return;
        }
        if(node.k > k) {
            if(node.l == null) {
                Node n = new Node(node, null, null, k, e);
                node.l = n;
                return;
            }
            insert(node.l, k, e);
        }
        if(node.k < k) {
            if(node.r == null) {
                Node n = new Node(node, null, null, k, e);
                node.r = n;
                return;
            }
            insert(node.r, k, e);
        }
    }
    public Node remove(Node node, int k) {
        if(node == null) return null;
        if(node.k == k) {
            Node rnode = node.r;
            while(rnode.l != null) {
                rnode = rnode.l;
            }
            Node temp = node;
            rnode.p = node.p;
            if(node.k < node.p.k) {
                rnode.p.l = rnode;
            } else {
                rnode.p.r = rnode;
            }
            rnode.l = node.l;
            rnode.r = node.r;
            return temp;
        }
        if(node.k > k) {
            return remove(node.l, k);
        }
        if(node.k < k) {
            return remove(node.r, k);
        }

        return null;
    }
}
