package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class NumberOfCitiesUnplaced extends TSPTerminal {

    public NumberOfCitiesUnplaced() {
        super("NCU");
    }

    protected NumberOfCitiesUnplaced(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        value = unplacedCities.size();
    }

    @Override
    protected Node<Double> getCopy() {
        return new NumberOfCitiesUnplaced(this);
    }

}
