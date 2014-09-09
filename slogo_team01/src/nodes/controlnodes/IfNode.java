package nodes.controlnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import turtle.Turtle;
import nodes.AbstractNode;

public class IfNode extends AbstractNode {

    private List<Turtle> myTurtles;

    public IfNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    private double traverseSubtree() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        // check left (condition node)
        AbstractNode conditionNode = this.getLeftNode();
        double count = conditionNode.evaluate();
        if (count == 1) { // condition is true
            this.getRightNode().evaluate();
        }
        return 0;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        return traverseSubtree();
    }

    @Override
    public boolean allowsTwoChildren () {
        return true;
    }

    @Override
    public boolean allowsMoreThanTwoChildren () {
        return false;
    }


    
    @Override
    public boolean hasOneConditionOneBlock() {
        return true;
    }
}
