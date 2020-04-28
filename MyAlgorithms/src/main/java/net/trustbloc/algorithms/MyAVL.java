package net.trustbloc.algorithms;

public class MyAVL<E> {

    public static class Node<E> {
        Node p;
        Node l;
        Node r;
        int k;
        E e;
        int h;

        public Node(Node p, Node l, Node r, int k, E e, int h) {
            this.p = p;
            this.l = l;
            this.r = r;
            this.k = k;
            this.e = e;
            this.h = h;
        }

        public Node(Node p, Node l, Node r, int k, E e) {
            this(p, l, r, k, e, 1);
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
                rebalance(n.p);
                return;
            }
            insert(node.l, k, e);
        }
        if(node.k < k) {
            if(node.r == null) {
                Node n = new Node(node, null, null, k, e);
                node.r = n;
                rebalance(n.p);
                return;
            }
            insert(node.r, k, e);
        }
    }
    public Node remove(Node node, int k) {
        if(node == null) return null;
        if(node.k == k) {
            Node rpl = node.r;
            while(rpl.l != null) {
                rpl = rpl.l;
            }
            Node del = node;
            Node tmp = rpl.p;
            rpl.p = node.p;
            if(node.k < node.p.k) {
                rpl.p.l = rpl;
            } else {
                rpl.p.r = rpl;
            }
            if(rpl.r != null) {
                rpl.r = tmp.l;
                tmp.l = rpl.r;
            } else {
                tmp.l = null;
            }
            rpl.l = node.l;
            rpl.r = node.r;
            rpl.h = del.h;
            int oh = tmp.h;
            if(tmp.l != null) {
                tmp.h = tmp.l.h + 1;
            }
            if(tmp.r != null) {
                if(tmp.r.h > tmp.h - 1) {
                    tmp.h = tmp.r.h + 1;
                }
            }
            if(tmp.h != oh) {
                rebalance(tmp);
            }
            return del;
        }
        if(node.k > k) {
            return remove(node.l, k);
        }
        if(node.k < k) {
            return remove(node.r, k);
        }

        return null;
    }

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
        } else if (z.l == y && y.l == x) {
            Node zp = z.p;
            Node t3 = y.r;
            z.l = t3;
            y.r = z; z.p = y;
            t3.p = z;
            y.p = zp;
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
            x.h -= 2;
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
        }
        return;
    }

    public boolean isBalanced(Node node) {
        int lh = 0, rh = 0;

        if(node.l != null) {
            lh = node.l.h;
        }
        if(node.r != null) {
            rh = node.r.h;
        }

        return Math.abs(lh - rh) <= 1;
    }

    public Node tallerChild(Node node) {
        if(node.l.h > node.r.h) return node.l;
        return node.r;
    }

    public int recomputeHeight(Node node) {
        int lh = 0, rh = 0;

        if(node.l != null) {
            lh = node.l.h;
        }
        if(node.r != null) {
            rh = node.r.h;
        }

        node.h = 1 + Math.max(lh, rh);
        return node.h;
    }

    public void rebalance(Node node) {
        int oh = node.h;

        do {
            if(!isBalanced(node)) {
                this.restructure(tallerChild(tallerChild(node)));
                this.recomputeHeight(node.l);
                this.recomputeHeight(node.r);
            }
            this.recomputeHeight(node);
            node = node.p;
        } while(oh != node.h && node != null);
    }
}
