package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class TotalDistanceCovered extends TSPTerminal {

    public TotalDistanceCovered() {
        super("TDC");
    }

    protected TotalDistanceCovered(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {

        if(placedCities.size() == 0){
            value = 0;

        }else {
            double totalDistance = 0;
            TSNode currentCityInTravel = placedCities.get(0);

            for (int i = 1; i < placedCities.size(); i++) {
                TSNode nextCity = placedCities.get(i);

                totalDistance += currentCityInTravel.getDistanceTo(nextCity);
                currentCityInTravel = nextCity;
            }
            value =  totalDistance;
        }
    }

    @Override
    protected Node<Double> getCopy() {
        return new TotalDistanceCovered(this);
    }

}
