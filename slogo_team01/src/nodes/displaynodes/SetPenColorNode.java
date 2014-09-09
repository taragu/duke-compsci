package nodes.displaynodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class SetPenColorNode extends AbstractNode {

	private List<Turtle> myTurtles;

	public SetPenColorNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
		AbstractNode child = this.getLeftNode();
		double colorIndex = child.evaluate();
		
		for (Turtle thisTurtle : myTurtles) {
            thisTurtle.setPenColor(colorIndex);
        }
		
		return colorIndex;
	}

	/**
	 * Both are false because this node only has one Child
	 */
	@Override
	public boolean allowsTwoChildren() {
		return false;
	}

	@Override
	public boolean allowsMoreThanTwoChildren() {
		return false;
	}

}
