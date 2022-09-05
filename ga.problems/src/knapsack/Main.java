package knapsack;

import ga.framework.GeneticAlgorithm;
import ga.framework.TopKSurvival;
import ga.framework.TournamentSelection;
import ga.framework.model.Solution;

import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] weights = new int[]{5,4,4,4,3,3,2,2,1,1};
        int[] values = new int[]{10,8,6,4,7,4,6,3,3,1};
        int capacity = 11;

        KnapsackProblem problem = new KnapsackProblem(capacity, weights,values);
        GeneticAlgorithm ga = new GeneticAlgorithm(new TopKSurvival(4));
        List<Solution> result = ga.solve(problem)
                .withPopulationSize(10)
                .evolvingSolutionsWith(new KnapsackMutation())
                .evaluatingSolutionsBy(new KnapsackFitnessEvaluator())
                .performingSelectionWith(new TournamentSelection())
                .stoppingAtEvolution(10)
                .runOptimization();
        result.sort(Comparator.comparing(Solution::getFitness));
        for (Solution solution : result) {
            System.out.println(solution);
        }
    }
}
