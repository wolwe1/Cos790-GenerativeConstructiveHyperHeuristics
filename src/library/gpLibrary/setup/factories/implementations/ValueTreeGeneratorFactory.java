package library.gpLibrary.setup.factories.implementations;

import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import library.gpLibrary.setup.factories.ITreeGeneratorFactory;
import library.gpLibrary.specialisations.value.ValueTreeGenerator;

public class ValueTreeGeneratorFactory<T> implements ITreeGeneratorFactory<T> {

    @Override
    public ITreeGenerator<T> createNew(GeneticAlgorithmConfig<T> config) {
        ValueTreeGenerator<T> generator = new ValueTreeGenerator<>(config.getFunctionSet(),config.getTerminalSet(),config.getMaxIncrease());

        generator.setDepths(config.getMaxDepth(),config.getMaxBreadth());
        generator.setTerminalToFunctionRatio(config.getTerminalChance());

        return generator;
    }
}
