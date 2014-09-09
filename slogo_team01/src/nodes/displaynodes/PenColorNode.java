package nodes.displaynodes;

import java.util.List;

import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class PenColorNode extends LeafNode {
	
	List<Turtle> myTurtles;

	public PenColorNode(List<Turtle> turtles) {
		super(turtles);
		
        myTurtles = turtles;
	}
	
	@Override
	public double evaluate() {
		double temp = 0;
		for (Turtle thisTurtle : myTurtles) {
			temp = thisTurtle.getPenColorIndex();
            return thisTurtle.getPenColorIndex();
        }
		return temp;
	}

}
