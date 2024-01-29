package org.leetcode.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, List<Integer>> g;

    public Graph() {
        g = new HashMap<>();
    }

    public void addUndirectedEdge(int nodeA, int nodeB) {
        List<Integer> list = g.getOrDefault(nodeA, new ArrayList<>());
        list.add(nodeB);
        g.put(nodeA, list);

        list = g.getOrDefault(nodeB, new ArrayList<>());
        list.add(nodeA);
        g.put(nodeB, list);
    }
}
