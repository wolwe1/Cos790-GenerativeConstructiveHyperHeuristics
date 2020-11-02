package library.gpLibrary.models.highOrder;

import library.gpLibrary.models.highOrder.implementation.PopulationMember;
import library.gpLibrary.models.highOrder.implementation.PopulationStatistics;

public class GeneticAlgorithmSummary<T> {

    public PopulationMember<T> bestPerformer;

    public long runtime;

    public int foundInGeneration;

    public GeneticAlgorithmSummary(PopulationMember<T> bestPerformer, long runtime, int foundInGeneration){
        this.bestPerformer = bestPerformer;
        this.runtime = runtime;
        this.foundInGeneration = foundInGeneration;
    }

    public PopulationStatistics<Double> getStats(){
        PopulationStatistics<Double> stats = new PopulationStatistics<>();
        stats.setMeasure("Fitness",bestPerformer.getFitness());
        stats.setMeasure("Runtime", (double) runtime);
        stats.setMeasure("Generation found", (double) foundInGeneration);

        return stats;
    }
}
