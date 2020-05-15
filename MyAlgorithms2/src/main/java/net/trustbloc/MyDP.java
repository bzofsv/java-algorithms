/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.trustbloc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author bz
 */
public class MyDP {
    
    public int[][] matrixChain(int []d) {
        
        int[][] mc = new int[d.length - 1][d.length - 1];
        
        for(int a = 0; a < d.length - 1; a++) {
            for(int b = 0; b < d.length - 1; b++) {
                mc[a][b] = a == b ? 0 : Integer.MAX_VALUE;
            }
        }
        
        for(int i = 0; i < d.length; i++) {
            for(int j = 0; i < d.length; j++) {
                for(int k = i; k < j; k++) {
                    int n = mc[i][k] + mc[k + 1][j] + d[i] * d[k + 1]* d[j + 1];
                    if(mc[i][j] > n) {
                        mc[i][j] = n;
                    }
                }
            }
        }
        
        return mc;
    }
    
    public int[][] lcs(char []x, char []y) {
        
        int [][]L = new int [x.length][y.length];
        
        for(int j = 0; j < x.length; j++) {
            for(int k = 0; k < y.length; k++) {
                if(j == 0 || k == 0) {
                    if(x[j] == y[k]) L[j][k] = 1;
                    else L[j][k] = 0;
                } else {
                    if(x[j] == y[k]) L[j][k] = 1 + L[j - 1][k - 1];
                    else L[j][k] = Math.max(L[j - 1][k], L[j][k - 1]);
                }
            }
        }
        
        return L;
    }
    
    public char[] reconstructLCS(char []x, char []y) {
        
        int [][]L = lcs(x, y);
        char[] lcs = new char[x.length];
        
        int k = 0;
        
        for(int i = x.length - 1; i >= 0;) {
            for(int j = y.length - 1; j >= 0;) {
                if(x[i] == x[j]) {
                    lcs[k++] = x[i];
                    i--;
                    j--;
                } else {
                    if(L[i][j - 1] > L[i - 1][j]) j--;
                    else i--;
                }
            }
        }
       
        for(int i = 0; i < lcs.length / 2; i++) {
            char temp = lcs[i];
            lcs[i] = lcs[lcs.length - i];
            lcs[lcs.length - 1] = temp;
        }
        
        return lcs;
    }
}
