package net.trustbloc;

public class MySortingAlgos {
    
    public void mergeSort(int[] a) {
        
        if(a.length <= 1) return;
        
        int []a1 = new int[a.length / 2];
        int []a2 = new int[a.length - a1.length];
        
        for(int i = 0; i < a.length / 2; i++) {
            a1[i] = a[i];
        }
        
        for(int j = a.length / 2; j < a.length; j++) {
            a2[j - (a.length / 2)] = a[j];
        }
        
        mergeSort(a1);
        mergeSort(a2);
        
        int i = 0;
        int j = 0;
        
        while(i + j < a.length) {
            if(a1[i] < a2[j]) a[i + j] = a1[i++];
            else a[i + j] = a2[j++];
        }
        
        return;
    }
    
    public int[] quickSort(int []a, int s, int e) {
        
        if(e - s < 2) return a;
        
        int p = s;
        int i = s + 1;
        int j = e;
        
        while(i < j) {
            while(a[i] < a[p]) i++;
            while(a[j] > a[p]) j--;
            
            swap(a, i, j);
        }
        
        swap(a, p, j);
        
        quickSort(a, s, i);
        quickSort(a, i + 1, e);
        
        return a;
    }
    
    private void swap( int []a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
