package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class NumberOfCitiesPlaced extends TSPTerminal {

    public NumberOfCitiesPlaced() {
        super("NCP");
    }

    protected NumberOfCitiesPlaced(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        value = placedCities.size();
    }

    @Override
    protected Node<Double> getCopy() {
        return new NumberOfCitiesPlaced(this);
    }

}
