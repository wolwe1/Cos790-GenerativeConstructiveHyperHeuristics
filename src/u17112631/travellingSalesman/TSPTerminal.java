package u17112631.travellingSalesman;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public abstract class TSPTerminal extends ValueNode<Double> {

    protected double value;

    protected TSPTerminal(String name) {
        super(name);
        value = 0;
        _maxChildren = 0;
    }

    protected TSPTerminal(Node<Double> other) {
        super(other);
    }

    @Override
    public boolean isFull() {
        return true;
    }

    @Override
    public boolean canTakeMoreChildren() {
        return false;
    }

    @Override
    public boolean requiresTerminals(int maxDepth) {
       return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int countLeaves() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setChildAtFirstTerminal(Node<Double> nodeInMain) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void cutNodes(int i) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Double getBaseValue() {
        return 0d;
    }

    public abstract void setValue(TSNode lastCityPlaced,TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities);

}
