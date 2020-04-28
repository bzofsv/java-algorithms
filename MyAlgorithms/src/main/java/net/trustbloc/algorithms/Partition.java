package net.trustbloc.algorithms;

public class Partition<E> {
    public static class Node<E> {
        Node p; // parent
        E e;
        int size;

        public Node(Node p, E e) {
            this.p = p;
            this.e = e;
        }

        public Node(E e) {
            this.p = this;
            this.e = e;
        }
    }

    public Node makeCluster(E e) {
        return new Node<E> (e);
    }

    public Node find(Node c) {
        if(c.p == c) return c;
        return find(c.p);
    }

    public Node union(Node c1, Node c2) {
        Node p1 = find(c1);
        Node p2 = find(c2);

        if(p1 == p2) return p1;
        if(p1.size > p2.size) {
            p2.p = p1;
            p1.size += p2.size;
            return p1;
        }
        p1.p = p2;
        p2.size += p1.size;
        return p2;
    }
}
