package nodes.commandnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class SetHeadingNode extends AbstractNode {

    private List<Turtle> myTurtles;

    public SetHeadingNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    // TODO
    @Override
    public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        AbstractNode child = this.getLeftNode();

        double degreesNew = child.evaluate();
        double degreesOld = 0;
        for (Turtle turtle : myTurtles) {
            degreesOld = turtle.getAngle();

            turtle.setHeading(degreesNew);
        }


        return Math.abs(degreesNew - degreesOld);
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
