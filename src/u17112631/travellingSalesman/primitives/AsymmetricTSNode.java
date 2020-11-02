package u17112631.travellingSalesman.primitives;

import java.util.HashMap;
import java.util.Map;

public class AsymmetricTSNode extends TSNode {

    HashMap<Integer,Double> weights;

    public AsymmetricTSNode(int nodeIndex, String[] weightMatrix) {
        super();

        nodeNumber = nodeIndex;
        weights = new HashMap<>();

        for (int i = 0, weightMatrixLength = weightMatrix.length; i < weightMatrixLength; i++) {
            String value = weightMatrix[i];

            if(i!= nodeNumber){
                weights.put(i,Double.parseDouble(value));
            }
        }
    }

    public AsymmetricTSNode(AsymmetricTSNode other) {
        nodeNumber = other.nodeNumber;
        weights = other.getWeights();
    }

    private HashMap<Integer, Double> getWeights() {
        HashMap<Integer,Double> weightCopies = new HashMap<>();

        for (Map.Entry<Integer, Double> weight : weights.entrySet()) {
            weightCopies.put(weight.getKey(),weight.getValue());
        }

        return weightCopies;
    }

    @Override
    public double getDistanceTo(TSNode otherCity) {
        return weights.get(otherCity.nodeNumber);
    }

    @Override
    public AsymmetricTSNode getCopy() {
        return new AsymmetricTSNode(this);
    }
}
