package nodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import turtle.Turtle;

public class FunctionNode extends AbstractNode{

    private String myName;
    
    private List<Turtle> myTurtles;
    
    public FunctionNode(List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }


    public void setName (String string) {
        myName = string;
    }
    
    @Override
    public String getName() {
        return myName;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException,
                             SecurityException, InstantiationException, IllegalAccessException,
                             IllegalArgumentException, InvocationTargetException,
                             NoSuchFieldException, IOException {
        return this.getLeftNode().evaluate();
    }

    @Override
    public boolean allowsTwoChildren () { // can allow one child
        return false;
    }

    @Override
    public boolean allowsMoreThanTwoChildren () {
        return false;
    }
}
