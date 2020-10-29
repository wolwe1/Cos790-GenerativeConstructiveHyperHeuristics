package library.gpLibrary.specialisations.value;

import library.gpLibrary.helpers.MathHelp;
import library.gpLibrary.infrastructure.implementation.FitnessFunction;
import library.gpLibrary.models.highOrder.implementation.NodeTree;
import library.gpLibrary.models.highOrder.implementation.PopulationStatistics;
import library.gpLibrary.models.highOrder.interfaces.IMemberStatistics;
import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValueFitnessFunction extends FitnessFunction<Double> {

    private final List<ValueNode<Double>> trainingSet;
    private final List<ValueNode<Double>> testingSet;
    private List<ValueNode<Double>> currentSet;
    private final int _lookAhead;
    private ValueNode<Double> constant;

    public ValueFitnessFunction(int lookAhead,List<ValueNode<Double>> trainingSet,List<ValueNode<Double>> testingSet){
        _lookAhead = lookAhead;
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
        this.currentSet = this.trainingSet;
    }

    @Override
    public double getWorstPossibleValue() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public IMemberStatistics<Double> calculateFitness(NodeTree<Double> populationMember) {
        int numberOfDataPointsTreeCanContain = populationMember.getNumberOfLeafNodes();
        int startIndexOfComparisons =  numberOfDataPointsTreeCanContain + _lookAhead;

        List<Double> answerSet = currentSet.stream().map(ValueNode::getValue)
                .collect(Collectors.toList()).subList(startIndexOfComparisons,currentSet.size());

        List<Double> treeAnswers = new ArrayList<>();

        //Perform a sliding window comparison
        int endPointForTest = currentSet.size() - (numberOfDataPointsTreeCanContain + _lookAhead);
        for (int i = 0; i < endPointForTest; i++) {

            populationMember.clearLeaves();
            //Load tree with data points
            int lastIndexOfDayToUse = i + numberOfDataPointsTreeCanContain;
            if(constant != null)
                lastIndexOfDayToUse--;

            double treeAnswer = getTreeAnswerOnNextSetOfData(populationMember,currentSet.subList(i,lastIndexOfDayToUse));

            treeAnswers.add(treeAnswer);

        }

        //populationMember.clearLeaves();
        return calculateTreePerformance(treeAnswers,answerSet);
    }

    private IMemberStatistics<Double> calculateTreePerformance(List<Double> treeAnswers, List<Double> answerSet) {
        IMemberStatistics<Double> treePerformance = new PopulationStatistics<>();
        double mae = 0;
        double mse = 0;

        for (int i = 0, treeAnswersSize = treeAnswers.size(); i < treeAnswersSize; i++) {
            Double treeAnswer = treeAnswers.get(i);
            Double answer = answerSet.get(i);
            mae += Math.abs(treeAnswer - answer);
            mse += Math.pow((treeAnswer - answer),2);
        }

        treePerformance.setMeasure("MAE", MathHelp.easyRound(4,mae));
        treePerformance.setMeasure("Avg MAE", MathHelp.easyRound(4,mae/treeAnswers.size()));
        treePerformance.setMeasure("MSE",MathHelp.easyRound(4,mse));
        treePerformance.setFitness("Avg MAE");
        return treePerformance;
    }

    private double getTreeAnswerOnNextSetOfData(NodeTree<Double> populationMember, List<ValueNode<Double>> nodesToLoad) {

        try {
            populationMember.addNodes(nodesToLoad);
            if(constant != null)
                populationMember.addNode(constant);
        } catch (Exception e) {
            throw new RuntimeException("Error loading nodes into tree");
        }

        return ( (ValueNode<Double>)populationMember.getRoot()).getValue();
    }

    @Override
    public boolean firstFitterThanSecond(double firstFitness, double secondFitness) {
        return firstFitness < secondFitness;
    }

    @Override
    public void useTrainingSet() {
        this.currentSet = this.trainingSet;
    }

    @Override
    public void useTestingSet() {
        this.currentSet = this.testingSet;
    }

    public void setConstant(ValueNode<Double> constant) {
        this.constant = constant;
    }
}
