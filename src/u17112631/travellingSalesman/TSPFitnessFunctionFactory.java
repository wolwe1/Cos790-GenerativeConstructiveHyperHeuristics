package u17112631.travellingSalesman;

import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import library.gpLibrary.setup.factories.IFitnessFunctionFactory;
import u17112631.travellingSalesman.infrastructure.TSPFitnessFunction;
import u17112631.travellingSalesman.primitives.TSProblem;

public class TSPFitnessFunctionFactory<T> implements IFitnessFunctionFactory<T> {

    TSProblem problem;

    public TSPFitnessFunctionFactory(TSProblem problem){
        this.problem = problem;
    }

    @Override
    public IFitnessFunction<T> createNew(GeneticAlgorithmConfig<T> config) {
        return new TSPFitnessFunction<>(problem);
    }
}
