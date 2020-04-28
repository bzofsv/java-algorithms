package net.trustbloc;

/**
 *
 * @author wang
 */
public class MyArrayHeap<E> {
    
    public class Node<E> {
        
        int k;
        E e;
        
        public Node(int k, E e) {
            this.k = k;
            this.e = e;
        }
    }
    
    Node<E> []data;
    int size;
    
    public MyArrayHeap(int cap) {
        this.data = new Node[cap];
        this.size = 0;
    }
    
    private void heapfiy() {
        
        if(this.size <= 1) return;
        
        for(int i = (this.size - 1) / 2; i >= 0; i++) {
            this.bubbleDown(i);
        }
    }
    
    private void bubbleDown(int i) {
        
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        
        if(l > size - 1) return;
        
        if(data[l] == null && data[r] == null) {
            return;
        }
        
        int lchild; //lesser child
        if(data[l].k < data[r].k) {
            lchild = l;
        } else lchild = r;
        
        if(data[lchild].k < data[i].k) swap(lchild, i);
        
        bubbleDown(lchild);
    }
    
    private void bubbleUp(int i) {
        
        if(i > size - 1 || data[i] == null) return;
        
        int p = (i - 1) / 2;
        if(data[i].k < data[p].k) {
            swap(i, p);
        }
        
        bubbleUp(p);
    }
    
    private void swap(int i, int j) {
        Node<E> temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    
    public void insert(int k, E e) {
        
        Node<E> node = new Node(k, e);
        
        if(size == data.length - 1) {
            
            Node<E> []ndata = new Node[data.length * 2];
            for(int i = 0; i < size; i++) {
                ndata[i] = data[i];
            }
            
            data = ndata;
        }
        
        data[size] = node;
        
        bubbleUp(size++);
    }
    
    public E remove() {
        
        E ret = data[0].e;
        
        data[0] = data[size - 1];
        
        size--;
        
        bubbleDown(0);
        
        return ret;
    }
}
