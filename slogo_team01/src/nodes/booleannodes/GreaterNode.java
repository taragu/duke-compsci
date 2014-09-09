package nodes.booleannodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import nodes.AbstractNode;
import turtle.Turtle;

public class GreaterNode extends CompareNode {

	private List<Turtle> myTurtles;

	public GreaterNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	/**
	 * Return 1 if left node is greater than right node; return 0 if left node is less than right node
	 */
	public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
		AbstractNode leftNode = this.getLeftNode();
		AbstractNode rightNode = this.getRightNode();
		if (leftNode.evaluate() > rightNode.evaluate()) {
			return 1;
		}
		return 0;
	}

}
