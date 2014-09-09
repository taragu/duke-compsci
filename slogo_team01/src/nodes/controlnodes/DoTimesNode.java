package nodes.controlnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import turtle.Turtle;
import nodes.AbstractNode;
import nodes.VariableNode;
import nodes.leafnodes.NumberNode;

public class DoTimesNode extends AbstractNode {

    private List<Turtle> myTurtles;
    
    public DoTimesNode (List<Turtle> turtles) {
        super(turtles);
        myTurtles = turtles;
    }

    @Override
    public double evaluate () throws ClassNotFoundException, NoSuchMethodException,
                             SecurityException, InstantiationException, IllegalAccessException,
                             IllegalArgumentException, InvocationTargetException,
                             NoSuchFieldException, IOException {
        return traverseSubtree();
    }
    
    private double traverseSubtree() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        // check left (condition node)
        AbstractNode conditionNode = this.getLeftNode();
        double returnValue = 0;
        double maxVal = conditionNode.evaluate();
        for (int i=0;i<maxVal;i++) {
            conditionNode.getLeftNode().setCurrentValue(i);
            conditionNode.setLeftNode(new NumberNode(myTurtles, i));
            traverseAndReplace(this.getRightNode(), conditionNode);
            returnValue+= this.getRightNode().evaluate();
        }
        return returnValue;
    }
    
    private void traverseAndReplace(AbstractNode node, AbstractNode conditionNode) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException{
        if (node==null) return;
        if (node instanceof VariableNode) {
            node.setLeftNode(new NumberNode(myTurtles, conditionNode.getLeftNode().evaluate()));
            node.setCurrentValue(conditionNode.evaluate());
        }
        traverseAndReplace(node.getLeftNode(), conditionNode);
        traverseAndReplace(node.getRightNode(),conditionNode);
    }

    @Override
    public boolean allowsTwoChildren () {
        return true;
    }

    @Override
    public boolean allowsMoreThanTwoChildren () {
        return false;
    }
    
    @Override
    public boolean hasTwoBlockNodes() {
        return true;
    }

}
