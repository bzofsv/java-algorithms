/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author wang
 */
public class MyHashTable2<V> {
    
    public class Entry<V> {
        String key;
        V v;
        
        public Entry (String key, V v) {
            this.key = key;
            this.v = v;
        }
       
    }
    
    int cap;
    int size;
    int a;
    int b;
    Entry<V>[] data;
    ArrayList<String> sorted = new ArrayList<>();
    Entry<V> DEFUNCT = new Entry(null, null);
    
    public MyHashTable2(int cap) {
        // TODO make size of array prime
        this.cap = 2 * cap - 1;
        this.size = 0;
        this.a = (int) Math.random() * cap;
        this.b = (int) Math.random() * cap;
        this.data = new Entry[cap];
    }
    
    private class keyComparator implements Comparator<String> {

        @Override
        public int compare(String k1, String k2) {
            return k1.compareTo(k2);
        }
    }
    
    private int hash(String key) {
        
        //hash code
        int h = key.hashCode();
        h = h << 5;
        
        //compression Divide
        //TODO make p prime
        int c; // compressed hash code to be placed in data
        c = ((h * a + b) % (cap + 1) ) % cap;
        
        return c; 
    }
    
    public void put(String k, V v) {
        
        if(cap < size * 2) {
            
            Entry<V>[] ndata = new Entry[cap * 2];
            for(int i = 0; i < cap; i++) {
                ndata[i] = this.data[i];
            }
            this.data = ndata;
            cap *= 2;
        }
        
        int i = hash(k);
        
        while(true) {
            
            if(i == cap) {
                i = 0;
            }
            
            if(data[i] == null || data[i] == DEFUNCT) {
                data[i] = new Entry(k, v);
                size++;
                sorted.add(k);
                sorted.sort(new keyComparator());
                return;
            }

            i++;
        }
    }
    
    public V get(String k) {
        
        int i = hash(k);
        int s = i;
        
        do {
            
            if(i == cap) {
                i = 0;
            }
            
            Entry<V> e = (Entry<V>)data[i];
            
            if(e == null) {
                return null;
            }
        
            if(e.key.compareTo(k) == 0) {
                return data[i].v;
            }
            
            i++;
            
        } while(s != i);
        
        return null;
    }
    
    public V remove(String k) {
        
        int i = hash(k);
        int s = i;
        
        do {
            
            if(i == cap) {
                i = 0;
            }
            
            Entry<V> e = (Entry<V>)data[i];
            
            if(e == null) {
                return null;
            }
        
            if(e.key.compareTo(k) == 0) {
                data[i] = DEFUNCT;
                sorted.remove(i);
                sorted.sort(new keyComparator());
                size--;
                
                return e.v;
            }
            
            i++;
            
        } while(s != i);
        
        return null;
    }
    
    public V first() {
        String k = sorted.get(0);
        return get(k);
    }
    
    public V last() {
        String k = sorted.get(sorted.size() - 1);
        return get(k);
    }
    
    public V ceiling(String k) {
        
        int mid = sorted.size() / 2;
        int pmid = mid;
        do {
            
            int comp = sorted.get(mid).compareTo(k);
            if(comp == 0) {
                return get(sorted.get(mid));
            } else if(comp > 0) {
                pmid = mid;
                mid /= 2;
            } else {
                pmid = mid;
                mid = mid + mid / 2;
            }
        } while(Math.abs(mid - pmid) > 1);
        
        return get(sorted.get(mid < pmid ? mid : pmid));
    } 
    
    public V floor(String k) {
        
        int mid = sorted.size() / 2;
        int pmid = mid;
        do {
            
            int comp = sorted.get(mid).compareTo(k);
            if(comp == 0) {
                return get(sorted.get(mid));
            } else if(comp > 0) {
                pmid = mid;
                mid /= 2;
            } else {
                pmid = mid;
                mid = mid + mid / 2;
            }
        } while(Math.abs(mid - pmid) > 1);
        
        return get(sorted.get(mid > pmid ? mid : pmid));
    } 
    
    public V lower(String k) {
        
        int mid = sorted.size() / 2;
        int pmid = mid;
        do {
            
            int comp = sorted.get(mid).compareTo(k);
            if(comp == 0) {
                return get(sorted.get(mid - 1));
            } else if(comp > 0) {
                pmid = mid;
                mid /= 2;
            } else {
                pmid = mid;
                mid = mid + mid / 2;
            }
        } while(Math.abs(mid - pmid) > 1);
        
        return get(sorted.get(mid > pmid ? mid : pmid));
    }
    
    public V higher(String k) {
        
        int mid = sorted.size() / 2;
        int pmid = mid;
        do {
            
            int comp = sorted.get(mid).compareTo(k);
            if(comp == 0) {
                return get(sorted.get(mid + 1));
            } else if(comp > 0) {
                pmid = mid;
                mid /= 2;
            } else {
                pmid = mid;
                mid = mid + mid / 2;
            }
        } while(Math.abs(mid - pmid) > 1);
        
        return get(sorted.get(mid > pmid ? mid : pmid));
    }
    
    public Collection<V> subMap(String k1, String k2) {
        
        ArrayList<V> sub = new ArrayList<>();
        
        int mid = sorted.size() / 2;
        int pmid = mid;
        do {
            
            int comp = sorted.get(mid).compareTo(k1);
            if(comp == 0) {
                break;
            } else if(comp > 0) {
                pmid = mid;
                mid /= 2;
            } else {
                pmid = mid;
                mid = mid + mid / 2;
            }
        } while(Math.abs(mid - pmid) > 1);
        
        int curr = mid;
        
        if(sorted.get(mid).compareTo(k1) == 0) {
            
            while(sorted.get(curr).compareTo(k2) < 0) {
                sub.add(get(sorted.get(curr)));
                curr++;
            }
        } else {
        
            curr = mid > pmid ? mid : pmid;
            
            while(sorted.get(curr).compareTo(k2) < 0) {
                sub.add(get(sorted.get(curr)));
                curr++;
            }
        }
        
        return sub;
    } 
}
