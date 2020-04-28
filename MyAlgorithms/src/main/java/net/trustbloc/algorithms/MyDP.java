package net.trustbloc.algorithms;

public class MyDP {

    public static int[][] matrixChain(int []d) {
        int n = d.length - 1;
        int [][]N = new int[n][n];

        for(int b = 1; b < n; b++) {
            for(int i = 0; i < n; i++ ) {
                int j = i + b;
                N[i][j] = Integer.MAX_VALUE;
                for(int k = i; k < j; k++) {
                    N[i][j] = Math.min(N[i][j], N[i][k] + N[k + 1][j] + d[i] * d[k + 1] * d[j + 1]);
                }
            }
        }

        return N;
    }

    public static int LCS(char []x, char []y) {

        //row i : index in x, col j: index in y, value: LCS(i, j)

        int [][]L = new int[x.length][y.length];

        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < y.length; j++) {
                if(x[i] == y[j]) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }
        return L[x.length - 1][y.length - 1];
    }

    public static char[] LCS2(char []x, char []y) {

        //row i : index in x, col j: index in y, value: LCS(i, j)

        int [][]L = new int[x.length][y.length];

        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < y.length; j++) {
                if(x[i] == y[j]) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }

        //size of LCS

        char []lcs = new char[L[x.length - 1][y.length - 1]];
        int i = x.length - 1;
        int j = y.length - 1;
        int k = lcs.length - 1;
        if(x[i] == y[j]) {
            lcs[k--] = x[i];
        }

        while(L[i][j] > 0) {
            if (L[i][j] == L[i - 1][j - 1] + 1) {
                lcs[k--] = x[i - 1];
                i--;
                j--;
            } else if(L[i - 1 ][j] >= L[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs;
    }
}
