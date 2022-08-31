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

    public KnapsackSolution copyOf(){
        KnapsackSolution copy = new KnapsackSolution(this.getProblem(), this.items, this.capacity);
        copy.isInside = this.isInside.clone();
        copy.weight = this.weight;
        return copy;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < isInside.length; i++) {
            if (isInside[i]) {
                sb.append(items[i]);
                sb.append(",");
            }
        }
        sb.append("]");
        sb.append("\n total weight: "+weight);
        return sb.toString();
    }
}

