package net.trustbloc.algorithms;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Daddy is the best!");
    }
}

class BST {
    public static class Node{
        int k;
        Object v;
        Node p, l, r; //parent, left child, right child
        int d; //depth
    }
    Node root;
    //TODO constructure
    void restructure (Node x) {
        Node y = x.p;
        Node z = y.p;
        if (z == null) return;

        if (z.r == y && y.r == x) {
            Node zp = z.p;
            Node t2 = y.l;
            z.r = t2;
            y.l = z; z.p = y;
            t2.p = z;
            y.p = zp;
            x.d--; y.d--; z.d++;
        } else if (z.l == y && y.l == x) {
            Node zp = z.p;
            Node t3 = y.r;
            z.l = t3;
            y.r = z; z.p = y;
            t3.p = z;
            y.p = zp;
            x.d--; y.d--; z.d++;
        } else if (z.r == y && y.l == x) {
            Node zp = z.p;
            Node t2 = x.l;
            Node t3 = x.r;
            y.l = t3;
            t3.p = y;
            z.r = t2;
            t2.p = z;
            x.r = y;
            x.l = z;
            y.p = x;
            z.p = x;
            x.p = zp;
            x.d -= 2;
            z.d++;
        } else if (z.l == y && y.r ==x) {
            Node zp = z.p;
            Node t2 = x.l;
            Node t3 = x.r;
            x.l = y;
            x.r = z;
            x.p = zp;
            y.r = t2;
            y.p = x;
            t2.p = y;
            z.l = t3;
            t3.p = z;
            z.p = x;
            x.d -= 2;
            z.d++;
        }
    }

}
