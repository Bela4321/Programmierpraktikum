package ga.framework;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Problem;
import ga.framework.model.Solution;
import ga.framework.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm{
    Problem problem;
    int populationSize;
    List<EvolutionaryOperator> evolutionaryOperator;
    FitnessEvaluator fitnessEvaluator;
    SurvivalOperator survivalOperator = new TopKSurvival(5);
    SelectionOperator selectionOperator;
    int maxGenerations;

    public GeneticAlgorithm(GeneticAlgorithm geneticAlgorithm) {
        this.problem = geneticAlgorithm.problem;
        this.populationSize = geneticAlgorithm.populationSize;
        this.evolutionaryOperator = geneticAlgorithm.evolutionaryOperator;
        this.fitnessEvaluator = geneticAlgorithm.fitnessEvaluator;
        this.survivalOperator = geneticAlgorithm.survivalOperator;
        this.selectionOperator = geneticAlgorithm.selectionOperator;
        this.maxGenerations = geneticAlgorithm.maxGenerations;
    }

    public GeneticAlgorithm(){};

    protected List<Solution> runOptimization() {
        List<Solution> population = new ArrayList<Solution>();
        //initialisiere Startpopulation with createNewSolution
        for (int i = 0; i < populationSize; i++) {
            try {
                population.add(problem.createNewSolution());
            } catch (NoSolutionException e) {
                e.printStackTrace();
            }
        }
        //evaluate fitness of population
        fitnessEvaluator.evaluate(population);
        for (int abc = 0; abc < maxGenerations; abc++) {
            //zufälligen evolutionary Operater auswählen und anwenden mit evolve()
            Random r = new Random();
            EvolutionaryOperator evolutionaryOperator = this.evolutionaryOperator.get(r.nextInt(this.evolutionaryOperator.size()));
            for (int i = 0; i < populationSize; i++) {
                try {
                    population.add(evolutionaryOperator.evolve(selectionOperator.selectParent(population)));
                } catch (EvolutionException e) {
                    e.printStackTrace();
                }
            }
            //Fitness der Nachkommen mit evaluate bestimmen; hinzufügen zur Population
            fitnessEvaluator.evaluate(population);
            //Mit selectPopulation Population für nächste Iteration auswählen, bis das Limit erreicht ist
            try {
                population = survivalOperator.selectPopulation(population, populationSize);
            } catch (SurvivalException e) {
                e.printStackTrace();
            }
        }
        //return die Population der letzten Iteration
        return population;
    }

    hasProblem solve(Problem problem){
        this.problem = problem;
        return new hasProblem(this);
    };

    class hasProblem extends GeneticAlgorithm{
        public hasProblem(GeneticAlgorithm ga) {
            super(ga);
        }

        hasPopSize withPopulationSize(int size){
            this.populationSize = size;
            return new hasPopSize(this);
        };
    };

    class hasPopSize extends GeneticAlgorithm{
        public hasPopSize(GeneticAlgorithm ga) {
            super(ga);
        }
        hasEvoOP evolvingSolutionsWith(EvolutionaryOperator operator){
            this.evolutionaryOperator = new ArrayList<EvolutionaryOperator>();
            this.evolutionaryOperator.add(operator);
            return new hasEvoOP(this);
        };
    };

    class hasEvoOP extends GeneticAlgorithm{
        public hasEvoOP(GeneticAlgorithm ga) {
            super(ga);
        }
        hasFitEval evaluatingSolutionsBy(FitnessEvaluator evaluator){
            this.fitnessEvaluator = evaluator;
            return new hasFitEval(this);
        };
        hasEvoOP evolvingSolutionsWith(EvolutionaryOperator operator){
            this.evolutionaryOperator.add(operator);
            return new hasEvoOP(this);
        };
    };

    class hasFitEval extends GeneticAlgorithm{
        public hasFitEval(GeneticAlgorithm ga) {
            super(ga);
        }
        hasSelOP performingSelectionWith(SelectionOperator operator){
            this.selectionOperator = operator;
            return new hasSelOP(this);
        };
        };
    };

    class hasSelOP extends GeneticAlgorithm{
        public hasSelOP(GeneticAlgorithm ga) {
            super(ga);
        }
        hasAttributes stoppingAtEvolution(int maxGenerations){
            this.maxGenerations = maxGenerations;
            return new hasAttributes(this);
        };
    }

    class hasAttributes extends GeneticAlgorithm{
        public hasAttributes(GeneticAlgorithm ga) {
            super(ga);
        }
        public List<Solution> runOptimization(){
        return super.runOptimization();
        };

        public static void main(String[] args) {
            GeneticAlgorithm ga = new GeneticAlgorithm();
            Problem problem = new Problem() {
                @Override
                public Solution createNewSolution() throws NoSolutionException {
                    return null;
                }
            };
            EvolutionaryOperator evoOp = new EvolutionaryOperator() {
                @Override
                public Solution evolve(Solution solution) throws EvolutionException {
                    return null;
                }
            };
            FitnessEvaluator fitEval = new FitnessEvaluator() {
                @Override
                public void evaluate(List<Solution> population) {

                }
            };
            SelectionOperator selecOP = new SelectionOperator() {
                @Override
                public Solution selectParent(List<Solution> candidates) {
                    return null;
                }
            };

            List<Solution> res = ga
                    .solve(problem)
                    .withPopulationSize(20)
                    .evolvingSolutionsWith(evoOp)
                    .evaluatingSolutionsBy(fitEval)
                    .performingSelectionWith(selecOP)
                    .stoppingAtEvolution(100)
                    .runOptimization();
        }
}
