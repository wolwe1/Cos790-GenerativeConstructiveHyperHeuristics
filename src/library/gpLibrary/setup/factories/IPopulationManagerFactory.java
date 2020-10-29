package library.gpLibrary.setup.factories;

import library.gpLibrary.infrastructure.interfaces.IPopulationManager;
import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;

public interface IPopulationManagerFactory<T> {
    IPopulationManager<T> createNew(ITreeGenerator<T> generator, IFitnessFunction<T> function, long seed);
}
