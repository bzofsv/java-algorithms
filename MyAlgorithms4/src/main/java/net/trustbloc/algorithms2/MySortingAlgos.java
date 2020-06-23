/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author bz
 */
public class MySortingAlgos<E> {
    
    public class Entry<E> {
        int k;
        E e;
        
        public Entry(int k, E e) {
            this.k = k;
            this.e = e;
        }
    }
    
    public int[] insertionSort(int[] a) {
        
        for(int i = 1; i < a.length; i++) {
            for(int j = i; j > 0; j--) {
                if(a[j] < a[j - 1]) swap(j, j - 1, a);
                else break;
            }
        }
        
        return a;
    }
    
    public int[] mergeSort(int[] a) {
        
        if(a.length < 2) return a;
        
        int mid = a.length/2;
        
        int[] a1 = Arrays.copyOfRange(a, 0, mid);
        int[] a2 = Arrays.copyOfRange(a, mid + 1, a.length - 1);
        
        mergeSort(a1);
        mergeSort(a2);
        
        int i = 0;
        int j = 0;
        while(i + j < a.length) {
            if(a1[i] < a2[j]) {
                a[i + j] = a1[i++];
            } else {
                a[i + j] = a2[j++];
            }
        }
        
        return a;
    }
    
    public int[] quickSort(int[] a, int s, int e) {
        
        int pivot = s; // pivot index = first in array
        int i = s + 1;
        int j = e;
        
        while(i < j) {
            while(a[i] < a[pivot]) i++;
            while(a[j] > a[pivot]) j--;
            
            swap(i, j, a);
        }
        
        swap(j, pivot, a);
        
        quickSort(a, s, j);
        quickSort(a, j + 1, e);
        
        return a;
    }
    
    public ArrayList<Entry> bucketSort(ArrayList<Entry> entries) {
        
        ArrayList<ArrayList<Entry>> buckets = new ArrayList<>();
        for(Entry e : entries) {
            entries.remove(e);
            buckets.get(e.k).add(e);
        }
        
        for(int i = 0; i < entries.size(); i++) {
            for(Entry e : buckets.get(i)) {
                entries.add(e);
                buckets.get(i).remove(e);
            }
        }
        
        return entries; 
    }
    
    private void swap(int i, int j, int[] a) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
