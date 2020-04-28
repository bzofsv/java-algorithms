package net.trustbloc.algorithms;

import java.util.LinkedList;

public class MyQuickSort {

    public static void sort(LinkedList<Integer> s) {
        if(s.size() < 2) return;

        LinkedList<Integer> l = new LinkedList<>();
        LinkedList<Integer> e = new LinkedList<>();
        LinkedList<Integer> g = new LinkedList<>();

        int pivot = s.pop();
        while(!s.isEmpty()) {
            int d = s.pop();
            if(d < pivot) {
                l.push(d);
            } else if(d == pivot) {
                e.push(d);
            } else {
                g.push(d);
            }
        }
        sort(l);
        sort(g);

        while(!l.isEmpty()) s.push(l.pop());
        while(!e.isEmpty()) s.push(e.pop());
        while(!g.isEmpty()) s.push(g.pop());
    }

    public static void sort(int []s, int b, int e) {
        if(e - b < 2) return;

        int pivot = s[b];
        int i = b + 1;
        int j = e;

        while(i < j) {
            while(s[i] <= pivot) i++;
            while(s[j] > pivot) j--;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }

        s[b] = s[i];
        s[i] = pivot;
        sort(s, b, i);
        sort(s, i + 1, e);
    }
}
