package nodes.commandnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class LeftNode extends AbstractNode {

    
    private List<Turtle> myTurtles;

    public LeftNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        double deltaAngle = this.getLeftNode().evaluate();
        for (Turtle turtle : myTurtles) {
            turtle.updatePosition(deltaAngle, 0);
        }
        
        return deltaAngle;
    }

    @Override
    public boolean allowsTwoChildren() {
        return false;
    }

    @Override
    public boolean allowsMoreThanTwoChildren() {
        return false;
    }

}
