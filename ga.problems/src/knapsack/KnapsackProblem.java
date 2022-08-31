package knapsack;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Solution;

import java.util.Random;

public class KnapsackProblem implements ga.framework.model.Problem{
    int capacity;
    int[] weights;

    Random r = new Random();
    double creationRate = 0.8;

    public KnapsackProblem(int capacity, int[] weights) {
        this.capacity = capacity;
        this.weights = weights.clone();
    }

    @Override
    public Solution createNewSolution() throws NoSolutionException {
        KnapsackSolution solution = new KnapsackSolution(this, weights, capacity);
        boolean controller = true;
        while (controller){
            controller= false;
            for (int i = 0; i < weights.length; i++) {
                if (!solution.isInside[i]&&solution.weight+ solution.items[i]<=capacity&&r.nextDouble()<=creationRate){
                    solution.isInside[i]=true;
                    solution.weight+= solution.items[i];
                    controller= true;
                }
            }
        }
        if (solution.weight==0){
            throw new NoSolutionException("No fitting weight found");
        }
        return solution;
    }
}
