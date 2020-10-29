package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class ClosestUnplacedCityTo extends TSPTerminal {

    public ClosestUnplacedCityTo() {
        super("CUC");
    }

    protected ClosestUnplacedCityTo(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        double smallestDistance = Double.POSITIVE_INFINITY;

        for (TSNode unplacedCity : unplacedCities) {
            double distanceToCity = currentCity.getDistanceTo(unplacedCity);
            if(distanceToCity < smallestDistance)
                smallestDistance = distanceToCity;
        }

        value = smallestDistance;
    }

    @Override
    protected Node<Double> getCopy() {
        return new ClosestUnplacedCityTo(this);
    }


}
