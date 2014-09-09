package nodes.commandnodes;

import java.util.List;
import turtle.Turtle;
import nodes.leafnodes.LeafNode;

public class PenDownNode extends LeafNode {

    private List<Turtle> myTurtles;

    public PenDownNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {
        for (Turtle turtle : myTurtles) {
            turtle.setPenDown();
        }
        
        return 1;
    }

}
