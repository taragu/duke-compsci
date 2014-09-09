package nodes.commandnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class ForwardNode extends AbstractNode {


    private List<Turtle> myTurtles;


    public ForwardNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

	@Override
	public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
	    double distance = this.getLeftNode().evaluate();
	    for (Turtle thisTurtle : myTurtles) {
	        thisTurtle.updatePosition(0, distance);
	    }
            
            return distance;
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
