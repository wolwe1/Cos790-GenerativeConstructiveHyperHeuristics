package u17112631.travellingSalesman.infrastructure;

import library.gpLibrary.helpers.UIController;
import library.gpLibrary.models.highOrder.implementation.TerminalSet;
import library.gpLibrary.setup.GeneticAlgorithmConfig;
import u17112631.travellingSalesman.terminals.*;

public class TSPConfig extends GeneticAlgorithmConfig<Double> {

    public TSPConfig(UIController UIController) {
        super(UIController);
    }

    @Override
    protected TerminalSet<Double> createTerminalSet() {
        TerminalSet<Double> terminalSet = new TerminalSet<>();

        terminalSet.addTerminal( new ClosestUnplacedCityTo());
        terminalSet.addTerminal( new DistanceToNext());
        terminalSet.addTerminal( new FurthestUnplacedCityFrom());
        terminalSet.addTerminal( new NumberOfCitiesPlaced());
        terminalSet.addTerminal( new NumberOfCitiesUnplaced());
        terminalSet.addTerminal( new TotalDistanceCovered());
        terminalSet.addTerminal( new TotalDistanceToUnplaced());

        return terminalSet;
    }
}
