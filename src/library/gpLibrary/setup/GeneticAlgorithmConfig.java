package library.gpLibrary.setup;

import library.gpLibrary.helpers.UIController;
import library.gpLibrary.models.highOrder.implementation.FunctionalSet;
import library.gpLibrary.models.highOrder.implementation.TerminalSet;
import library.gpLibrary.models.primitives.enums.PrintLevel;
import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.specialisations.value.functions.AddFunction;
import library.gpLibrary.specialisations.value.functions.DivisionFunction;
import library.gpLibrary.specialisations.value.functions.MultiplicationFunction;
import library.gpLibrary.specialisations.value.functions.SubtractFunction;


public abstract class GeneticAlgorithmConfig<T> {

    private final UIController uiController;
    private long seed;
    private FunctionalSet<T> functionSet;
    private TerminalSet<T> terminalSet;
    private int maxIncrease;
    private int lookAhead;
    private int populationSize;
    private int numberOfGenerations;
    private int numberOfRuns;
    private int trainingSetPercentage;
    private int maxDepth;
    private int maxBreadth;
    private int terminalChance;

    private boolean isConfigured;
    private PrintLevel printLevel;

    public GeneticAlgorithmConfig(UIController UIController){
        this.uiController = UIController;
    }

    public long getSeed() {
        return this.seed;
    }

    public FunctionalSet<T> getFunctionSet() {
        return this.functionSet;
    }

    public TerminalSet<T> getTerminalSet() {
        return this.terminalSet;
    }

    public int getMaxIncrease() {
        return this.maxIncrease;
    }

    public int getLookAhead() {
        return this.lookAhead;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public int getNumberOfGenerations() {
        return this.numberOfGenerations;
    }

    public void setup() {

        seed = (long) uiController.askNumber("Seed",0);

        maxDepth = (int) uiController.askNumber("Maximum initial tree depth",5);
        maxBreadth = (int) uiController.askNumber("Maximum breadth",2);
        maxIncrease = (int) uiController.askNumber("Maximum depth increase",2);
        terminalChance = (int) uiController.askNumber("Terminal chance",0.2);

        lookAhead = (int) uiController.askNumber("Look Ahead",0);

        populationSize = (int) uiController.askNumber("Population Size",10);
        numberOfGenerations = (int) uiController.askNumber("Number of generations",15);
        numberOfRuns = (int) uiController.askNumber("Number of runs",1);
        trainingSetPercentage = (int) uiController.askNumber("Training set percentage",0.7);
        printLevel = (PrintLevel) uiController.askEnum("Printout level",PrintLevel.class,PrintLevel.MINOR);

        functionSet = createFunctionSet();
        terminalSet = createTerminalSet();

        isConfigured = true;

    }

    protected abstract TerminalSet<T> createTerminalSet();

    private FunctionalSet<T> createFunctionSet() {
        FunctionalSet<T> set = new FunctionalSet<>();
        set.addFunction((Node<T>) new AddFunction());
        set.addFunction((Node<T>) new SubtractFunction());
        set.addFunction((Node<T>) new MultiplicationFunction());
        set.addFunction((Node<T>) new DivisionFunction());

        return set;
    }

    public int getNumberOfRuns() {
        return this.numberOfRuns;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public int getMaxBreadth() {
        return this.maxBreadth;
    }

    public double getTerminalChance() {
        return terminalChance;
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public PrintLevel getPrintLevel() {
        return this.printLevel;
    }
}
