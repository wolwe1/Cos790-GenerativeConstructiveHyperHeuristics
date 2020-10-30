package u17112631;

public class Main {

    public static void main(String[] args) {
	// write your code here

        GeneticAlgorithmRunner<Double> runner = new GeneticAlgorithmRunner<>();

        runner.run();


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
