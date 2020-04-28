/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc.algorithms;

/**
 *
 * @author wang
 */
public class MyDP2 {
    
    public static int[][] matrixChain(int[] d) {
        
        int n = d.length - 1;
        int[][] N = new int[n][n];
        
        for(int i = 0; i < n; i++) {
            N[i][i] = 0;
            for(int k = 0; k < n; k++) {
                for(int j = 0; j < n; j++) {
                    N[i][j] = Integer.MAX_VALUE;
                    N[i][j] = Math.min(N[i][j], N[i][k] + N[k + 1][j] + d[i] * d[k + 1] * d[j + 1]);
                }
            }
        }
        
        return N;
    }
    
    public static int[][] lcs(char[] x, char[] y) {
        int n = x.length;
        int m = y.length;
        int[][] L = new int[n + 1][m + 1];
        
        for(int i = 0; i <= n; i++) {
            for(int j = 0; j <= m; j++) {
                if(x[i - 1] == y[j - 1]) {
                    L[i][j] = 1 + L[i - 1][j - 1];
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }
       
        return L;
    }
    
    public static char[] reconstructLCS(char[] x, char[] y) {
       int[][] L = lcs(x, y);
       int i = x.length;
       int j = y.length;
       char[] lcs = new char[Math.min(i, j)];
       int k = 0;
       
       while(L[i][j] != 0) {
           if(x[i] == y[j]) {
               lcs[k] = x[i];
               k++;
               i--;
               j--;
           } else {
               if(L[i][j - 1] > L[i - 1][j]) {
                   j--;
               } else {
                   i--;
               }
           }
       }
       for(int l = 0; l <= k/2; l++) {
           char temp = lcs[l];
           lcs[l] = lcs[k - l];
       }
       
       return lcs;
    }
}
