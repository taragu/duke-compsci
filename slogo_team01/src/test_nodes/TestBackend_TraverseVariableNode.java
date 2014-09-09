package test_nodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import model.Parser;
import nodes.AbstractNode;
import turtle.Turtle;

public class TestBackend_TraverseVariableNode {
    
    @org.junit.Test
    public void testForTraverseTree_Make() throws Exception {
        String string = "make :distance 100 fd :distance";
        Turtle turtle = new Turtle(0, 0, 0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==100);
    }

    @org.junit.Test
    public void testForTraverseTree_To() throws Exception {
        String string = "to function [ fd 100 ]";
        Turtle turtle = new Turtle(0, 0, 0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==100);
        
    }

    @org.junit.Test
    public void testForTraverseTree_MakeTo() throws Exception {
        String string = "make :distance 100 to function [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, 0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==200);
    }

    @org.junit.Test
    public void testForTraverseTree_To2() throws Exception {
        String string = "make :distance 100 to function [ :distance ] [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, 0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==200);
    }
    
    @org.junit.Test
    public void testForTraverseTree_DoTimes() throws Exception{
        String string = "dotimes [ :distance 3 ] [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0,0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==1);
    }
    
    @org.junit.Test
    public void testForTraverseTree_For() throws Exception{
        String string = "for [ :distance 0 3 1 ] [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, 0);
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        double result = parser.traverseTree(root);
        System.out.println("TestBackend_TraverseVariableNode: result = "+result);
        assert (result==1);
    }
}
