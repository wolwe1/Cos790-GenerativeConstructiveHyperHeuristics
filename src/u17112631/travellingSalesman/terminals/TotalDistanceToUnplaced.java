package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class TotalDistanceToUnplaced extends TSPTerminal {

    public TotalDistanceToUnplaced() {
        super("TDU");
    }

    protected TotalDistanceToUnplaced(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        double totalDistance = 0;

        for (TSNode unplacedCity : unplacedCities) {

            if(!currentCity.equals(unplacedCity))
                totalDistance += currentCity.getDistanceTo(unplacedCity);
        }
        value = totalDistance;
    }

    @Override
    protected Node<Double> getCopy() {
        return new TotalDistanceToUnplaced(this);
    }

}
