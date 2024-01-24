package org.leetcode.Solution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Solution {
    public Solution() {
    }

    public int minFallingPathSum(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int ans = Integer.MAX_VALUE;


        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j < M; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < N; k++) {
                    if (j != k) {
                        min = Math.min(min, matrix[i + 1][k]);
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

    int[] findPrevSmallerIndexes(int[] arr) {
        // initialize an empty stack
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // initialize previousSmaller array, this array hold the output
        // initialize all the elements are -1 (invalid value)
        int[] previousSmaller = new int[(arr.length)];
        Arrays.fill(previousSmaller, -1);

        // iterate through all the elements of the array
        for (int i = 0; i < arr.length; i++) {

            // while loop runs until the stack is not empty AND
            // the element represented by stack top is LARGER OR EQUAL to the current element
            // This means, the stack will always be monotonic strictly increasing (type 1)
            while (stack.size() > 0 && arr[stack.peek()] >= arr[i]) {

                // pop out the top of the stack, it represents the index of the item
                var stackTop = stack.pop();
            }

            // this is the additional bit here
            if (stack.size() > 0) {
                // the index at the stack top refers to the previous smaller element for `i`th index
                previousSmaller[i] = stack.peek();
            }

            // push the current element
            stack.push(i);
        }
        return previousSmaller;
    }

    int[] findNextSmallerIndexes(int[] arr) {
        // initialize an empty stack
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // initialize nextGreater array, this array hold the output
        // initialize all the elements are -1 (invalid value)
        int[] nextSmaller = new int[arr.length];
        Arrays.fill(nextSmaller, -1);

        // iterate through all the elements of the array
        for (int i = 0; i < arr.length; i++) {

            // while loop runs until the stack is not empty AND
            // the element represented by stack top is STRICTLY LARGER than the current element
            // This means, the stack will always be monotonic non decreasing (type 2)
            while (stack.size() > 0 && arr[stack.peek()] > arr[i]) {

                // pop out the top of the stack, it represents the index of the item
                int stackTop = stack.pop();

                // as given in the condition of the while loop above,
                // nextSmaller element of stackTop is the element at index i
                nextSmaller[stackTop] = i;
            }

            // push the current element
            stack.push(i);
        }
        return nextSmaller;
    }

    public int sumSubarrayMins(int[] arr) {
        int sum = 0;
        int mod = (int) (1e9 + 7);
        var nextSmaller = findNextSmallerIndexes(arr);
        var prevSmaller = findPrevSmallerIndexes(arr);
        for (int i = 0; i < arr.length; i++) {
            int prevIndex = prevSmaller[i] == -1 ? i + 1 : i - prevSmaller[i];
            int nextIndex = (nextSmaller[i] == -1) ? arr.length - i : nextSmaller[i] - i;
            long pdt = ((long) (prevIndex) * (nextIndex)) % mod;
            long finalPdt = (pdt * arr[i]) % mod;
            sum += finalPdt;
            sum = sum % mod;
        }
        return sum;
    }

    public int rob(int[] nums) {
        int[] dp = new int[nums.length + 1];
        Arrays.fill(dp, 0);
        dp[1] = nums[0];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(nums[i - 1] + dp[i - 2], dp[i - 1]);
        }
        return dp[dp.length - 1];
    }

    public int[] findErrorNums(int[] nums) {
        int N = nums.length;
        int[] res = new int[]{0, 0};
        int[] map = new int[N + 1];
        Arrays.fill(map, 0);
        for (int i = 0; i < N; i++) {
            map[nums[i]]++;
        }
        for (int i = 1; i < map.length; i++) {
            if (map[i] == 0) {
                res[1] = i;
            }
            if (map[i] == 2) {
                res[0] = i;
            }
        }
        return res;
    }

    int updateState(String s, int state) {
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';
            boolean bitStatus = (state & (1 << index)) > 0;
            if (bitStatus) {
                // we have already used this up, we can't take this since it will cause duplicate chars.
                return -1;
            }
            state = ((1 << index) | state);
        }
        return state;
    }

    // Returning len from the evaluations.
    int solve(List<String> arr, int index, int state, int len) {
        if (index > arr.size()) {
            // we have reached the end, we won't find answer here.
            return -1;
        }
        if (index == arr.size()) {
            return len;
        }
        // here we can do take, notTake
        int notTake = solve(arr, index + 1, state, len);
        int take = 0;
        // if we take this index.
        int newState = updateState(arr.get(index), state);
        if (newState != -1) {
            take = solve(arr, index + 1, newState, len + arr.get(index).length());
        }
        return Math.max(take, notTake);
    }

    public int maxLength(List<String> arr) {
        int ans = solve(arr, 0, 0, 0);
        return ans;
    }

    boolean validateIfPseudoPallindome(int[] state, int len) {
        if (len % 2 == 0) {
            for (int i = 0; i < state.length; i++) {
                if (state[i] > 0 && state[i] % 2 != 0)
                    return false;
            }
            return true;
        } else {
            int oddCount = 0;
            for (int i = 0; i < state.length; i++) {
                if (state[i] > 0 && state[i] % 2 == 1)
                    oddCount++;
            }
            if (oddCount != 1)
                return false;
            return true;
        }
    }

    int solveTree(TreeNode root, int[] state, int len) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null) {
            // we need to evaluate state here.
            if (validateIfPseudoPallindome(state, len))
                return 1;
            return 0;
        }
        state[root.val]++;
        int L = solveTree(root.left, state, len + 1);
        int R = solveTree(root.right, state, len + 1);
        state[root.val]--;
        return Math.max(L, R);
    }

    public int pseudoPalindromicPaths(TreeNode root) {
        int[] state = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        return solveTree(root, state, 0);
    }
}

