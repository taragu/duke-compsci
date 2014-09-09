package nodes.commandnodes;

import java.util.List;
import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class PenUpNode extends LeafNode {

    private List<Turtle> myTurtles;

    public PenUpNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {
        for (Turtle turtle : myTurtles) {
            turtle.setPenUp();
        }

        return 1;
    }


}
