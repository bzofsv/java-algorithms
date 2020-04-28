/*
 *
 */
package net.trustbloc.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author wang
 */
public class MyMultiMap<V> {
    
    HashMap<String, ArrayList<V>> multimap = new HashMap<>();
    int size;
    
    public Collection<V> get(String k) {
        return this.multimap.get(k);
    }
    
    public void put(String k, V v) {
        this.multimap.get(k).add(v);
        size++;
    }
    
    public void remove(String k, V v) {
        this.multimap.get(k).remove(v);
        size--;
    }
    
    public void removeAll(String k) {
        ArrayList<V> a = this.multimap.remove(k);
        size -= a.size();
    }
    
    public int size() {
        return size;
    }
    
    public Collection<V> values() {
        
        Collection<V> values = new ArrayList<>();
        for(ArrayList<V> a : multimap.values()) {
            for(V v : a) {
                values.add(v);
            }
        }
        
        return values;
    }
    
    public Collection keys() {
        
        Collection keys = new ArrayList<>();
        for(String k : multimap.keySet()) {
            for(V v : multimap.get(k)) {
                keys.add(k);
            }
        }
        
        return keys;
    }
    
    public Collection keySet() {
        return multimap.keySet();
    }
}
