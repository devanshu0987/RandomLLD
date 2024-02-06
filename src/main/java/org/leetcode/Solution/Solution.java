package org.leetcode.Solution;

import java.util.*;

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

    public int longestCommonSubsequence(String text1, String text2) {
        // abcde VS ace
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }

    int solveFindPaths(int m, int n, int row, int col, int moveLeft, int[][][] dp) {
        int MOD = (int) (1e9 + 7);
        if (moveLeft < 0)
            return 0;
        if (row < 0 || row >= m || col < 0 || col >= n) {
            // we are outside the boundary
            return 1;
        }
        if (dp[row][col][moveLeft] != -1)
            return dp[row][col][moveLeft];
        // 4 options
        int a = solveFindPaths(m, n, row + 1, col, moveLeft - 1, dp);
        int b = solveFindPaths(m, n, row - 1, col, moveLeft - 1, dp);
        int c = solveFindPaths(m, n, row, col + 1, moveLeft - 1, dp);
        int d = solveFindPaths(m, n, row, col - 1, moveLeft - 1, dp);

        return dp[row][col][moveLeft] = (a + b + c + d) % MOD;
    }

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][][] dp = new int[m][n][maxMove + 1];
        // initialize with -1 here.
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                for (int k = 0; k < dp[0][0].length; k++)
                    dp[i][j][k] = -1;
            }
        }
        int ans = solveFindPaths(m, n, startRow, startColumn, maxMove, dp);
        return ans;

    }

    int MOD = (int) (1e9 + 7);

    int f(int n, int k, int[][] dp) {
        if (n < 0 || k < 0 || k > (n * (n - 1)) / 2)
            return 0;
        if (n == 1 && k == 0)
            return 1;
        if (dp[n][k] != -1)
            return dp[n][k];
        int option1 = f(n, k - 1, dp) % MOD;
        int option2 = f(n - 1, k, dp) % MOD;
        int option3 = f(n - 1, k - n, dp) % MOD;
        int ans = option1;
        ans += option2;
        ans %= MOD;
        ans -= option3;
        if (ans < 0)
            ans += MOD;
        return dp[n][k] = ans;
    }

    public int kInversePairs(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);
        return f(n, k, dp);
    }

    public List<List<Integer>> findWinners(int[][] matches) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Set<Integer> players = new HashSet<>();
        for (var match : matches) {
            indegree.put(match[1], indegree.getOrDefault(match[1], 0) + 1);
            players.add(match[0]);
            players.add(match[1]);
        }
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        ans.add(new ArrayList<>());

        // players who lost exactly 1 match
        for (var item : indegree.entrySet()) {
            if (item.getValue() == 1)
                ans.get(1).add(item.getKey());
        }

        // players who didn't lose any match
        for (var item : players) {
            if (!indegree.containsKey(item))
                ans.get(0).add(item);
        }

        Collections.sort(ans.get(0));
        Collections.sort(ans.get(1));
        return ans;
    }

    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] prefix = new int[N][M + 1];
        // row wise
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                prefix[i][j + 1] = prefix[i][j] + matrix[i][j];
            }
        }
