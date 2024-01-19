package org.leetcode;

import java.util.List;

class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int ans = Integer.MAX_VALUE;


        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j < M; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < N; k++) {
                    if (j != k) {
                        min = Math.min(min, matrix[i+1][k]);
                    }
                }
                matrix[i][j] += min;
            }
        }
        // find the ans;
        for (int i = 0; i < M; i++) {
            ans = Math.min(ans, matrix[0][i]);
        }

        return ans;
    }
}

public class Main {
}
