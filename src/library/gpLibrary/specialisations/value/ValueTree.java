package library.gpLibrary.specialisations.value;

import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;

import java.util.List;

public class ValueTree<T> extends NodeTree<T> {

    ValueNode<T> root;

    public ValueTree(int maxDepth,int maxBreadth){
        super(maxDepth,maxBreadth);
    }

    public ValueTree(ValueTree<T> other) {
        super(other);

        setRoot(other.getRoot().getCopy(true));
    }

    @Override
    protected void setRoot(Node<T> node) {
        root = (ValueNode<T>) node;
    }

    @Override
    public NodeTree<T> getCopy() {
        return new ValueTree<>(this);
    }

    @Override
    public boolean isFull() {
        if(root == null)
            return false;

        return !root.canTakeMoreChildren();
    }

    @Override
    public boolean requiresTerminals() {

        if(root == null)
            return false;

        return root.requiresTerminals(maxDepth - 1);
    }

    @Override
    public ValueNode<T> getRoot() {
        return root;
    }

    @Override
    public boolean acceptsNode(Node<T> nodeToAdd) {
        return true;
    }

    @Override
    public void addNodes(List<? extends Node<T>> nodesToLoad) {

        for (Node<T> newNode : nodesToLoad) {
            try {
                addNode(newNode);
            } catch (Exception e) {
                throw new RuntimeException("Unable to add node");
            }
        }
    }

    @Override
    public void cutTree(int maxDepthIncrease) {
        root.cutNodes(maxDepth + maxDepthIncrease);
    }
}
