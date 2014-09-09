package nodes.displaynodes;

import java.util.List;
import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class ShapeNode extends LeafNode {

    private List<Turtle> myTurtles;

    public ShapeNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {
        for (Turtle thisTurtle : myTurtles) {
            return thisTurtle.getMyShapeIndex();
        }
        return 1;
    }

}
