package u17112631.travellingSalesman;

import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import library.gpLibrary.setup.factories.ITreeGeneratorFactory;
import u17112631.travellingSalesman.infrastructure.TSPTreeGenerator;

public class TSPTreeGeneratorFactory implements ITreeGeneratorFactory<Double> {

    @Override
    public ITreeGenerator<Double> createNew(GeneticAlgorithmConfig<Double> config) {
        TSPTreeGenerator generator = new TSPTreeGenerator(config.getFunctionSet(),config.getTerminalSet(),config.getMaxIncrease());
        generator.setDepths(config.getMaxDepth(),config.getMaxBreadth());

        return generator;
    }
}
