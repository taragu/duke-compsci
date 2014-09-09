package nodes.mathnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class ProductNode extends AbstractNode {
	private List<Turtle> myTurtles;

	public ProductNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, IOException {
		AbstractNode leftNode = this.getLeftNode();
		AbstractNode rightNode = this.getRightNode();

		MathResults.addToMathResultsList(String.valueOf(leftNode.evaluate()
				* rightNode.evaluate()));
		return leftNode.evaluate() * rightNode.evaluate();

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
