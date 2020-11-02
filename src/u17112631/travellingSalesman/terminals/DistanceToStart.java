package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class DistanceToStart extends TSPTerminal {

    public DistanceToStart() {
        super("DTS");
    }

    protected DistanceToStart(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {

        if(placedCities.size() == 0)
            value = 0;
        else
            value = currentCity.getDistanceTo(placedCities.get(0));
    }

    @Override
    protected Node<Double> getCopy() {
        return new DistanceToStart(this);
    }
}
