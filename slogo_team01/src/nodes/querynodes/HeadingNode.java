package nodes.querynodes;

import java.util.List;

import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class HeadingNode extends LeafNode {

	private List<Turtle> myTurtles;

	public HeadingNode(List<Turtle> turtles) {
		super(turtles);

		myTurtles = turtles;
	}

	@Override
	public double evaluate() {
		double temp = 0;
		for (Turtle thisTurtle : myTurtles) {
			temp = thisTurtle.getAngle();
			return thisTurtle.getAngle();
		}

		return temp;
	}
}
