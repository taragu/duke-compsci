//package test_nodes;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//import model.Model;
//import model.Parser;
//import nodes.AbstractNode;
//import nodes.BlockNode;
//import nodes.MakeNode;
//import nodes.VariableNode;
//import nodes.booleannodes.EqualNode;
//import nodes.commandnodes.ForwardNode;
//import nodes.controlnodes.ConditionNode;
//import nodes.controlnodes.IfElseNode;
//import nodes.controlnodes.IfNode;
//import nodes.controlnodes.RepeatNode;
//import nodes.leafnodes.NumberNode;
//import turtle.Turtle;
//
//public class TestBackend {
//
//    @org.junit.Test
//    public void testForCreateTree_Repeat() throws ClassNotFoundException,
//    NoSuchMethodException, SecurityException, InstantiationException,
//    IllegalAccessException, IllegalArgumentException,
//    InvocationTargetException, IOException, NoSuchFieldException {
//        String string = "repeat 2 [ fd 50 fd 100 fd 80 ]";
//        Turtle turtle = new Turtle(0, 0, "English");
//        List<Turtle> allTurtles = new ArrayList<Turtle>();
//        allTurtles.add(turtle);
//
//        Parser parser = new Parser(allTurtles, string, "English");
//        AbstractNode root = parser.createTree();
//        assert root instanceof BlockNode;
//        AbstractNode node = root.getLeftNode();
//        assert node instanceof RepeatNode;
//        assert node.getLeftNode() instanceof ConditionNode;
//        assert node.getRightNode() instanceof BlockNode;
//        assert node.getLeftNode().getLeftNode() instanceof NumberNode;
//        for (AbstractNode thisNode : node.getRightNode().getChildren()) {
//            assert thisNode instanceof ForwardNode;
//            assert thisNode.getLeftNode() instanceof NumberNode;
//        }
//    }
//    
//
//    @org.junit.Test
//    public void testForCreateTree_If() throws ClassNotFoundException,
//    NoSuchMethodException, SecurityException, InstantiationException,
//    IllegalAccessException, IllegalArgumentException,
//    InvocationTargetException, IOException, NoSuchFieldException {
//        String string = "if equalp 2 2 [ fd 50 fd 100 fd 80 ]";
//        Turtle turtle = new Turtle(0, 0, "English");
//        List<Turtle> allTurtles = new ArrayList<Turtle>();
//        allTurtles.add(turtle);
//
//        Parser parser = new Parser(allTurtles, string, "English");
//        AbstractNode root = parser.createTree();
//        
//            assert root instanceof BlockNode;
//            AbstractNode node = root.getLeftNode();
//            assert node instanceof IfNode;
//            assert node.getLeftNode() instanceof ConditionNode;
//            assert node.getRightNode() instanceof BlockNode;
//            assert node.getLeftNode().getLeftNode() instanceof EqualNode;
//            for (AbstractNode thisChild : node.getLeftNode().getLeftNode()
//                    .getChildren()) {
//                assert thisChild instanceof NumberNode;
//            }
//            for (AbstractNode thisChild : node.getRightNode().getChildren()) {
//                assert thisChild instanceof ForwardNode;
//                assert thisChild.getLeftNode() instanceof NumberNode;
//            }
//    }
////
////    @org.junit.Test
////    public void testForCreateTree_IfElse() throws ClassNotFoundException,
////    NoSuchMethodException, SecurityException, InstantiationException,
////    IllegalAccessException, IllegalArgumentException,
////    InvocationTargetException, IOException, NoSuchFieldException {
////        String string = "ifelse equalp 2 2 [ fd 50 fd 100 fd 80 ] [rt 20]";
////        Turtle turtle = new Turtle(0, 0, "English");
////        List<Turtle> allTurtles = new ArrayList<Turtle>();
////        allTurtles.add(turtle);
////        Parser parser = new Parser(turtle, allTurtles);
////        parser.createFunctionsAndVariables(string);
////        for (Function function : parser.getFunctions()) {
////            AbstractNode root = parser.createTree(function, turtle);
////            assert root instanceof BlockNode;
////            AbstractNode node = root.getLeftNode();
////            assert node instanceof IfElseNode;
////            assert node.getLeftNode() instanceof BlockNode;
////            assert node.getRightNode() instanceof BlockNode;
////            assert node.getLeftNode().getLeftNode() instanceof ConditionNode;
////            assert node.getLeftNode().getRightNode() instanceof BlockNode;
////            assert node.getLeftNode().getLeftNode().getLeftNode() instanceof EqualNode;
////
////            for (AbstractNode thisChild : node.getLeftNode().getLeftNode()
////                    .getLeftNode().getChildren()) {
////                assert thisChild instanceof NumberNode;
////            }
////            for (AbstractNode thisChild : node.getLeftNode().getRightNode()
////                    .getChildren()) {
////                assert thisChild instanceof ForwardNode;
////                assert thisChild.getLeftNode() instanceof NumberNode;
////            }
////        }
////    }
////
////    @org.junit.Test
////    public void testForTraverseTree_IfElse() throws ClassNotFoundException,
////    NoSuchMethodException, SecurityException, InstantiationException,
////    IllegalAccessException, IllegalArgumentException,
////    InvocationTargetException, IOException, NoSuchFieldException {
////        String string = "ifelse equalp 2 2 [ fd 50 fd 100 fd 80 ] [rt 20]";
////        Turtle turtle = new Turtle(0, 0, "English");
////        List<Turtle> allTurtles = new ArrayList<Turtle>();
////        allTurtles.add(turtle);
////        Parser parser = new Parser(turtle, allTurtles);
////        parser.createFunctionsAndVariables(string);
////        for (Function function : parser.getFunctions()) {
////            AbstractNode root = parser.createTree(function, turtle);
////            parser.traverseTree(turtle, root);
////            assert turtle.getYPos() == 230;
////        }
////    }
////
////    @org.junit.Test
////    public void testFor_FdFd50() throws ClassNotFoundException,
////    NoSuchMethodException, SecurityException, InstantiationException,
////    IllegalAccessException, IllegalArgumentException,
////    InvocationTargetException, IOException, NoSuchFieldException {
////        String string = "qianjin qianjin 50";
////        Turtle turtle = new Turtle(0, 0, "Chinese");
////        List<Turtle> allTurtles = new ArrayList<Turtle>();
////        allTurtles.add(turtle);
////        Parser parser = new Parser(turtle, allTurtles);
////        parser.createFunctionsAndVariables(string);
////        for (Function function : parser.getFunctions()) {
////            AbstractNode root = parser.createTree(function, turtle);
////            parser.traverseTree(turtle, root);
////            assert turtle.getYPos() == 100;
////        }
////    }
////
////    @org.junit.Test
////    public void testForTraverseTree_Repeat() throws ClassNotFoundException,
////    NoSuchMethodException, SecurityException, InstantiationException,
////    IllegalAccessException, IllegalArgumentException,
////    InvocationTargetException, IOException, NoSuchFieldException {
////        String string = "repeat 20 [ fd 50 ]";
////        Turtle turtle = new Turtle(0, 0, "English");
////        List<Turtle> allTurtles = new ArrayList<Turtle>();
////        allTurtles.add(turtle);
////        Parser parser = new Parser(turtle, allTurtles);
////        parser.createFunctionsAndVariables(string);
////        for (Function function : parser.getFunctions()) {
////            AbstractNode root = parser.createTree(function, turtle);
////            parser.traverseTree(turtle, root);
////            assert turtle.getYPos() == 1000;
////        }
////    }
////
////    @org.junit.Test
////    public void testForTraverseTree_If() throws ClassNotFoundException,
////    NoSuchMethodException, SecurityException, InstantiationException,
////    IllegalAccessException, IllegalArgumentException,
////    InvocationTargetException, IOException, NoSuchFieldException {
////        String string = "if and equalp 1 1 greaterp 3 2 [ fd 50 ]";
////        Turtle turtle = new Turtle(0, 0, "English");
////        List<Turtle> allTurtles = new ArrayList<Turtle>();
////        allTurtles.add(turtle);
////        Parser parser = new Parser(turtle, allTurtles);
////        parser.createFunctionsAndVariables(string);
////        for (Function function : parser.getFunctions()) {
////            AbstractNode root = parser.createTree(function, turtle);
////            parser.traverseTree(turtle, root);
////            assert turtle.getYPos() == 50;
////        }
////    }
//
//}
