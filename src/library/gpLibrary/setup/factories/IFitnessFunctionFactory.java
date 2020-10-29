package library.gpLibrary.setup.factories;

import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.setup.GeneticAlgorithmConfig;

public interface IFitnessFunctionFactory<T> {
    IFitnessFunction<T> createNew(GeneticAlgorithmConfig<T> config);
}
