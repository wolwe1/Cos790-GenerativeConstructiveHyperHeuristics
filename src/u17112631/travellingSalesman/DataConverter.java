package u17112631.travellingSalesman;

import u17112631.travellingSalesman.primitives.TSProblem;

import java.util.List;

public class DataConverter {

    TSProblem problem;

    public void loadData(List<String> fileData) {
        problem = new TSProblem(fileData);
    }

    public TSProblem getProblem(){
        return problem;
    }
}
