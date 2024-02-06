package org.leetcode;

import org.leetcode.Solution.Solution;
import org.leetcode.Solution.Template;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = new int[]{1, 15, 7, 9, 2, 5, 10};
        // var res = s.sumSubarrayMins(arr);
        // System.out.println(res);

//        int[] input = new int[]{1, 2, 2, 4};
//        var res = s.findErrorNums(input);
//        System.out.println(res[0] + " " + res[1]);

        // check if nothing is possible
//        String[] ar = new String[]{"abcdefghijklmnopqrstuvwxyz"};
//        var res = s.maxLength(List.of(ar));
//        System.out.println(res);
        //System.out.println(s.longestCommonSubsequence("abcde", "ace"));
        try {
            // System.out.println(s.findPaths(10, 30, 50, 0, 1));
//            Template t = new Template();
//            t.test();
            int[][] matrix = new int[][]{
                    {0, 1, 0, 0, 1},
                    {0, 0, 1, 1, 1},
                    {1, 1, 1, 0, 1},
                    {1, 1, 0, 1, 1},
                    {0, 1, 1, 0, 0}
            };
            System.out.println(s.minWindow("ADOBECODEBANC", "ABC"));
            // System.out.println(s.numSubmatrixSumTarget(matrix, 1));

            // System.out.println(s.numOfSubarrays(arr));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
