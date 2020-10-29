package library.gpLibrary.setup.factories;

import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.setup.GeneticAlgorithmConfig;

public interface ITreeGeneratorFactory<T> {

    ITreeGenerator<T> createNew(GeneticAlgorithmConfig<T> config);
}
