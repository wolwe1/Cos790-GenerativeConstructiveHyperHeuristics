package library.gpLibrary.setup.factories.implementations;

import library.gpLibrary.infrastructure.abstractClasses.GeneticAlgorithm;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.infrastructure.interfaces.IPopulationManager;
import library.gpLibrary.infrastructure.interfaces.ITreeGenerator;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import library.gpLibrary.setup.factories.IFitnessFunctionFactory;
import library.gpLibrary.setup.factories.IPopulationManagerFactory;
import library.gpLibrary.setup.factories.ITreeGeneratorFactory;

public class GeneticAlgorithmFactory<T> {

    private final GeneticAlgorithmConfig<T> config;
    private final IFitnessFunctionFactory<T> fitnessFunctionFactory;
    private final ITreeGeneratorFactory<T> treeGeneratorFactory;
    private final IPopulationManagerFactory<T> populationManagerFactory;

    public GeneticAlgorithmFactory(IFitnessFunctionFactory<T> fitnessFunctionFactory, ITreeGeneratorFactory<T> treeGeneratorFactory,IPopulationManagerFactory<T> popMan,GeneticAlgorithmConfig<T> conf){
        this.fitnessFunctionFactory = fitnessFunctionFactory;
        this.treeGeneratorFactory = treeGeneratorFactory;
        this.populationManagerFactory = popMan;
        this.config = conf;
    }

    public GeneticAlgorithm<T> createGeneticAlgorithm(){

        if(!config.isConfigured())
            config.setup();

        return createNew();
    }



    private GeneticAlgorithm<T> createNew() {

        IPopulationManager<T> manager = createNewPopulationManager();

        GeneticAlgorithm<T> geneticAlgorithm = new GeneticAlgorithm<>(
                config.getPopulationSize(),config.getNumberOfGenerations(),manager);

        geneticAlgorithm.setPrintLevel(config.getPrintLevel());

        return geneticAlgorithm;
    }

    private IPopulationManager<T> createNewPopulationManager() {

        ITreeGenerator<T> generator = getTreeGenerator();
        IFitnessFunction<T> function = getFitnessFunction();

        return populationManagerFactory.createNew(generator,function,config.getSeed());
    }

    private IFitnessFunction<T> getFitnessFunction() {
        return fitnessFunctionFactory.createNew(config);
    }

    private ITreeGenerator<T> getTreeGenerator() {
        return treeGeneratorFactory.createNew(config);
    }

    public GeneticAlgorithmConfig<T> getConfig() {
        return this.config;
    }
}
