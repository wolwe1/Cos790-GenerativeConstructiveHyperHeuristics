package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class FurthestUnplacedCityFrom extends TSPTerminal {

    public FurthestUnplacedCityFrom() {
        super("FUCF");
    }

    protected FurthestUnplacedCityFrom(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        double highestDistance = Double.NEGATIVE_INFINITY;

        for (TSNode unplacedCity : unplacedCities) {

            if(!currentCity.equals(unplacedCity)){
                double distanceToCity = currentCity.getDistanceTo(unplacedCity);
                if(distanceToCity > highestDistance)
                    highestDistance = distanceToCity;
            }
        }

        value = highestDistance;
    }

    @Override
    protected Node<Double> getCopy() {
        return new FurthestUnplacedCityFrom(this);
    }

}
