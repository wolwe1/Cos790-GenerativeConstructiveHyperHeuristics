package u17112631;

import library.gpLibrary.functionality.implementation.StatisticsManager;
import library.gpLibrary.helpers.Printer;
import library.gpLibrary.helpers.UIController;
import library.gpLibrary.infrastructure.abstractClasses.GeneticAlgorithm;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.models.highOrder.GeneticAlgorithmSummary;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;
import library.gpLibrary.models.highOrder.implementation.PopulationStatistics;
import library.gpLibrary.models.highOrder.interfaces.IMemberStatistics;
import library.gpLibrary.models.primitives.enums.PrintLevel;
import library.gpLibrary.setup.factories.implementations.GeneticAlgorithmFactory;
import library.helpers.FileManager;
import u17112631.travellingSalesman.DataConverter;
import u17112631.travellingSalesman.TSPFitnessFunctionFactory;
import u17112631.travellingSalesman.TSPPopulationManagerFactory;
import u17112631.travellingSalesman.TSPTreeGeneratorFactory;
import u17112631.travellingSalesman.infrastructure.TSPConfig;
import u17112631.travellingSalesman.primitives.TSProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithmRunner<T> {

    public void runMonty(){
        String[] executionFiles = {"symmetric\\city\\ch130.tsp","symmetric\\city\\ch150.tsp",
                "symmetric\\city\\eil101.tsp","symmetric\\city\\eil51.tsp","symmetric\\city\\eil76.tsp"};

        List<GeneticAlgorithmSummary<Double>> executionBests = new ArrayList<>();

        for (String executionFile : executionFiles) {
            System.out.println("Running on " + executionFile);
            executionBests.add(run(executionFile));
        }

        System.out.println("Execution results");
        summariseResults(executionBests);
    }

    public GeneticAlgorithmSummary<Double> run(String file){
        GeneticAlgorithmFactory<Double> factory = createFactory(file);
        return runOnDataSet(factory);
    }

    private GeneticAlgorithmSummary<Double> runOnDataSet(GeneticAlgorithmFactory<Double> factory) {

        GeneticAlgorithm<Double> geneticAlgorithm = factory.createGeneticAlgorithm();

        List<GeneticAlgorithmSummary<Double>> bestMembersFromEachRun = performExecution(factory,geneticAlgorithm);

        Printer.print("Execution completed");
        IFitnessFunction<Double> fitnessFunction = geneticAlgorithm.getFitnessFunction();
        int bestRunIndex = getBestPerformerOfRun(fitnessFunction,bestMembersFromEachRun);

        GeneticAlgorithmSummary<Double> bestMemberOfExecution = bestMembersFromEachRun.get(bestRunIndex);

        printExecutionInformation(bestRunIndex,bestMemberOfExecution,bestMembersFromEachRun);

        return bestMemberOfExecution;
    }

    private void printExecutionInformation(int bestRunIndex, GeneticAlgorithmSummary<Double> bestMemberFromExecution, List<GeneticAlgorithmSummary<Double>> runResults) {

        Printer.print("Best performer from execution");
        Printer.print(bestMemberFromExecution.bestPerformer.getId() + " : " + bestMemberFromExecution.bestPerformer.getFitness());
        Printer.print("Found in run: " + (bestRunIndex + 1));
        Printer.underline();


        summariseResults(runResults);
    }

    private void summariseResults(List<GeneticAlgorithmSummary<Double>> runResults) {
        List<IMemberStatistics<Double>> runStats = runResults.stream().map(GeneticAlgorithmSummary::getStats).collect(Collectors.toList());
        PopulationStatistics<Double> averageOfRuns = StatisticsManager.calculateAverages(runStats);
        PopulationStatistics<Double> standardDeviationOfRuns = StatisticsManager.calculateStandardDeviation(runStats);
        averageOfRuns.print();
        standardDeviationOfRuns.print();
    }


    private List<GeneticAlgorithmSummary<Double>> performExecution(GeneticAlgorithmFactory<Double> factory, GeneticAlgorithm<Double> geneticAlgorithm) {

        List<GeneticAlgorithmSummary<Double>> runStats = new ArrayList<>();

        PrintLevel printLevel = geneticAlgorithm.getPrintLevel();
        for (int i = 0; i < factory.getConfig().getNumberOfRuns(); i++) {

            if(printLevel != PrintLevel.NONE){
                Printer.print("Run " + i);
                Printer.underline();
            }

            geneticAlgorithm.setSeed(i);
            runStats.add(geneticAlgorithm.run());

            if(printLevel != PrintLevel.NONE)
                Printer.underline();

        }
        return runStats;
    }

    private int getBestPerformerOfRun(IFitnessFunction<Double> fitnessFunction, List<GeneticAlgorithmSummary<Double>> bestMembersFromEachRun) {
        int bestRun = 0;
        double bestFitness = fitnessFunction.getWorstPossibleValue();

        for (int i = 1, bestMembersFromEachRunSize = bestMembersFromEachRun.size(); i < bestMembersFromEachRunSize; i++) {
            PopulationMember<Double> bestMemberFromRun = bestMembersFromEachRun.get(i).bestPerformer;

            if (fitnessFunction.firstFitterThanSecond(bestMemberFromRun.getFitness(), bestFitness)) {
                bestFitness = bestMemberFromRun.getFitness();
                bestRun = i;
            }
        }

        return bestRun;
    }

    private GeneticAlgorithmFactory<Double> createFactory(String fileName) {
        FileManager fileManager = new FileManager(new DataConverter());
        UIController ui = new UIController(true,fileManager);

        DataConverter converter = fileManager.loadDataConverter(fileName);

        TSProblem problem = converter.getProblem();
        GeneticAlgorithmFactory<Double> factory = new GeneticAlgorithmFactory<>(
                new TSPFitnessFunctionFactory<>(problem),
                new TSPTreeGeneratorFactory(),
                new TSPPopulationManagerFactory<>(),
                new TSPConfig(ui));

        return factory;
    }
}
