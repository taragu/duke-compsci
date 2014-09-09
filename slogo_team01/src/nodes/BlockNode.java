package nodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import turtle.Turtle;

public class BlockNode extends AbstractNode {

    private List<Turtle> myTurtles; 
    
    public BlockNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        if (this.getLeftNode()!=null && this.getRightNode()==null) {
            return this.getLeftNode().evaluate();
        } else if (this.getLeftNode()==null && this.getRightNode()!=null) {
            return this.getRightNode().evaluate();
        } else if (this.getLeftNode()!=null && this.getRightNode()!=null) {
            return (this.getLeftNode().evaluate() + this.getRightNode().evaluate());
        }
        return 0; //TEST: RETURN 0
        
    }

    @Override
    public boolean allowsTwoChildren () {
        return true;
    }

    @Override
    public boolean allowsMoreThanTwoChildren () {
        return true;
    }

}
