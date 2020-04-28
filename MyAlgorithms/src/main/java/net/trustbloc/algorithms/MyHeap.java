package net.trustbloc.algorithms;

public class MyHeap {

    int []data;
    int size;

    MyHeap(int []data) {
        this.data  = data;
        this.size = data.length;
        heapify();
    }

    void heapify() {
        int lastLeafIndex = this.size - 1;
        int lastParentIndex = Math.floorDiv(lastLeafIndex - 1, 2);
        for (int i = lastParentIndex; i >=0; i--) {
            downHeap(i);
        }
    }

    void insert(int v) {
        if (this.data.length == this.size) {
            //enlarge the array
            int []temp = new int[2 * this.size];
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.data[i];
            }

            this.data = temp;
        }

        this.data[this.size++ - 1] = v;

        this.upHeap(this.size -1);
    }

    int remove() {
        int ret = this.data[0];
        this.data[0] = this.data[this.size-- - 1];
        this.downHeap(0);
        return ret;
    }

    void upHeap(int i) {
        if (i == 0) return;
        int parentIndex = Math.floorDiv(i - 1 , 2);
        if (this.data[i] < this.data[parentIndex]) {
            this.swap(i, parentIndex);
            this.upHeap(parentIndex);
        } // else noop
    }

    void downHeap(int i) {

        int leftIndex = 2*i + 1;
        int rightIndex = 2*i + 2;

        if (leftIndex < this.size - 1 ) {
            //has left child
            if (rightIndex < this.size -1 ) {
                //has both left and right
                int smallerIndex = leftIndex;
                if (this.data[leftIndex] >= this.data[rightIndex]) {
                    smallerIndex = rightIndex;
                }

                if (this.data[i] > this.data[smallerIndex]) {
                    this.swap(i, smallerIndex);
                    this.downHeap(smallerIndex);
                } //else no-op
            } else {
                // left only
                if (this.data[i] > this.data[leftIndex]) {
                    this.swap(i, leftIndex);
                    this.downHeap((leftIndex));
                } // else no-op
            }
        }
        //no child
        return;
    }

    void swap (int i, int j) {
        int temp = this.data[i];
        this.data[i] = this.data[j];
        this.data[j] = temp;
    }
}
