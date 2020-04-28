/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author wang
 */
public class MySet<E> {
    
    LinkedList<E> data;
    int cap;
    int size;
    Comparator<E> c;
    
    public MySet(LinkedList<E> data, Comparator<E> c) {
        this.data = data;
        this.cap = data.size();
        this.size = 0;
        this.c = c;
    }
    
    public void add(E e) {
        data.add(e);
        size++;
        data.sort(c);
    }
    
    public void remove(E e) {
        data.remove(e);
        size--;
        data.sort(c);
    }
    
    public boolean contains(E e) {
        return data.contains(e);
    }
    
    public Iterator<E> iterator() {
        return this.data.iterator();
    }
    
    public void addAll(MySet<E> t) {
        for(E e : t.data) { 
            if(data.contains(e)) continue;
            this.data.add(e);
            this.size++;
        }
        
        data.sort(c);
    }
    
    public LinkedList<E> retainAll(MySet<E> T) {
        for(E e : this.data) {
            if(!T.data.contains(e)) {
                this.remove(e);
                size--;
            }
        }
        data.sort(c);
        
        return data;
    }
    
    public LinkedList<E> removeAll(MySet<E> T) {
        for(E e : this.data) {
            if(T.data.contains(e)) {
                this.remove(e);
                size--;
            }
        }
               
        data.sort(c);
        
        return data;
    }
}
