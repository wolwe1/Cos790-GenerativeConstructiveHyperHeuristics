package library.gpLibrary.models.primitives.functions.interfaces;


import library.gpLibrary.models.primitives.nodes.abstractClasses.Node;
import library.gpLibrary.models.primitives.nodes.abstractClasses.ValueNode;

public abstract class IOperateUpFunction<T> extends ValueNode<T> {

    protected IOperateUpFunction(String name) {
        super(name);
    }

    /**
     * Specifies the operation performed by the function
     * @return The calculated value by performing the operation
     */
    public abstract T operation();

    @Override
    public boolean isFull() {

        if(children.size() < _maxChildren)
            return false;

        for (Node<T> child : children) {
            if(!child.isFull())
                return false;
        }

        return true;
    }
}
