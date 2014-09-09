package nodes.mathnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class SinNode extends AbstractNode {
	
	private List<Turtle> myTurtles;

	public SinNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	    public double evaluate () throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
	    	
	    	AbstractNode child = this.getLeftNode();
	    	double degrees = child.evaluate();
	    	double result = Math.sin(degrees * Math.PI/180);
	    	
	    	MathResults.addToMathResultsList(String.valueOf(result));
	    	
	    	return result;

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
