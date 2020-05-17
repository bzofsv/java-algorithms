/*
 * bzofsv 
 */
package net.trustbloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author bz
 */
public class MyHashMap<E> {
    
    public class Entry<E> {
        
        String k;
        E e;
        
        public Entry(String k, E e) {
            this.k = k;
            this.e = e;
        }
    }
    
    Entry []data;
    int size = 0;
    int a; // mulitply
    int b; // add
    int p; // divide
    Entry DEFUNCT = new Entry(null, null);
    
    public MyHashMap(int cap) {
        this.data = new Entry[cap];
        Random rand = new Random();
        this.a = rand.nextInt();
        this.b = rand.nextInt();
        this.p = (int) Math.pow(2, rand.nextInt(b)) - 1;
    }
    
    private int hash(String k) {
        
        // hash code
        int h = k.hashCode();
        h = h >>> 27 | h << 5;
        
        //compression
        int i = ((this.a * h + this.b) % this.p) % this.data.length;
        
        return i;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public Collection<String> keySet() {
        
        Collection<String> keys = new ArrayList<>();
        
        for(Entry e : this.data) {
            if(e == null || e == this.DEFUNCT) continue;
            keys.add(e.k);
        }
        
        return keys;
    }
    
    public Collection<E> values() {
        
        Collection<E> values = new ArrayList<>();
        
        for(Entry e : this.data) {
            if(e == null || e == this.DEFUNCT) continue;
            values.add((E) e.e);
        }
        
        return values;
    }
    
    public Collection<Entry> entrySet() {
        
        Collection<Entry> entries = new ArrayList<>();
        
        for(Entry e : this.data) {
            if(e == null || e == this.DEFUNCT) continue;
            entries.add(e);
        }
        
        return entries;
    }
    
    public E get(String k) {
        
        int i = this.hash(k);
        
        while(i < data.length && data[i] != null) { 
            if(data[i].k.equals(k)) return (E) data[i].e;
            else i = (int) (i + (Math.pow(++i, 2)));
        }
        
        return null;
    }
    
    public void put(String k, E e) throws Exception{
        
        int i = this.hash(k);
        // guarranteed inbound
        while(i < data.length && data[i] != null) {
            i = (int) (i + (Math.pow(++i, 2))); 
        }
        if(i >= data.length) throw new Exception("Out of capacity");

        data[i] = new Entry(k, e);
        size++;
    }
    
    public E remove(String k) {
        
        int i = this.hash(k);
        
        while(i < data.length && data[i] != null) {
            if(data[i].k.equals(k)) {
                E e = (E) data[i].e;
                data[i] = this.DEFUNCT;
                size--;
                return e;
            } else i = (int) (i + (Math.pow(++i, 2)));
        }
        
        return null;
    }
}
