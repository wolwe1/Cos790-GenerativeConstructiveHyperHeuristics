package library.gpLibrary.specialisations.ADF.infrastructure;

import library.gpLibrary.functionality.implementation.TreeCombinationVisitor;
import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;

import java.util.ArrayList;
import java.util.List;

public class ADFFuncDefinition<T> extends NodeTree<T> {

    List<T> arguments;
    ADFunction<T> function;

    protected ADFFuncDefinition(int maxDepth,int maxBreadth) {
        super(maxDepth,maxBreadth);

        arguments = new ArrayList<>();
        function = new ADFunction<>("ADF0");

    }

    public ADFFuncDefinition(ADFFuncDefinition<T> other) {
        super(other.maxDepth,other.maxBreadth);
        arguments = other.arguments;
        function = other.function.getCopy();
    }

    @Override
    protected void setRoot(Node<T> node) {
        function.setRoot(node);
    }

    @Override
    public ADFFuncDefinition<T> getCopy() {
        return new ADFFuncDefinition<>(this);
    }

    @Override
    public boolean isFull() {
        return function.isFull();
    }

    @Override
    public boolean requiresTerminals() {
        return function.requiresTerminals(maxDepth);
    }

    @Override
    public ValueNode<T> getRoot() {
        return function.root;
    }

    @Override
    public boolean acceptsNode(Node<T> nodeToAdd) {
        return true;
    }

    @Override
    public void addNodes(List<? extends Node<T>> nodesToLoad) {
        for (Node<T> node : nodesToLoad) {
            try {
                function.root.addChild(node);
            } catch (Exception e) {
                throw new RuntimeException("Unable to load nodes");
            }
        }
        numberOfNodes += nodesToLoad.size();
    }

    public ADFunction<T> getFunction() {
        return function;
    }

    public void setFunctionComposition(){
        TreeCombinationVisitor<T> visitor = new TreeCombinationVisitor<>();
        visitTree(visitor);

        String composition = visitor.getCombination();
        function.setComposition(composition);

    }

    public void cutTree(int maxDepthIncrease) {
        function.root.cutNodes(maxDepth + maxDepthIncrease);
    }
}
