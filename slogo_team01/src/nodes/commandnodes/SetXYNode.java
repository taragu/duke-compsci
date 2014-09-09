package nodes.commandnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class SetXYNode extends AbstractNode {

    private List<Turtle> myTurtles;

    public SetXYNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {

        double xNew = this.getLeftNode().evaluate();
        double yNew = this.getRightNode().evaluate();
        double xOld = 0;
        double yOld = 0;
        for (Turtle turtle : myTurtles) {
            xOld = turtle.getXPos();
            yOld = turtle.getYPos();

            turtle.setPosition(xNew, yNew);
        }
        

        return Math.sqrt(Math.pow((xNew - xOld), 2)
                         + Math.pow((yNew - yOld), 2));
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
