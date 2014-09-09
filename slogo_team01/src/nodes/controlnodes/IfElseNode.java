package nodes.controlnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class IfElseNode extends AbstractNode {

    private List<Turtle> myTurtles;

    public IfElseNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    private double traverseSubtree() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        // go to left block: if satisfies condition, go to its block; if not, go
        // to right block
        AbstractNode leftBlock = this.getLeftNode();
        AbstractNode rightBlock = this.getRightNode();

        AbstractNode conditionNode = this.getLeftNode();
        double count = conditionNode.evaluate();
        if (count == 1) { // condition is true
            this.getLeftNode().getRightNode().evaluate();
        } else {
            this.getRightNode().evaluate();
        }

        return 0;
    }

    @Override
    public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        return traverseSubtree();
    }

    @Override
    public boolean allowsTwoChildren() {
        return true;
    }

    @Override
    public boolean allowsMoreThanTwoChildren() {
        return false;
    }

}
