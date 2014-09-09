package nodes.leafnodes;

import java.util.List;
import turtle.Turtle;

public class NumberNode extends LeafNode{

    private double myValue;
    private List<Turtle> myTurtles; 
    
    public NumberNode (List<Turtle> turtles, double value) {
        super(turtles);
        myValue = value;
        myTurtles = turtles;
    }

    @Override
    public double evaluate () {
        return myValue;
    }

}
