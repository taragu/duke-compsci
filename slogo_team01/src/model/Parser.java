package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import turtle.Turtle;
import nodes.AbstractNode;
import nodes.BlockNode;
import nodes.FunctionNode;
import nodes.MakeNode;
import nodes.NodeFactory;
import nodes.VariableNode;
import nodes.controlnodes.ConditionNode;
import nodes.controlnodes.IfElseNode;
import nodes.leafnodes.LeafNode;

public class Parser {
    private List<Turtle> myTurtles;
    private boolean myValidBoolean = true;
    private String myCommands;
    private String myLanguage;
    private List<AbstractNode> myVariableNodes = new ArrayList<AbstractNode>();
    private List<AbstractNode> myFunctionNodes = new ArrayList<AbstractNode>();
    private List<String> myVariables = new ArrayList<String>();
    
    public Parser (List<Turtle> turtles, String commands, String language) {
        myTurtles = turtles;
        myCommands = commands;
        myLanguage = language;
    }

    public boolean isValid() {
        return myValidBoolean;
    }
    /** 
     * call this method in Model; creates abstract syntax tree and traverse the tree
     * @return value to display in the view
     */
    public double doParse() throws Exception {
        return traverseTree(createTree());
    }

    public AbstractNode createTree() throws Exception {
        NodeFactory nodeFactory = new NodeFactory(myTurtles, myLanguage);
        AbstractNode root = new BlockNode(myTurtles);
        String[] words = myCommands.split(" ");
        Queue<String> queue = new LinkedList<String>();
        Stack<String> bracketStack = new Stack<String>(); 
        for (String word : words) { queue.add(word); }
        String currentWord = queue.poll();
        AbstractNode currentNode = nodeFactory.createNode(currentWord);
        root.setLeftNode(currentNode);
        while (!queue.isEmpty()) {
            boolean skipLeafCheck = false;
            if (currentNode instanceof VariableNode || currentNode instanceof FunctionNode) {
                if (findPreviouslyCreatedNodes(currentNode)!=null) {
                    currentNode = findPreviouslyCreatedNodes(currentNode);
                } else {
                    skipLeafCheck=true;
                }
            } 
            if (!skipLeafCheck) {
                if (currentNode instanceof LeafNode || currentNode instanceof VariableNode || currentNode instanceof FunctionNode) {
                    currentNode = currentNode.getParent();
                    if (currentNode instanceof VariableNode && (currentNode.getChildren().size() == 3 
                            || (currentNode.getChildren().size()==1 && !nodeFactory.isNumeric(queue.peek())))) {
                        if (currentNode instanceof VariableNode) { 
                            currentNode.setCurrentValue(currentNode.getLeftNode().evaluate());
                            myVariableNodes.add(currentNode);
                        }
                        if (currentNode instanceof FunctionNode) myFunctionNodes.add(currentNode);
                        currentNode = currentNode.getParent();
                        if (currentNode instanceof MakeNode || currentNode instanceof ConditionNode) currentNode = currentNode.getParent();
                    } else if (!(currentNode instanceof VariableNode) && (!currentNode.allowsTwoChildren() || 
                            (currentNode.allowsTwoChildren() && currentNode.getChildren().size()==2))) {
                        currentNode = currentNode.getParent();
                    }
                }
            }
            if (currentNode instanceof IfElseNode) {
                currentNode.setLeftNode(new BlockNode(myTurtles));
                currentNode.setRightNode(new BlockNode(myTurtles));
                currentNode.getLeftNode().setLeftNode(new ConditionNode(myTurtles));
                currentNode.getLeftNode().setRightNode(new BlockNode(myTurtles));
                currentNode = currentNode.getLeftNode().getLeftNode();
            }
            if ((currentNode!=null && currentNode.hasOneConditionOneBlock()) &&currentNode.getLeftNode()==null) {
                currentNode.setLeftNode(new ConditionNode(myTurtles));
                currentNode.setRightNode(new BlockNode(myTurtles));
                currentNode = currentNode.getLeftNode();
            }
            if ((currentNode!=null && currentNode.hasTwoBlockNodes()) && currentNode.getLeftNode() == null) {
                currentNode.setLeftNode(new BlockNode(myTurtles));
                currentNode.setRightNode(new BlockNode(myTurtles));
                currentNode = currentNode.getLeftNode();
            }
            if (currentNode instanceof FunctionNode) {
                ((FunctionNode) currentNode).setName(queue.poll());
                boolean hasTwoChildren=false;
                while(queue.poll().equals("[")) bracketStack.push(queue.poll());
                if (queue.peek().charAt(0)==':') {
                    hasTwoChildren=true;
                }
                if (hasTwoChildren) {
                    currentNode.setLeftNode(new BlockNode(myTurtles));
                    currentNode.setRightNode(new BlockNode(myTurtles));
                } else {// if it's a normal declare of function without variables --> only one child
                    currentNode.setLeftNode(new BlockNode(myTurtles));
                }
                currentNode = currentNode.getLeftNode();
            }
            String nextWord = queue.poll();
            if (nextWord==null) {
                myValidBoolean = bracketStack.isEmpty();
                return root;
            }
            while(nextWord.equals("[") || nextWord.equals("]")) {
                if (nextWord.equals("[")){// if the parent node is a repeat node or an if node, or the parent of the parent node is an if else node
                    bracketStack.push(nextWord);
                    if (currentNode!=null && (currentNode.hasOneConditionOneBlock() || currentNode.hasTwoBlockNodes())) {
                        currentNode = currentNode.getRightNode(); // go to block
                    } else if (currentNode.getParent()!=null && currentNode.getParent().hasOneConditionOneBlock()) {
                        currentNode = currentNode.getParent().getRightNode(); 
                    } else if (currentNode.getParent() !=null && currentNode.getParent().getParent() instanceof IfElseNode) {
                        currentNode = currentNode.getParent().getRightNode();
                    }
                } else if (nextWord.equals("]")) {  
                    if (!bracketStack.pop().equals("[")) myValidBoolean=false;
                    if (currentNode == null) {
                        myValidBoolean = bracketStack.isEmpty();
                        return root;
                    }
                    if (!currentNode.hasTwoBlockNodes()) {
                        currentNode = currentNode.getParent();
                        if (currentNode == null)  return root;
                        if (currentNode.getParent() instanceof IfElseNode) {
                            currentNode = currentNode.getParent();
                        }
                    }
                }
                nextWord = queue.poll();
                if (nextWord == null) {
                    myValidBoolean = bracketStack.isEmpty();
                    return root;
                }
            }
            AbstractNode nextNode = nodeFactory.createNode(nextWord); 
            if (currentNode.getLeftNode()== null) {
                currentNode.setLeftNode(nextNode);
            } else if (currentNode.getRightNode() == null && (currentNode.allowsTwoChildren() || currentNode.allowsMoreThanTwoChildren())) {
                currentNode.setRightNode(nextNode);
            } else if (currentNode.getRightNode() != null && currentNode.allowsMoreThanTwoChildren()){
                currentNode.addChild(nextNode);
            }
            currentNode = nextNode;
            currentWord = nextWord;
        }
        myValidBoolean = bracketStack.isEmpty();
        return root;
    }

    private AbstractNode findPreviouslyCreatedNodes (AbstractNode currentNode) throws Exception {
        for (AbstractNode thisNode : myVariableNodes) {
            if (thisNode.getName().equals(currentNode.getName())) {
                currentNode.setLeftNode(thisNode.getLeftNode()); 
                currentNode.setCurrentValue(thisNode.evaluate());
                return currentNode;
            }
        }
        return null;
    }
    public double traverseTree(AbstractNode root) throws Exception {  
        if (root!=null) {
            for (AbstractNode childNode : root.getChildren()) {
                if (childNode.hasTwoBlockNodes() || childNode.hasOneConditionOneBlock()) {
                    childNode.evaluate();
                } else { traverseTree(childNode); }
            }
            if(root instanceof VariableNode){
            	myVariables.add(root.getName());
            }
            return root.evaluate();
        }
        return 1; 
    }
    public List<String> getVariables(){ return myVariables; }
}