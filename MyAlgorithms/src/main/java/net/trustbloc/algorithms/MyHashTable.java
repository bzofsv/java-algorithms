package net.trustbloc.algorithms;

import java.util.ArrayList;

public class MyHashTable {

    public static class Entry {
        String k;
        Object v;
        Entry(String k, Object v) {
            this.k = k;
            this.v = v;
        }
    }

    int capacity;
    int nEntries;

    ArrayList<Entry>[] data;

    public MyHashTable(int capacity) {
        this.capacity = capacity;
        //TODO: should be prime, instead of this.capacity *2
        this.nEntries = this.capacity * 2;
        data = new ArrayList[this.nEntries];
    }

    public Object get(String k) {
        ArrayList al = this.data[this.hash(k)];
        if (al == null) return null;
        for (Object o : al) {
            Entry e = (Entry) o;
            if (e.k.equals(k)) return e.v;
        }
        return null;
    }

    public void put(String k, Object v) {
        int hc = this.hash(k);
        ArrayList al = this.data[hc];
        if (al == null) al = new ArrayList();
        else {
            for (Object o : al) {
                Entry e = (Entry) o;
                if (e.k.equals(k)) {
                    al.remove(o);
                }
            }
        }

        al.add(new Entry(k, v));
        this.data[hc] = al;
    }

    public Object rem(String k) {
        ArrayList al = this.data[this.hash(k)];
        if (al == null) return null;
        for (Object o : al) {
            Entry e = (Entry) o;
            if (e.k.equals(k)) {
                al.remove(o);
                return e.v;
            }
        }
        return null;
    }

    protected int hash(String k) {
        int h = 0;
        for (int i = 0; i < k.length(); i++) {
            int c = k.charAt(i);
            h += c << 5 | c >>> 27;
        }
        return h % this.nEntries;
    }
}
