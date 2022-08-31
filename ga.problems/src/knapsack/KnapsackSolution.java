package knapsack;

import ga.framework.model.Problem;

public class KnapsackSolution extends ga.framework.model.Solution {
    int[] items;
    int capacity;
    boolean[] isInside;
    int weight;

    public KnapsackSolution(Problem problem, int[] items, int capacity) {
        super(problem);
        this.items = items.clone();
        this.capacity = capacity;
        this.isInside = new boolean[items.length];
        for (boolean b : isInside) {
            b = false;
        }
        this.weight = 0;
    }
}

