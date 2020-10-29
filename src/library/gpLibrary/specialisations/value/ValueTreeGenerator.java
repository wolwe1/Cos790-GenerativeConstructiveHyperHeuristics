package library.gpLibrary.specialisations.value;


import library.gpLibrary.infrastructure.abstractClasses.TreeGenerator;
import library.gpLibrary.models.highOrder.implementation.FunctionalSet;
import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;
import library.gpLibrary.models.highOrder.implementation.TerminalSet;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ValueTreeGenerator<T> extends TreeGenerator<T> {

    private final int maxDepthIncrease;
    private int maxDepth;
    private int maxBreadth;

    public ValueTreeGenerator(FunctionalSet<T> functionalSet, TerminalSet<T> terminals, int maxDepthIncrease){
        super(functionalSet,terminals);
        this.maxDepthIncrease = maxDepthIncrease;
    }

    public void setDepths(int maxDepth,int maxBreadth){
        this.maxDepth = maxDepth;
        this.maxBreadth = maxBreadth;
    }

    @Override
    public ValueTree<T> createRandom() {
        ValueTree<T> newTree = new ValueTree<>(maxDepth,maxBreadth);

        try {
            newTree.addNode(pickFunction());
        }catch (Exception e){
            throw new RuntimeException("Unable to create tree root");
        }

        fillTree(newTree);

        if(!newTree.isValid())
            throw new RuntimeException("Created invalid tree");

        return newTree;
    }

    @Override
    public NodeTree<T> create(String chromosome) {
        String[] chromosomes = chromosome.split("\\|");
        String[] joinedChromosomes = Stream.concat(Arrays.stream(chromosomes[0].split("\\.")),
                Arrays.stream(chromosomes[1].split("\\.")))
                .toArray(String[]::new);

        ValueTree<T> newTree = new ValueTree<>(maxDepth,maxBreadth);

        insertChromosomeMembersIntoTree(newTree,joinedChromosomes);

        return newTree;
    }

    @Override
    public ValueTree<T> replaceSubTree(PopulationMember<T> chromosome) {

        ValueTree<T> tree = (ValueTree<T>) chromosome.getTree();
        int pointToReplace = randomGenerator.nextInt(tree.getSize() - 1) + 1;

        Node<T> replacingNode = pickNode();

        tree.replaceNode(pointToReplace,replacingNode);
        fillTree(tree);

        if(!tree.isValid())
            throw new RuntimeException("Created invalid tree");

        return tree;
    }

    private void cutTree(NodeTree<T> tree) {
        tree.cutTree(maxDepthIncrease);
    }

    @Override
    public List<NodeTree<T>> swapSubTrees(PopulationMember<T> first, PopulationMember<T> second) {

        NodeTree<T> firstTree = first.getTree();
        NodeTree<T> secondTree = second.getTree();

        int pointToReplaceInFirst = randomGenerator.nextInt(firstTree.getSize() - 1) + 1;
        int pointToReplaceInSecond = randomGenerator.nextInt(secondTree.getSize() - 1) + 1;

        Node<T> firstSubtree = firstTree.getNode(pointToReplaceInFirst).getCopy(true);
        Node<T> secondSubTree = secondTree.getNode(pointToReplaceInSecond).getCopy(true);


        firstTree.replaceNode(pointToReplaceInFirst,secondSubTree);
        secondTree.replaceNode(pointToReplaceInSecond,firstSubtree);

        if(!firstTree.isValid() || !secondTree.isValid()){
            throw new RuntimeException("Invalid tree created");
        }

        List<NodeTree<T>> newTrees = new ArrayList<>();
        newTrees.add(firstTree);
        newTrees.add(secondTree);

        return newTrees;
    }
}
