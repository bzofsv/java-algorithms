/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms;

import java.util.Comparator;
import java.util.LinkedList;
/**
 *
 * @author wang
 */
public class MyPriorityQueue {
    
    public class PQList<E> {
        
        public class PQEntry<E> {
            int k;
            E e;
            PQEntry<E> prev;
            PQEntry<E> next;
            
            public PQEntry(int k, E e, PQEntry<E> prev, PQEntry<E> next) {
                this.k = k;
                this.e = e;
                this.prev = prev;
                this.next = next;
            }
            
            public PQEntry(int k, E e) {
                this(k, e, null, null);
            }
        }
        
        LinkedList<PQEntry> l = new LinkedList<>();
        
        public int size() {
            return l.size();
        }
        
        public boolean isEmpty() {
            if(removeMin() == null) return true;
            return false;
        }
        
        public PQEntry removeMin() {
            PQEntry min = min();
            min.prev.next = min.next;
            min.next.prev = min.prev;
            return min;
        }
        
        public PQEntry min() {
            
            PQEntry<E> min = l.get(0);
            PQEntry<E> curr = min;
            while(curr != null) {
                if(curr.next.k < curr.k) {
                    min = curr.next;
                    curr = curr.next;
                } else {
                    curr = curr.next;
                }
            }
            
            return min;
        }
        
        public void insert(int k, E e) {
            PQEntry<E> pqe = new PQEntry(k, e);
            l.add(pqe);
        }
    }
    
    public class PQHeap<E> {
        
        E []entries;
        int size;
        Comparator<E> comp;
        
        /*public PQHeap(E []entries) {
            this.entries = entries;
            this.size = entries.length;
        }
        
        public PQHeap(int cap) {
            this.entries = (E []) new Object[cap];
            this.size = 0;
        }*/
        
        public PQHeap(int cap, Comparator<E> comp) {
            this.entries = (E []) new Object[cap];
            this.size = 0;
            this.comp = comp;
        }
        
        public PQHeap(Comparator<E> comp, E[] entries) {
            this.comp = comp;
            this.entries = entries;
            this.size = entries.length;
            heapify();
        }
        
        public void swap(E e1, E e2) {
            E temp = e1;
            e1 = e2;
            e2 = temp;
        }
        
        public void bubbleUp(int last) {
            
            int curr = last;
            
            while(curr >= 1) {
                int com = this.comp.compare((E)entries[curr], (E)entries[(int) Math.floor((curr - 1)/2)]);
                if(com < 0) {
                    swap(entries[curr], entries[(int) Math.floor((curr - 1) / 2)]);
                    curr--;
                } else if(com == 0) {
                    curr--;
                } else {
                    curr--;
                }
            }
        }
        
        public void bubbleDown(int root) {
            
            int curr = root;
            while(curr <= size - 3) {
                int comp = this.comp.compare((E)entries[curr], (E)entries[(int) 2 * curr + 1]);
                int comp2 = this.comp.compare((E)entries[curr], (E)entries[(int) 2 * curr + 2]);
                if(comp < 0) {
                    if(comp2 < 0) {
                        int comp3 = this.comp.compare((E)entries[(int) 2 * curr + 1], (E)entries[(int) 2 * curr + 2]); 
                        if(comp3 < 0) {
                            swap(entries[(int) 2 * curr + 1], entries[curr]);
                        } else {
                            swap(entries[(int) 2 * curr + 2], entries[curr]);
                        }
                    } else {
                        swap(entries[(int) 2 * curr + 1], entries[curr]);
                    }
                }
                curr++;
            }
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int size() {
            return this.size;
        }
        
        public void insert(E e) {
            // TODO check if enuff space exists
            entries[size++] = e;
            bubbleUp(size - 1);
        }   
        
        public E min() {
            return entries[0];
        }
        
        public E removeMin() {
            E min = min();
            entries[0] = entries[size-- - 1];
            bubbleDown(0);
            return min;
        }

        public void heapify() {
            for(int i = size - 1; i >= 0; i--) {
                bubbleDown(i);
            }
        }
 
        // heapsort
        public LinkedList<E> heapSort() {
            
            LinkedList<E> r = new LinkedList<>();
            for(int i = 0; i < size; i++) {
                r.add(this.removeMin());
            }
            
            return r;
        }
        
        public E[] heapSort2() {
            
            int sz = size;
            while(size > 0) {
                swap(entries[size-- - 1], entries[0]);
                bubbleDown(0);
            } 
            
            size = sz;
            for(int i = 0; i < size/2; i++) {
                swap(entries[size - 1 - i], entries[i]);
            }
            
            return entries;
        }
    }
}