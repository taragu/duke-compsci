package nodes.commandnodes;

import java.util.List;
import nodes.leafnodes.LeafNode;
import turtle.Turtle;

public class HomeNode extends LeafNode {

    private List<Turtle> myTurtles;

    public HomeNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {

//        double xOld = myTurtle.getXPos();
//        double yOld = myTurtle.getYPos();
//
//        myTurtle.setPosition(0, 0);
//
//        return Math.sqrt(Math.pow((0 - xOld), 2) + Math.pow((0 - yOld), 2));
        
        //TODO
        return 0;
    }

}
