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

        name = fileData.get(0).split(" ")[1];
        type = fileData.get(1).split(" ")[1];
        comment = fileData.get(2).split(" ")[1];
        dimension = Integer.parseInt(fileData.get(3).split(" ")[1]);
        edgeWeightType = fileData.get(4).split(" ")[1];

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
