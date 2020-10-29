package u17112631.travellingSalesman.infrastructure;


import library.gpLibrary.infrastructure.implementation.TreePopulationManager;
import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;

public class TSPPopulationManager<T> extends TreePopulationManager<T> {

    public TSPPopulationManager(ITreeGenerator<T> treeGenerator, IFitnessFunction<T> fitnessFunction, long seed) {
        super(treeGenerator, fitnessFunction, seed);
    }
}
