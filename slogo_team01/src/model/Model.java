package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import turtle.Turtle;

public class Model {
	
	List<Turtle> myTurtles;
	List<Turtle> postTurtles;
	String myLanguage;
	String myCommands;
	List<String> myVariables;
	
	public List<Turtle> getTurtles(){
		return myTurtles;
	}
	
	public List<String> getVariables(){
		return myVariables;
	}
	
	public Model() {
	}

	/**
	 * Call this method to process commands, update turtle position, and change
	 * workspace display
	 * 
	 * @return
	 * @throws IOException
	 * @throws NoSuchFieldException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	
	/**
	 * Benson to All:
	 * Got rid of the maps. 1 Controller per Model.
	 */
	public double processCommands(List<Turtle> turtles,
			String commands, String language)
			throws Exception {

		myTurtles = turtles;
		myCommands = commands;
		myLanguage = language;
		Parser parser = new Parser(myTurtles, myCommands, myLanguage);
		double result = parser.doParse();
		myVariables = parser.getVariables();
		
		return result;
	}

}
