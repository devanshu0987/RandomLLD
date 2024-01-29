package org.leetcode.Solution;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.swap;

public class DisjointSetUnion {
    private Map<Integer, Integer> parent, size;

    public DisjointSetUnion() {
        parent = new HashMap<>();
        size = new HashMap<>();
    }

    public void makeSet(Integer v) {
        if (!parent.containsKey(v)) {
            parent.put(v, v);
            size.put(v, 1);
        }
    }

    public Integer findSet(Integer v) {
        if (!parent.containsKey(v))
            return Integer.MAX_VALUE;
        if (v == parent.get(v))
            return v;
        return parent.put(v, findSet(parent.get(v)));
    }

    public Integer unionSet(Integer u, Integer v) {
        // get representatives of the set
        Integer parentU = findSet(u);
        Integer parentV = findSet(v);
        if (parentU != parentV) {
            // make the parent same.
            if (size.get(parentU) < size.get(parentV)) {
                Integer temp = parentU;
                parentU = parentV;
                parentV = temp;
            }

            parent.put(parentV, parentU);
            size.put(parentU, size.get(parentU) + size.get(parentV));
            return size.get(parentU);
        }
        return size.get(parentU);
    }
}
