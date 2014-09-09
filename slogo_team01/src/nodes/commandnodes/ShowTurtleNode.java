package nodes.commandnodes;

import java.util.List;
import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class ShowTurtleNode extends LeafNode {

    private List<Turtle> myTurtles;

    public ShowTurtleNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {
        for (Turtle turtle : myTurtles) {
            turtle.setVisible();
        }

        return 1;
    }

}