//        for (int i = 0; i < prefix.length; i++) {
//            for (int j = 0; j < prefix[0].length; j++) {
//                System.out.print(prefix[i][j]);
//            }
//            System.out.println();
//        }

        int ans = 0;
        for (int c1 = 1; c1 <= M; c1++) {
            for (int c2 = c1; c2 <= M; c2++) {
                Map<Integer, Integer> ps = new HashMap<>();
                ps.put(0, 1);
                int cummSum = 0;
                for (int i = 0; i < N; i++) {
                    // fix the sum for the first element.
                    cummSum += prefix[i][c2] - prefix[i][c1 - 1];
                    int key = cummSum - target;
                    if (ps.containsKey(key)) {
                        ans += ps.get(key);
                    }
                    ps.put(cummSum, ps.getOrDefault(cummSum, 0) + 1);
                }
            }
        }
        return ans;
    }

    int solveOddSum(int[] arr, int index, int type, int isOdd, int[][][] dp) {
        if (index >= arr.length) {
            // end of the array
            // if the sum is odd, we should count it.
            return isOdd;
        }

        if (dp[index][type][isOdd] != -1) {
            return dp[index][type][isOdd];
        }

        if (type == 0) {
            int notTake = solveOddSum(arr, index + 1, 0, 0, dp);
            int take = solveOddSum(arr, index + 1, 1, arr[index] % 2, dp);
            return dp[index][type][isOdd] = take + notTake;
        } else {
            // either you end here
            int notTake = solveOddSum(arr, arr.length, 1, isOdd, dp);
            int take = solveOddSum(arr, index + 1, 1, (isOdd + arr[index]) % 2, dp);
            return dp[index][type][isOdd] = take + notTake;
        }
    }


    public int numOfSubarrays(int[] arr) {
        int[][][] dp = new int[arr.length][2][2];
        // initialize
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return solveOddSum(arr, 0, 0, 0, dp);
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stk = new ArrayDeque<>();
        Set<String> operators = new HashSet<>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        for (var item : tokens) {
            if (operators.contains(item)) {
                int second = stk.pop();
                int first = stk.pop();
                int res = 0;
                switch (item) {
                    case "+":
                        res = first + second;
                        break;
                    case "-":
                        res = first - second;
                        break;
                    case "*":
                        res = first * second;
                        break;
                    case "/":
                        res = first / second;
                        break;
                }
                stk.push(res);
            } else {
                stk.push(Integer.valueOf(item));
            }
        }
        return stk.peek();
    }

    public List<Integer> sequentialDigits(int low, int high) {
        // low to high
        // Digit DP but possible that we can just enumerate it because we have to return actual count.
        // 12, 23, 34, 45, 56, 67, 78, 89,
        // x , 123, 234, 345, 456, 567, ...
        // x , x  , 1234,
        List<String> initialList = Arrays.asList("12", "23", "34", "45", "56", "67", "78", "89");
        List<Integer> ans = new ArrayList<>();
        Deque<String> q = new ArrayDeque<>(initialList);
        while (!q.isEmpty()) {
            String front = q.peek();
            q.pop();
            // see if we need to count this answer.
            // It will not overflow or throw exception. We will test it out.
            Integer val = Integer.parseUnsignedInt(front);
            if (val >= low && val <= high) {
                ans.add(val);
            }
            // generate the child if possible.
            char frontChar = front.charAt(0);
            frontChar--;
            if (frontChar > '0' && frontChar <= '9') {
                q.push(frontChar + front);
            }
        }
        Collections.sort(ans);
        return ans;
    }

    public int[][] divideArray(int[] nums, int k) {
        int size = nums.length;
        int[][] ans = new int[size / 3][size / 3];
        Arrays.sort(nums);
        int index = 0;
        for (int i = 0; i < nums.length; i = i + 3) {
            int a = nums[i];
            int b = nums[i + 1];
            int c = nums[i + 2];
            // validate
            if (Math.abs(a - b) <= k && Math.abs(b - c) <= k && Math.abs(c - a) <= k) {
                ans[index][0] = a;
                ans[index][1] = b;
                ans[index][2] = c;
                index++;
            } else {
                int[][] err = new int[0][0];
                return err;
            }
        }
        return ans;
    }

    int[] nextGreater(int[] arr) {
        int[] ans = new int[arr.length];
        Arrays.fill(ans, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {

            // while loop runs until the stack is not empty AND
            // the element represented by stack top is STRICTLY LARGER than the current element
            // This means, the stack will always be monotonic non decreasing (type 2)
            while (stack.size() > 0 && arr[stack.peek()] <= arr[i]) {

                int stackTop = stack.pop();
                ans[stackTop] = i;
            }

            stack.push(i);
        }
        return ans;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] ans = nextGreater(temperatures);
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == -1)
                ans[i] = 0;
            else {
                ans[i] = ans[i] - i;
            }
        }
        return ans;
    }

    public long solveMaxSum(int[] arr, int index, int k, long[] dp) {
        if (index >= arr.length) {
            return 0;
        }

        if (dp[index] != -1)
            return dp[index];

        // split into multiple partitions.
        long max = arr[index];
        long ans = 0;
        for (int i = index; i < Math.min(arr.length, index + k); i++) {
            max = Math.max(max, arr[i]);
            long option = (max * (i - index + 1)) + solveMaxSum(arr, i + 1, k, dp);
            ans = Math.max(ans, option);
        }
        dp[index] = ans;
        return dp[index];
    }

    public int maxSumAfterPartitioning(int[] arr, int k) {
        long[] dp = new long[arr.length];
        Arrays.fill(dp, -1);
        return (int) solveMaxSum(arr, 0, k, dp);
    }

    boolean isMatch(Map<Character, Integer> state, Map<Character, Integer> tstate) {
        for (var item : tstate.entrySet()) {
            if (state.containsKey(item.getKey())) {
                if (state.get(item.getKey()) < item.getValue()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public String minWindow(String s, String t) {
        String ans = "";
        int minLen = Integer.MAX_VALUE;
        int L = -1, R = -1;
        Map<Character, Integer> state = new HashMap<>();
        Map<Character, Integer> tstate = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tstate.put(t.charAt(i), tstate.getOrDefault(t.charAt(i), 0) + 1);
        }
        int we = 0, ws = 0;
        for (we = 0; we < s.length(); we++) {
            // add to state.
            state.put(s.charAt(we), state.getOrDefault(s.charAt(we), 0) + 1);

            // check if all chars of t are satisfied.
            while (isMatch(state, tstate)) {
                // we have a potential answer.
                if ((we - ws + 1) < minLen) {
                    minLen = we - ws + 1;
                    L = ws;
                    R = we;
                }
                // update state.
                state.put(s.charAt(ws), state.get(s.charAt(ws)) - 1);
                if (state.get(s.charAt(ws)) == 0) {
                    state.remove(s.charAt(ws));
                }
                ws++;
            }
        }
        if (L == -1) {
            return "";
        }
        return s.substring(L, R + 1);

    }

    public int firstUniqChar(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            freq.put(s.charAt(i), freq.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1)
                return i;
        }
        return -1;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> anagrams = new HashMap<>();
        for (var item : strs) {
            char[] chars = item.toCharArray();
            Arrays.sort(chars);
            String hash = Arrays.toString(chars);
            if (anagrams.containsKey(hash)) {
                var value = anagrams.get(hash);
                value.add(item);
                anagrams.put(hash, value);
            } else {
                ArrayList<String> value = new ArrayList<>();
                value.add(item);
                anagrams.put(hash, value);
            }
        }
        for (var item : anagrams.entrySet()) {
            res.add(item.getValue());
        }
        return res;

    }
}

