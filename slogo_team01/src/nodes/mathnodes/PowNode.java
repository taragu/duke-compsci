package nodes.mathnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class PowNode extends AbstractNode {

	private List<Turtle> myTurtles;

	public PowNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
		AbstractNode leftNode = this.getLeftNode();
		AbstractNode rightNode = this.getRightNode();
		
		double base = leftNode.evaluate();
		double exp = rightNode.evaluate();
		 MathResults.addToMathResultsList(String.valueOf(Math.pow(base, exp)));
		return Math.pow(base, exp);

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
