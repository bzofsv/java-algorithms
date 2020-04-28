package net.trustbloc.algorithms;

import java.util.Arrays;
import java.util.Random;

public class MySortingAlgos {
	
	public static int[] mergeSort(int []a) {
		if(a.length <= 1) return a;
		int []left = Arrays.copyOfRange(a, 0, a.length/2);
		int []right = Arrays.copyOfRange(a, (a.length/2) + 1, a.length - 1);
		left = mergeSort(left);
		right = mergeSort(right);
		return merge(left, right);
	}
	
	public static int[] merge(int []a, int []b) {
		int []c = new int[a.length + b.length];
		int i = 0;
		int j = 0;
		while(i + j < c.length) {
			if(i != a.length && (j == b.length || a[i] < b[j])) {
				c[i + j] = a[i++];
			}
			if(j != b.length) {
				c[i + j] = b[j++];	
			}
		}
		
		return c;
	}
	
	public static void selectionSort(int []a) {
		for(int i = 0; i < a.length; i++) {
			for(int j = i; j < a.length; j++) {
				if(a[j] < a[i]) {
					swap(a, i, j);
				}
			}
		}
	}
	
	public static void bubbleSort(int []a) {
		for(int i = 0; i < a.length; i++) {
			for(int j = i; j < a.length; j++) {
				if(a[j] > a[j + 1]) {
					swap(a, j, j + 1);
				}
			}
		}
	}
	
	public static void insertionSort(int []a) {
		for(int i = 0; i < a.length; i++) {
			for(int j = i; j >= 0; j--) {
				if(a[j] < a[j - 1]) {
					swap(a, j, j - 1);
				}
				break;
			}
		}
	}
	
	public static void swap(int []a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void swap(float []a, int i, int j) {
		float temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void quickSort(float []a, int b, int e) {
		
		if(e - b <= 1) return;
		
		int p = new Random().nextInt(e - b) + b;
		
		swap(a, p, b);
		
		float pivot = a[b];
		int i = b + 1;
		int j = e;
		
		while(i < j) {
			while(a[i] < pivot) i++;
			while(a[j] > pivot) j--;
			swap(a, i, j);
		}
		swap(a, i, b);
		quickSort(a, b, i);
		quickSort(a, i + 1, e);
	}
}
 