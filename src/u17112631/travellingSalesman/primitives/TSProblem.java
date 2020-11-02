package u17112631.travellingSalesman.primitives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    private void loadAsymmetric(List<String> fileData) {

        List<String[]> nodeStrings = new ArrayList<>();
        List<String> nodeLines = new ArrayList<>();


        for (int i = 7; i < fileData.size(); i++) {
            if(fileData.get(i).equals("EOF"))
                break;
            nodeLines.add(fileData.get(i));
        }
        //str.split("\\s+")

        for (int i = 0, nodeLinesSize = nodeLines.size(); i < nodeLinesSize; i+= 2) {
            String nodeLine = nodeLines.get(i).trim();
            String extraValue = nodeLines.get(i+1).trim();

            String[] weightsFromFirstLine = nodeLine.split("\\s+");
            String[] weightsFromSecondLine = extraValue.split("\\s+");

            String[] both = Stream.concat(Arrays.stream(weightsFromFirstLine), Arrays.stream(weightsFromSecondLine))
                    .toArray(String[]::new);

            nodeStrings.add(both);
        }

        for (int i = 0, nodeStringsSize = nodeStrings.size(); i < nodeStringsSize; i++) {
            String[] nodeString = nodeStrings.get(i);
            nodes.add(new AsymmetricTSNode(i,nodeString));
        }
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
