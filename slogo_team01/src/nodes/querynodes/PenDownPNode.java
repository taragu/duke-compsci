package nodes.querynodes;

import java.util.List;

import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class PenDownPNode extends LeafNode {

	private List<Turtle> myTurtles;
	private final double PEN_DOWN = 1;
	private final double PEN_UP = 0;

	public PenDownPNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() {
		
		double temp = 0;

		for (Turtle thisTurtle : myTurtles) {
			if (thisTurtle.getPen() == PEN_DOWN) {
				temp = PEN_DOWN;
				return PEN_DOWN;
			} else {
				temp = PEN_UP;
				return PEN_UP;
			}
		}
		return temp;
	}

}
