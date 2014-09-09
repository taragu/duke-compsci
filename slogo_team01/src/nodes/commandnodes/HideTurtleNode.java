package nodes.commandnodes;

import java.util.List;
import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class HideTurtleNode extends LeafNode {

    private List<Turtle> myTurtles;

    public HideTurtleNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {
        for (Turtle thisTurtle : myTurtles) {
            thisTurtle.setInvisible();

        }
        return 0;
    }

}
