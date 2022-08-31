package knapsack;

import ga.framework.GeneticAlgorithm;
import ga.framework.TopKSurvival;
import ga.framework.TournamentSelection;
import ga.framework.model.Solution;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] weights = new int[]{5,4,4,4,3,3,2,2,1,1};
        int capacity = 11;

        KnapsackProblem problem = new KnapsackProblem(capacity, weights);
        GeneticAlgorithm ga = new GeneticAlgorithm(new TopKSurvival(2));
        List<Solution> result = ga.solve(problem)
                .withPopulationSize(4)
                .evolvingSolutionsWith(new KnapsackMutation())
                .evaluatingSolutionsBy(new KnapsackFitnessEvaluator())
                .performingSelectionWith(new TournamentSelection())
                .stoppingAtEvolution(10)
                .runOptimization();

        for (Solution solution : result) {
            System.out.println(solution);
        }
    }
}
