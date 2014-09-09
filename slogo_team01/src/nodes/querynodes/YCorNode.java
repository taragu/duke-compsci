package nodes.querynodes;

import java.util.List;

import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class YCorNode extends LeafNode {

	private List<Turtle> myTurtles;

	public YCorNode(List<Turtle> turtles) {
		super(turtles);

		myTurtles = turtles;
	}

	@Override
	public double evaluate() {
		double temp = 0;

		for (Turtle thisTurtle : myTurtles) {
			temp = thisTurtle.getYPos();
			return thisTurtle.getYPos();
		}
		return temp;
	}

}
