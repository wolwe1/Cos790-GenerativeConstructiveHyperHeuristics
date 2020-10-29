package u17112631;

import library.gpLibrary.helpers.Printer;
import library.gpLibrary.helpers.UIController;
import library.gpLibrary.infrastructure.abstractClasses.GeneticAlgorithm;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;
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

public class Main {

    public static void main(String[] args) {
	// write your code here

        //long SEED = 1;
        FileManager fileManager = new FileManager(new DataConverter());
        UIController ui = new UIController(true,fileManager);

        DataConverter converter = fileManager.loadDataConverter();

        TSProblem problem = converter.getProblem();
        GeneticAlgorithmFactory<Double> factory = new GeneticAlgorithmFactory<>(
                new TSPFitnessFunctionFactory<>(problem),
                new TSPTreeGeneratorFactory(),
                new TSPPopulationManagerFactory<>(),
                new TSPConfig(ui));

        GeneticAlgorithm<Double> geneticAlgorithm = factory.createGeneticAlgorithm();

        List<PopulationMember<Double>> bestMembersFromEachRun = new ArrayList<>();

        for (int i = 0; i < factory.getConfig().getNumberOfRuns(); i++) {
            Printer.print("Run " + i);
            Printer.underline();
            geneticAlgorithm.setSeed(i);
            bestMembersFromEachRun.add(geneticAlgorithm.run());
            Printer.underline();
        }

//        IFitnessFunction<Double> fitnessFunction = geneticAlgorithm.getFitnessFunction();
//
//        List<IMemberStatistics<Double>> stats = new ArrayList<>();
//        List<IMemberStatistics<Double>> generelisationStats = new ArrayList<>();
//
//        for (PopulationMember<Double> runWinner : bestMembersFromEachRun) {
//            stats.add( fitnessFunction.calculateFitness(runWinner.getTree()));
//        }
//
//        fitnessFunction.useTestingSet();
//
//        for (PopulationMember<Double> runWinner : bestMembersFromEachRun) {
//            generelisationStats.add( fitnessFunction.calculateFitness(runWinner.getTree()));
//        }
//
//        Printer.print("Best performers");
//        Printer.underline();
//
//        Printer.print("Training");
//        for (int i = 1; i < stats.size(); i++) {
//            IMemberStatistics<Double> stat = stats.get(i);
//            Printer.print("Run " + i + ":" + bestMembersFromEachRun.get(i).getId());
//            stat.print();
//        }
//
//        Printer.print("Testing");
//        for (int i = 0; i < generelisationStats.size(); i++) {
//            IMemberStatistics<Double> stat = generelisationStats.get(i);
//            Printer.print("Run " + i + ":" + bestMembersFromEachRun.get(i).getId());
//            stat.print();
//        }


    }
}
