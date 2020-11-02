package library.gpLibrary.infrastructure.interfaces;


import library.gpLibrary.models.highOrder.GeneticAlgorithmSummary;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;

public interface IGeneticAlgorithm<T> {

    PopulationMember<T> performGeneration(int i);

    GeneticAlgorithmSummary<T> run();

    void addOperator(IGeneticOperator<T> newOperator);

    void setSeed(long seed);
}
