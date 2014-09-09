package nodes.commandnodes;

import java.util.List;
import turtle.Turtle;
import nodes.leafnodes.LeafNode;

public class ClearScreenNode extends LeafNode {


    private List<Turtle> myTurtles;

    public ClearScreenNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate() {

//        double xOld = myTurtle.getXPos();
//        double yOld = myTurtle.getYPos();
//
//        /**
//         * Benson to Tara: Not sure screen clearing will work.
//         */
//
//        myTurtle.clearScreen();
//        myTurtle.setPosition(0, 0);
//        myTurtle.resetClear();
//        return Math.sqrt(Math.pow((0 - xOld), 2) + Math.pow((0 - yOld), 2));

        //TODO
        return 0;
    }

}
