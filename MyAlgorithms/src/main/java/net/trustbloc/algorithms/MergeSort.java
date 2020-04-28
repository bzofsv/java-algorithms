package net.trustbloc.algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.LinkedTransferQueue;

public class MergeSort {

    public static int [] sort(int []s) {
        if(s.length < 2) return s;
        int mid = s.length/2;
        int []s1 = Arrays.copyOfRange(s, 0, mid);
        int []s2 = Arrays.copyOfRange(s, mid + 1, s.length - 1);
        s1 = sort(s1);
        s2 = sort(s2);
        return merge(s1, s2);
    }

    public static int [] merge(int[]s1, int []s2) {
        int []s = new int[s1.length + s2.length];
        int i = 0, j = 0;
        while(i + j < s.length) {
            if(j == s2.length || i < s1.length && s1[i] < s2[j]) {
                s[i + j] = s1[i++];
            } else if(j != s2.length){
                s[i + j] = s2[j++];
            }
        }

        return s;
    }

    public static LinkedList<Integer> sort(LinkedList<Integer> s) {
        if(s.size() < 2) return s;

        LinkedList<Integer> l = new LinkedList<>();
        LinkedList<Integer> r = new LinkedList<>();

        while(true) {
            if(s.isEmpty()) break;
            l.add(s.pop());
            if(s.isEmpty()) break;
            r.add(s.pop());
        }
        l = sort(l);
        r = sort(r);
        return merge(l, r);
    }

    public static LinkedList<Integer> merge(LinkedList<Integer> l, LinkedList<Integer> r) {
        LinkedList<Integer> s = new LinkedList<>();
        while(!l.isEmpty()) s.push(l.pop());
        while(!r.isEmpty()) s.push(r.pop());
        return s;
    }
}
