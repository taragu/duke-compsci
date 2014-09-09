package nodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import model.Parser;
import turtle.Turtle;

public class VariableNode extends AbstractNode{ 

    private double myCurrentValue;
    private List<Turtle> myTurtles;

    private double myStartingValue;
    private double myEndingValue;
    private double myIncrement;
    
    private int myIndex = 0;

    private String myVariableName;

    public VariableNode (List<Turtle> turtles, String variableName) {
        super(turtles);
        myTurtles = turtles;
        myVariableName = variableName;
    }


    public VariableNode (List<Turtle> turtles, String variableName, double value) {
        this(turtles, variableName);
        myCurrentValue = value;
    }

    public VariableNode (List<Turtle> turtles, String variableName, double startingValue, double endingValue, double increment) {
        this(turtles, variableName);
        myStartingValue = startingValue;
        myEndingValue = endingValue;
        myIncrement = increment;

    }

    @Override
    public void setCurrentValue(double value) {
        myCurrentValue = value;
    }

    public void setIncrement(double increment) {
        myIncrement = increment;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        if (this.getLeftNode()!=null) {    
            setCurrentValue(this.getLeftNode().evaluate());
        }
        System.out.println("******VariableNode: evaluate called; current value is " + myCurrentValue+" and the parent is "+this.getParent());
        
        return myCurrentValue;

    }

    @Override
    public String getName () {
        return myVariableName;
    }


    @Override
    public boolean allowsTwoChildren () { // can allow one child OR three children
        return false;
    }


    @Override
    public boolean allowsMoreThanTwoChildren () {
        return true;
    }

}
