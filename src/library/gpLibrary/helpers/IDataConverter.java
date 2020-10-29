package library.gpLibrary.helpers;

import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;

public interface IDataConverter {

    Node<Double> convert(String item);
}
