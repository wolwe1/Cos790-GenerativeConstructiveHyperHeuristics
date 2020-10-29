package library.gpLibrary.setup.factories.implementations;

import library.gpLibrary.helpers.IDataConverter;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import library.gpLibrary.setup.factories.IFitnessFunctionFactory;
import library.gpLibrary.specialisations.value.ValueFitnessFunction;

import java.util.ArrayList;
import java.util.List;

public class ValueTreeFitnessFunctionFactory implements IFitnessFunctionFactory<Double> {

    List<ValueNode<Double>> trainingSet;
    List<ValueNode<Double>> testingSet;

    public ValueTreeFitnessFunctionFactory() {
    }

    @Override
    public IFitnessFunction<Double> createNew(GeneticAlgorithmConfig<Double> config) {
//        List<ValueNode<Double>> trainingSet = new ArrayList<>();
//        List<ValueNode<Double>> testingSet = new ArrayList<>();
//
//        for (String item : config.getTrainingSet()) {
//            trainingSet.add((ValueNode<Double>) dataConverter.convert(item));
//        }
//
//        for (String item : config.getTestingSet()) {
//            testingSet.add((ValueNode<Double>) dataConverter.convert(item));
//        }

        return new ValueFitnessFunction(config.getLookAhead(),trainingSet,testingSet);
    }
}
