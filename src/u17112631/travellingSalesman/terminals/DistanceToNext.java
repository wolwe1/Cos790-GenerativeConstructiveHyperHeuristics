package u17112631.travellingSalesman.terminals;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class DistanceToNext extends TSPTerminal {

    public DistanceToNext() {
        super("D2N");
    }

    protected DistanceToNext(Node<Double> other) {
        super(other);
    }

    @Override
    public void setValue(TSNode lastPlacedCity,TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        if(lastPlacedCity == null)
            value = 0;
        else
            value = lastPlacedCity.getDistanceTo(currentCity);
    }

    @Override
    protected Node<Double> getCopy() {
        return new DistanceToNext(this);
    }


}
