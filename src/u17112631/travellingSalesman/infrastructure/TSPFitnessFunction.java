package u17112631.travellingSalesman.infrastructure;

import library.gpLibrary.infrastructure.implementation.FitnessFunction;
import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.highOrder.implementation.PopulationStatistics;
import library.gpLibrary.models.highOrder.interfaces.IMemberStatistics;
import u17112631.travellingSalesman.primitives.TSNode;
import u17112631.travellingSalesman.primitives.TSProblem;

import java.util.ArrayList;
import java.util.List;

public class TSPFitnessFunction<T> extends FitnessFunction<T> {

    private final TSProblem problem;

    public TSPFitnessFunction(TSProblem problem) {
        this.problem = problem;
    }

    @Override
    public double getWorstPossibleValue() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public IMemberStatistics<Double> calculateFitness(NodeTree<T> populationMember) {

        TSPTree tree = (TSPTree) populationMember;
        List<TSNode> cities = problem.getNodes();
        List<TSNode> placedCities = new ArrayList<>();

        TSNode lastPlacedCity = null;

        //While there are cities to allocate
        while (cities.size() > 0){

            int indexOfNextCityToPlace = getNextCityToPlace(tree,lastPlacedCity,placedCities,cities);

            lastPlacedCity = cities.get(indexOfNextCityToPlace);
            placedCities.add(lastPlacedCity);
            cities.remove(indexOfNextCityToPlace);
        }

        double totalDistance = getTotalDistanceTravelled(placedCities);

        PopulationStatistics<Double> stats = new PopulationStatistics<>();
        stats.setMeasure("Distance",totalDistance);
        stats.setFitness("Distance");

        return stats;
    }

    private double getTotalDistanceTravelled(List<TSNode> placedCities) {
        double totalDistance = 0;
        TSNode currentCityInTravel = placedCities.get(0);

        for (int i = 1; i < placedCities.size(); i++) {
            TSNode nextCity = placedCities.get(i);

            totalDistance += currentCityInTravel.getDistanceTo(nextCity);
            currentCityInTravel = nextCity;
        }
        return totalDistance;
    }

    private int getNextCityToPlace(TSPTree tree,TSNode lastPlacedCity,List<TSNode> placedCities, List<TSNode> unplacedCities) {
        double highestPriority = Double.POSITIVE_INFINITY;
        int nextCityIndex = 0;
        //For each remaining city, use the tree to get their priority of placement
        for (int i = 0, unplacedCitiesSize = unplacedCities.size(); i < unplacedCitiesSize; i++) {
            TSNode currentCity = unplacedCities.get(i);

            //Set the TSP specific nodes' values based on what is the current city
            tree.setTerminalValues(lastPlacedCity,currentCity,placedCities,unplacedCities);

            double priorityForCity = tree.getValue();
            if (priorityForCity < highestPriority) {
                highestPriority = priorityForCity;
                nextCityIndex = i;
            }
        }

        return nextCityIndex;
    }

    @Override
    public boolean firstFitterThanSecond(double firstFitness, double secondFitness) {
        return firstFitness < secondFitness;
    }

    @Override
    public void useTrainingSet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void useTestingSet() {
        throw new RuntimeException("Not implemented");
    }
}
