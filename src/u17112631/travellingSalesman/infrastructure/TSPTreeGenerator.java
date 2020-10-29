package u17112631.travellingSalesman.infrastructure;

import library.gpLibrary.infrastructure.abstractClasses.TreeGenerator;
import library.gpLibrary.models.highOrder.implementation.FunctionalSet;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;
import library.gpLibrary.models.highOrder.implementation.TerminalSet;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TSPTreeGenerator extends TreeGenerator<Double> {


    private final int maxDepthIncrease;
    private int maxDepth;
    private int maxBreadth;

    public TSPTreeGenerator(FunctionalSet<Double> functionalSet, TerminalSet<Double> terminals, int maxDepthIncrease){
        super(functionalSet,terminals);
        this.maxDepthIncrease = maxDepthIncrease;
    }

    public void setDepths(int maxDepth,int maxBreadth){
        this.maxDepth = maxDepth;
        this.maxBreadth = maxBreadth;
    }

    @Override
    public TSPTree createRandom() {
        TSPTree  newTree = new TSPTree(maxDepth,maxBreadth);

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
    public TSPTree create(String chromosome) {
        String[] chromosomes = chromosome.split("\\|");
        String[] joinedChromosomes = Stream.concat(Arrays.stream(chromosomes[0].split("\\.")),
                Arrays.stream(chromosomes[1].split("\\.")))
                .toArray(String[]::new);

        TSPTree newTree = new TSPTree(maxDepth,maxBreadth);

        insertChromosomeMembersIntoTree(newTree,joinedChromosomes);

        return newTree;
    }

    @Override
    public TSPTree replaceSubTree(PopulationMember<Double> chromosome) {

        TSPTree tree = (TSPTree) chromosome.getTree();
        int pointToReplace = randomGenerator.nextInt(tree.getSize() - 1) + 1;

        Node<Double> replacingNode = pickNode();

        tree.replaceNode(pointToReplace,replacingNode);
        fillTree(tree);

        if(!tree.isValid())
            throw new RuntimeException("Created invalid tree");

        return tree;
    }

    private void cutTree(TSPTree tree) {
        tree.cutTree(maxDepthIncrease);
    }

    @Override
    public List<TSPTree> swapSubTrees(PopulationMember<Double> first, PopulationMember<Double> second) {

        TSPTree firstTree = (TSPTree) first.getTree();
        TSPTree secondTree = (TSPTree) second.getTree();

        int pointToReplaceInFirst = randomGenerator.nextInt(firstTree.getSize() - 1) + 1;
        int pointToReplaceInSecond = randomGenerator.nextInt(secondTree.getSize() - 1) + 1;

        Node<Double> firstSubtree = firstTree.getNode(pointToReplaceInFirst).getCopy(true);
        Node<Double> secondSubTree = secondTree.getNode(pointToReplaceInSecond).getCopy(true);


        firstTree.replaceNode(pointToReplaceInFirst,secondSubTree);
        secondTree.replaceNode(pointToReplaceInSecond,firstSubtree);

        if(!firstTree.isValid() || !secondTree.isValid()){
            throw new RuntimeException("Invalid tree created");
        }

        List<TSPTree> newTrees = new ArrayList<>();
        newTrees.add(firstTree);
        newTrees.add(secondTree);

        return newTrees;
    }
}
