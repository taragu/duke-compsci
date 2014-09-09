package nodes.querynodes;

import java.util.List;

import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class ShowingNode extends LeafNode {

	private List<Turtle> myTurtles;
	private final double VISIBLE = 1;
	private final double INVISIBLE = 0;

	public ShowingNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() {
		double temp = 0;
		for (Turtle thisTurtle : myTurtles) {
			if (thisTurtle.getVisibility() == VISIBLE) {
				temp = thisTurtle.getVisibility();
				return VISIBLE;
			} else {
				temp = thisTurtle.getVisibility();
				return INVISIBLE;
			}
		}

		return temp;

	}

}
