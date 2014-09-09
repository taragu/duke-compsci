package nodes.mathnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class MinusNode extends AbstractNode {
	private List<Turtle> myTurtles;

    public MinusNode(List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {

        AbstractNode child = this.getLeftNode();
		MathResults.addToMathResultsList(String.valueOf(child.evaluate() * -1));
        return child.evaluate() * -1;
    }

    /**
     * Both are false because this node only has one Child
     */
    @Override
    public boolean allowsTwoChildren() {
        return false;
    }

    @Override
    public boolean allowsMoreThanTwoChildren() {
        return false;
    }

}
