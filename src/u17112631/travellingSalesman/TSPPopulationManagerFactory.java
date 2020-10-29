package u17112631.travellingSalesman;

import library.gpLibrary.infrastructure.interfaces.IPopulationManager;
import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.setup.factories.IPopulationManagerFactory;
import u17112631.travellingSalesman.infrastructure.TSPPopulationManager;

public class TSPPopulationManagerFactory<T> implements IPopulationManagerFactory<T> {

    @Override
    public IPopulationManager<T> createNew(ITreeGenerator<T> generator, IFitnessFunction<T> function, long seed) {
        return new TSPPopulationManager<T>(generator,function,seed);
    }
}
