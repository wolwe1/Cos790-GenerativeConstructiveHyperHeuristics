package u17112631.travellingSalesman.primitives;

import java.util.ArrayList;
import java.util.List;

public class TSProblem {

    String name;
    String type;
    String comment;
    String edgeWeightType;
    int dimension;

    List<TSNode> nodes;

    public TSProblem(List<String> fileData){
        nodes = new ArrayList<>();

        name = fileData.get(0).split("\\s+")[1];
        type = fileData.get(1).split("\\s+")[1];
        comment = fileData.get(2).split("\\s+")[1];
        dimension = Integer.parseInt(fileData.get(3).split("\\s+")[1]);
        edgeWeightType = fileData.get(4).split("\\s+")[1];

        if(!type.equals("ATSP"))
            loadSymmetric(fileData);
        else
            loadAsymmetric(fileData);
    }

    private String[] concatenateRowsIntoArrayOfWeights(List<String> fileData){

        List<String> nodeLines = new ArrayList<>();

        //Read file data into list
        for (int i = 7; i < fileData.size(); i++) {
            if(fileData.get(i).equals("EOF"))
                break;
            nodeLines.add(fileData.get(i));
        }

        //Concatenate rows into single string for easier use
        StringBuilder concatenatedRows = new StringBuilder();
        for (String nodeLine : nodeLines) {
            concatenatedRows.append(nodeLine);
        }

        //Create array of weight values
        String[] weights = concatenatedRows.toString().trim().split("\\s+");

        assert weights.length == Math.pow(dimension,2);

        return weights;
    }
    private void loadAsymmetric(List<String> fileData) {

        List<String[]> nodeStrings = new ArrayList<>();
        String[] weights = concatenateRowsIntoArrayOfWeights(fileData);

        //For each node
        for (int i = 0; i < dimension; i++) {

            //For each weight from the current node to other node
            String[] weightsFromNodeToOtherNodes = new String[dimension];

            System.arraycopy(weights, i * dimension, weightsFromNodeToOtherNodes, 0, dimension);

            nodes.add(new AsymmetricTSNode(i,weightsFromNodeToOtherNodes));
        }

        assert nodes.size() == dimension;
    }

    private void loadSymmetric(List<String> fileData) {
        for (int i = 6; i < fileData.size(); i++) {
            if(fileData.get(i).equals("EOF"))
                break;
            nodes.add(new TSNode(fileData.get(i)));
        }
    }

    public List<TSNode> getNodes() {
        List<TSNode> nodes = new ArrayList<>();

        for (TSNode node : this.nodes) {
            nodes.add( node.getCopy());
        }

        return nodes;
    }
}
