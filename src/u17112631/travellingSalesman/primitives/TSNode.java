package u17112631.travellingSalesman.primitives;

import java.util.Objects;

public class TSNode {

    int nodeNumber;
    double xCoord;
    double yCoord;

    public TSNode(String details) {
        String[] info = details.split(" ");

        nodeNumber = Integer.parseInt(info[0]);
        xCoord = Double.parseDouble(info[1]);
        yCoord = Double.parseDouble(info[2]);
    }

    public TSNode(TSNode other) {
        nodeNumber = other.nodeNumber;
        xCoord = other.xCoord;
        yCoord = other.yCoord;
    }

    public double getDistanceTo(TSNode otherCity) {
        double otherX = otherCity.xCoord;
        double otherY = otherCity.yCoord;

        return Math.sqrt( Math.pow((otherX - xCoord),2) + Math.pow((otherY - yCoord),2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSNode tsNode = (TSNode) o;
        return nodeNumber == tsNode.nodeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeNumber);
    }

    public TSNode getCopy() {
        return new TSNode(this);
    }
}
