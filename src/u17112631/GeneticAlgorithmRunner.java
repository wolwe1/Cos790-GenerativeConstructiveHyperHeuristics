package u17112631;

import library.gpLibrary.functionality.implementation.StatisticsManager;
import library.gpLibrary.helpers.Printer;
import library.gpLibrary.helpers.UIController;
import library.gpLibrary.infrastructure.abstractClasses.GeneticAlgorithm;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
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
import java.util.Map;

public class GeneticAlgorithmRunner<T> {


    public void run(){
        GeneticAlgorithmFactory<Double> factory = createFactory();

        runOnDataSet(factory);

    }

    private void runOnDataSet(GeneticAlgorithmFactory<Double> factory) {

        GeneticAlgorithm<Double> geneticAlgorithm = factory.createGeneticAlgorithm();

        List<PopulationMember<Double>> bestMembersFromEachRun = performExecution(factory,geneticAlgorithm);
        List<IMemberStatistics<Double>> runStats = getExecutionStatistics(bestMembersFromEachRun);

        Printer.print("Execution completed");
        IFitnessFunction<Double> fitnessFunction = geneticAlgorithm.getFitnessFunction();
        int bestMemberFromExecution = getBestPerformerOfRun(fitnessFunction,bestMembersFromEachRun);

        PopulationMember<Double> bestMemberOfExecution = bestMembersFromEachRun.get(bestMemberFromExecution);

        printExecutionInformation(bestMemberFromExecution,bestMemberOfExecution,runStats);
    }

    private void printExecutionInformation(int bestRunIndex,PopulationMember<Double> bestMemberFromExecution, List<IMemberStatistics<Double>> runStats) {

        Printer.print("Best performer from execution");
        Printer.print(bestMemberFromExecution.getId() + " : " + bestMemberFromExecution.getFitness());
        Printer.print("Found in run: " + bestRunIndex);
        Printer.underline();

        PopulationStatistics<Double> averageOfRuns = StatisticsManager.calculateAverages(runStats);
        PopulationStatistics<Double> standardDeviationOfRuns = StatisticsManager.calculateStandardDeviation(runStats);
        averageOfRuns.print();
        standardDeviationOfRuns.print();
    }

    private List<IMemberStatistics<Double>> getExecutionStatistics(List<PopulationMember<Double>> bestMembersFromEachRun) {

        List<IMemberStatistics<Double>> runStats = new ArrayList<>();
        for (PopulationMember<Double> member : bestMembersFromEachRun) {

            PopulationStatistics<Double> runStat = new PopulationStatistics<>();
            runStat.setMeasure("Run best fitness",member.getFitness());
            runStats.add(runStat);
        }
        return runStats;
    }

    private List<PopulationMember<Double>> performExecution(GeneticAlgorithmFactory<Double> factory, GeneticAlgorithm<Double> geneticAlgorithm) {

        List<PopulationMember<Double>> bestMembersFromEachRun = new ArrayList<>();
        List<PopulationStatistics<IMemberStatistics>> runStats = new ArrayList<>();

        PrintLevel printLevel = geneticAlgorithm.getPrintLevel();
        for (int i = 0; i < factory.getConfig().getNumberOfRuns(); i++) {

            if(printLevel != PrintLevel.NONE){
                Printer.print("Run " + i);
                Printer.underline();
            }

            geneticAlgorithm.setSeed(i);
            PopulationMember<Double> bestOfRun = geneticAlgorithm.run();
            PopulationStatistics<IMemberStatistics> runStat = geneticAlgorithm.getRunStats();
            runStats.add(runStat);
            bestMembersFromEachRun.add(bestOfRun);

            if(printLevel != PrintLevel.NONE)
                Printer.underline();

        }

        for (PopulationStatistics<IMemberStatistics> runStat : runStats) {

            for (Map.Entry<String, IMemberStatistics> runStatistics : runStat.getMeasures().entrySet()) {
                IMemberStatistics aRunsStats = runStatistics.getValue();

            }
        }
        return bestMembersFromEachRun;
    }

    private int getBestPerformerOfRun(IFitnessFunction<Double> fitnessFunction, List<PopulationMember<Double>> bestMembersFromEachRun) {
        int bestRun = 1;
        double bestFitness = fitnessFunction.getWorstPossibleValue();

        for (int i = 1, bestMembersFromEachRunSize = bestMembersFromEachRun.size(); i < bestMembersFromEachRunSize; i++) {
            PopulationMember<Double> bestMemberFromRun = bestMembersFromEachRun.get(i);

            if (fitnessFunction.firstFitterThanSecond(bestMemberFromRun.getFitness(), bestFitness)) {
                bestFitness = bestMemberFromRun.getFitness();
                bestRun = i;
            }
        }

        return bestRun;
    }

    private GeneticAlgorithmFactory<Double> createFactory() {
        FileManager fileManager = new FileManager(new DataConverter());
        UIController ui = new UIController(true,fileManager);

        DataConverter converter = fileManager.loadDataConverter();

        TSProblem problem = converter.getProblem();
        GeneticAlgorithmFactory<Double> factory = new GeneticAlgorithmFactory<>(
                new TSPFitnessFunctionFactory<>(problem),
                new TSPTreeGeneratorFactory(),
                new TSPPopulationManagerFactory<>(),
                new TSPConfig(ui));

        return factory;
    }
}
