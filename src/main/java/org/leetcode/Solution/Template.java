package org.leetcode.Solution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Template {

    public class TT {
        int x;
        int y;
    }

    public void incrementInteger(int[] s) {
        s[0]++;
    }

    public void test() {
        int[][] dir = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        List<List<Integer>> g = IntStream.rangeClosed(0, 4).boxed()
                .map(i -> new ArrayList<>(Collections.nCopies(0, 0)))
                .collect(Collectors.toList());
        g.get(0).add(1);
        System.out.println(g);
        List<List<Integer>> gg = new ArrayList<>();
        IntStream.rangeClosed(0, 4).boxed().map(x -> gg.add(new ArrayList<>())).collect(Collectors.toList());

        for (int i = 0; i < 5; i++)
            gg.add(new ArrayList<>());
        System.out.println(gg);
        Map<Integer, Integer> m = new HashMap<>();
        Set<Integer> s = new HashSet<>();
        m.put(0, 0);
        m.remove(0);
        s.remove(0);
        int[] ans = new int[1];
        incrementInteger(ans);
        System.out.println(ans[0]);

        DisjointSetUnion d = new DisjointSetUnion();
        d.makeSet(1);
        d.makeSet(2);
        d.unionSet(1, 2);
        d.makeSet(3);
        d.unionSet(1, 3);
        System.out.print(d.findSet(1));

        int[] ss = new int[]{5, 4, 3, 2, 1};
        TT[] sst = new TT[]{new TT()};
        Arrays.sort(sst, (lhs, rhs) -> {
            return lhs.x;
        });

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(1);
        PriorityQueue<Integer> pp = new PriorityQueue<Integer>(1, null);
        pp.add(2);
        pp.add(1);
        pq.add(2);
        pq.add(1);
        pq.add(3);
    }

    public class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer integer, Integer t1) {
            return 0;
        }
    }
}
