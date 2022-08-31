package knapsack;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Solution;

public class KnapsackProblem implements ga.framework.model.Problem{
    int capacity;
    int[] weights;

    @Override
    public Solution createNewSolution() throws NoSolutionException {
        KnapsackSolution solution = new KnapsackSolution(this);
        boolean controller = true;
        while (controller){
            controller= false;
            for (int i = 0; i < weights.length; i++) {
                if (!solution.isInside[i]&&solution.weight+ solution.items[i]<=capacity){
                    solution.isInside[i]=true;
                    solution.weight+= solution.items[i];
                    controller= true;
                }
            }
        }

    }
}
