package u17112631.travellingSalesman.infrastructure;

import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;
import u17112631.travellingSalesman.TSPTerminal;
import u17112631.travellingSalesman.primitives.TSNode;

import java.util.List;

public class TSPTree extends NodeTree<Double> {

    ValueNode<Double> root;

    public TSPTree(int maxDepth,int maxBreadth){
        super(maxDepth,maxBreadth);
    }

    public TSPTree(TSPTree other) {
        super(other);
        root = (ValueNode<Double>) other.root.getCopy(true);
    }

    @Override
    protected void setRoot(Node<Double> node) {
        root = (ValueNode<Double>) node;
    }

    @Override
    public NodeTree<Double> getCopy() {
        return new TSPTree(this);
    }

    @Override
    public boolean isFull() {
        if(root == null)
            return false;

        return !root.canTakeMoreChildren();
    }

    @Override
    public boolean requiresTerminals() {
        return root.requiresTerminals(maxDepth - 1);
    }

    @Override
    public ValueNode<Double> getRoot() {
        return root;
    }

    @Override
    public boolean acceptsNode(Node<Double> nodeToAdd) {
        return true;
    }

    @Override
    public void addNodes(List<? extends Node<Double>> nodesToLoad) {
        for (Node<Double> node : nodesToLoad) {
            try {
                addNode(node);
            } catch (Exception e) {
                throw new RuntimeException("Could not load nodes");
            }
        }
    }

    @Override
    public void cutTree(int maxDepthIncrease) {
        root.cutNodes(maxDepth + maxDepthIncrease);
    }

    public void setTerminalValues(TSNode lastCityPlaced, TSNode currentCity, List<TSNode> placedCities, List<TSNode> unplacedCities) {
        List<Node<Double>> terminalNodes = root.getTerminalNodes();

        for (Node<Double> terminalNode : terminalNodes) {
            TSPTerminal attribute = (TSPTerminal) terminalNode;
            attribute.setValue(lastCityPlaced,currentCity,placedCities,unplacedCities);
        }
    }

    public Double getValue() {
        return root.getValue();
    }
}
