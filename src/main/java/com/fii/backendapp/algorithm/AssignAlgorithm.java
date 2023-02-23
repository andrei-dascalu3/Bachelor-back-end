package com.fii.backendapp.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class AssignAlgorithm {
    private final Integer refineLimit;
    private final Integer n, m, nodes;
    private final Double[] pi;
    private final Double alpha;
    private final Map<Edge, Integer> x;
    private final List<Integer> unassignedStuds;
    private final List<Integer> unassignedProps;
    private final Map<Edge, Double> c;
    private final TreeMap<Double, Integer> heap;
    private Double eps;
    private final Map<Integer, Assignation> solution;

    public AssignAlgorithm(Integer n, Integer m, Map<Edge, Double> c) {
        this.n = n;
        this.m = m;
        this.c = c;
        nodes = n + m;
        alpha = 10.0;
        pi = new Double[m + 1];
        x = new HashMap<>();
        unassignedStuds = new ArrayList<>();
        unassignedProps = new ArrayList<>();
        heap = new TreeMap<>();
        solution = new HashMap<>();
        refineLimit = n * 2;
    }

    public Map<Integer, Assignation> solve() {
        eps = getMaxCost();
        for (int i = 0; i < n; i++) {
            unassignedStuds.add(i);
        }
        for (int i = 0; i < m; i++) {
            pi[i] = round(.0);
        }
        while (eps >= alpha / nodes.doubleValue()) {
            refine();
        }
        for(var stud : unassignedStuds) {
            if(!unassignedProps.isEmpty()) {
                var prop = unassignedProps.remove(0);
                solution.put(stud, new Assignation(prop, 100.0));
            }
        }
        return solution;
    }

    private void refine() {
        eps = eps / alpha;
        for (var edge : c.keySet()) {
            x.put(edge, 0);
        }
        Collections.shuffle(unassignedStuds);
        Integer counter = 0;
        while (!unassignedStuds.isEmpty() && counter < refineLimit) {
            Integer i = unassignedStuds.remove(0);
            doublePushHeap(i);
            counter++;
        }
    }

    private void doublePushHeap(Integer i) {
        Edge edge;
        Integer[] result = getEndsWithMinReducedCost(i);
        Integer j = result[0], k = result[1];
        if (j != null && k != null) {
            x.put(new Edge(i, j), 1); // push
            solution.put(i, new Assignation(j, c.get(new Edge(i, j))));
            unassignedStuds.remove(new Integer(i));
            unassignedProps.remove(new Integer(j));
            if (!heap.containsValue(j)) {
                heap.put(pi[j], j);
                if (heap.size() > n) {
                    Integer g = heap.pollFirstEntry().getValue();
                    for (Integer f = 0; f < n; ++f) {
                        edge = new Edge(f, g);
                        if (x.containsKey(edge) && x.get(edge) == 1) {
                            x.put(edge, 0); // push
                            solution.put(i, null);
                            unassignedStuds.add(f);
                            unassignedProps.add(g);
                        }
                    }
                }
            }
            for (Integer h = 0; h < n; ++h) {
                edge = new Edge(h, j);
                if (!h.equals(i) && x.get(edge) != null && x.get(edge) == 1) {
                    x.put(edge, 0); //push
                    unassignedStuds.add(h);
                    unassignedProps.add(j);
                    break;
                }
            }
            pi[j] = pi[k] + c.get(new Edge(i, k)) - c.get(new Edge(i, j)) + eps;
            pi[j] = round(pi[j]);
        }
    }

    private Integer[] getEndsWithMinReducedCost(Integer i) {
        Double firstMin = null, secondMin = null, reducedCost;
        Integer[] result = {null, null};
        for (int j = 0; j < m; ++j) {
            reducedCost = getReducedCost(i, j);
            if (reducedCost != null) {
                if (firstMin == null) {
                    firstMin = reducedCost;
                    result[0] = j;
                } else if (reducedCost < firstMin) {
                    secondMin = firstMin;
                    result[1] = result[0];
                    firstMin = reducedCost;
                    result[0] = j;
                } else if (secondMin == null || reducedCost < secondMin) {
                    secondMin = reducedCost;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    private Double getReducedCost(Integer i, Integer j) {
        Edge edge = new Edge(i, j);
        if (c.containsKey(edge)) {
            return c.get(edge) - pi[i] + pi[j];
        }
        return null;
    }

    private Double getMaxCost() {
        Double max = null;
        for (var edge : c.keySet()) {
            Double value = c.get(edge);
            if (max == null || max < c.get(edge)) {
                max = value;
            }
        }
        return max;
    }

    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
