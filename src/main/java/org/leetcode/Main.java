package org.leetcode;

import org.leetcode.Solution.Solution;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = new int[]{71, 55, 82, 55};
        // var res = s.sumSubarrayMins(arr);
        // System.out.println(res);

//        int[] input = new int[]{1, 2, 2, 4};
//        var res = s.findErrorNums(input);
//        System.out.println(res[0] + " " + res[1]);

        // check if nothing is possible
        String[] ar = new String[]{"abcdefghijklmnopqrstuvwxyz"};
        var res = s.maxLength(List.of(ar));
        System.out.println(res);
    }

}
