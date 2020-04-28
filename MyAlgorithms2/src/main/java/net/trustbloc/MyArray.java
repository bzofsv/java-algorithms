package net.trustbloc;

public class MyArray {
    
    int[] data;
    
    public MyArray(int[] data) {
        this.data = data;
    }
    
    public int[] insertionSort() {
        for(int i = 1; i < data.length; i++) {
            for(int j = i; j > 0; j--) {
                if(data[j] >= data[j - 1]) continue;
                int temp = data[j];
                data[j] = data[j - 1];
                data[j - 1] = temp;
            }
        }
        
        return data;
    }
}
