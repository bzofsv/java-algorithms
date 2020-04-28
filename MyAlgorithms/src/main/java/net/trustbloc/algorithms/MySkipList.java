package net.trustbloc.algorithms;

import java.util.Random;

public class MySkipList {

    public static class Node {
        int k; //key is an integer
        Object v; //Value is an object
        Node prev, next, above, below; // Node relations

        public Node(int k, Object v, Node prev, Node next, Node above, Node below) { //constructor for Node class
            this.k = k;
            this.v = v;
            this.prev = prev;
            this.next = next;
            this.above = above;
            this.below = below;
        }

        public Node(int k, Object v) {
            this(k, v, null, null, null, null); } // other constructor with only key and value
    }

    int h = 10;  // log(n)

    Node start; // beginning Node, last row, leftmost.

    public MySkipList (int cap) { //Constructor for MySkipList, height is log of provided capacity

        h = (int) Math.log(cap);
        if (h < 10) h = 10; // height is minimum 10

        // init the highest level, i.e. level h
        this.start = new Node(Integer.MIN_VALUE, null); // leftmost, start of get, remove, and insert functions
        this.start.next = new Node(Integer.MAX_VALUE, null); //39 and 40 point arrows to - and + infinity
        this.start.next.prev = this.start; //highest level is only two sentinels, - and + infinity

        Node currleft = this.start; //the leftmost node in level h
        Node currright = this.start.next; //the rightmost node in level h

        for (int i = h - 1; i > 0; i--) { // for loop with counter to create our Skip List with height h
            Node node = new Node(Integer.MIN_VALUE, null); // node is currently the left sentinel
            node.next = new Node(Integer.MAX_VALUE, null); // node is currently the right sentinel
            node.next.prev = node; // pointing the arrows of the node next to our current node to the current node
            node.above = currleft; // set the above node for level i to be the leftmost in level i+1
            node.next.above = currright; // set the above of the next node to be the rightmost in i+1
            currleft = node; // prepare for next iteration by setting left as a node in level i
            currright = node.next; // prepare by setting right as a node in i
        }
    }

    Node get(int k) { //get the Node based on key

        Node curr = this.start; //node that we are currently addressing

        while (curr.below != null) { //repeat while current node is not on level 0
            Node lnode = curr.below; // node in next level
            while(lnode != null && lnode.k < k) { //repeat while current node is not on level 0 and we haven't passed Node with key k
                if (lnode.k == Integer.MAX_VALUE) break; // if we reach the right side and key k is not found, leave the inner loop
                lnode = lnode.next; //move to the next node;
            }
            if (lnode.k == k) { //if we find the wanted key, return the node associated with it
                return lnode;
            }
            if(lnode.k > k) lnode = lnode.prev; //if we have passed the requested key, move back
            while (lnode.below == null) lnode = lnode.prev; // if on bottom row and key not found, move back until up is possible
            curr = lnode; //move down
        }

        return null; // still not found, return null
    }

    void put(int k, Object v) { // add Node column with key k and value v

        Node curr = this.start; //start from the start

        Node found = null; //variable to place found Node in, if found

        while (curr.below != null) { //repeat while the the current is not on the last row
            Node lnode = curr.below; // Node to access the below row
            while(lnode != null && lnode.k < k) { // repeat while the current is not on the last row and we have not passed the requested key
                if (lnode.k == Integer.MAX_VALUE) break; //reach the end, key not found, break
                lnode = lnode.next; //move forward
            }
            // now lnode is either is k or greater, or at POS_INF

            if (lnode.k == k) { // key is found
                found = lnode; // not necessarily at S0
                break;
            }
            if(lnode.k > k) { //key is passed
                lnode = lnode.prev; // go back once
                if (lnode.below ==  null) { // at the lowest level
                    curr = lnode; // could not find it, add after curr
                    break;
                }
            }

            while (lnode.below == null) lnode = lnode.prev; //if lnode is on S0, go back
            curr = lnode; //move curr down
        }
        // either found the position(k), or just at the position before k
        if (found != null) { //found position
            //replace found
            while (found != null) {
                found.v = v; // value of position to be inserted replaces found value
                found = found.below; //move down the column since found position will always be the highest level in the column
            }
        } else { //if not found, or just at the position
            // add after curr

            for (int i = 0; i < h; i++) { //i is the level of the inserted Node, i must be less than the height
                //set pointers to point to new Node
                Node node = new Node(k, v); //create new Node to insert
                Node next = curr.next;
                curr.next = node;
                node.prev = curr;
                node.next = next;
                next.prev = node;

                if (!new Random().nextBoolean()) break; //coin-flip to determine continuation, if false, leave loop and stop growth

                while (curr.above == null) curr = curr.prev; //find higher layer

                curr = curr.above; //move to higher layers
            }
        }
    }

    Object rem(int k) { // remove Node at key k, return value

        Node curr = this.start; //start searching for k at leftmost on first row

        Node found = null; //store found Node

        while (curr.below != null) { //repeat while current is not in S0
            Node lnode = curr.below; // lnode used to access row below current row
            while(lnode != null && lnode.k < k) { //while current is not in S0, and wanted key is not passed
                if (lnode.k == Integer.MAX_VALUE) break; //if we reach the end of the row, key not found, break
                lnode = lnode.next; // move forward
            }
            if (lnode.k == k) { //found
                found = lnode; // not necessarily at S0
                break;
            }
            if(lnode.k > k) { //passed by one
                lnode = lnode.prev; //go back
                if (lnode.below ==  null) { //lnode is on the last row
                    curr = lnode; // could not find it
                    return null; //cannot find
                }
            }

            while (lnode.below == null) lnode = lnode.prev; //repeat while lnode is still on last row
            curr = lnode; //move curr down
        }

        if (found != null) { //found

            while(true) {
            //change pointers to remove found
                Node prev = found.prev;
                Node next = found.next;

                prev.next = next;
                next.prev = prev;

                if (found.below == null) return found.v; //if found is on bottom row, return its value

                found = found.below; //go down
            }
        }
        return null;
    }
}
