package nodes.querynodes;

import java.util.List;

import turtle.Turtle;
import nodes.leafnodes.LeafNode;

public class XCorNode extends LeafNode {
	
    private List<Turtle> myTurtles;

	public XCorNode(List<Turtle> turtles) {
		super(turtles);
        myTurtles = turtles;
	}
	
	@Override
	public double evaluate() {
		double temp = 0;
		for (Turtle thisTurtle : myTurtles) {
			temp = thisTurtle.getXPos();
            return thisTurtle.getXPos();
        }
		return temp;
	}

}
