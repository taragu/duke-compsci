package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Model;
import turtle.ITurtle;
import turtle.Turtle;
import turtle_graphics.TurtleImage;

public class ModelController extends AbstractController {

	/*
	 * Setting up Communication between Controller and Model
	 */

	private final String DEFAULT_LANGUAGE = "English";
	public static final String MODEL = "Model";

	public ModelController() {
	}

	public List<ITurtle> convertImagetoITurtle(List<TurtleImage> turtleImages) {

		List<ITurtle> iTurtles = new ArrayList<ITurtle>();

		for (TurtleImage ti : turtleImages) {
			iTurtles.add(ti);
		}
		return iTurtles;
	}

	public List<TurtleImage>  passToEnglishModel(List<TurtleImage> turtles, String command)
			throws Exception {

		return passToModel(turtles, command, DEFAULT_LANGUAGE);
	}

	public List<TurtleImage>  passToModel(List<TurtleImage> turtles, String command,
			String language) throws Exception {

		List<Turtle> backendTurtles = new ArrayList<Turtle>();

		for (ITurtle it : convertImagetoITurtle(turtles)) {
			backendTurtles.add(new Turtle(it));
		}
		setModelProperty(backendTurtles, command, language);
		
		List<Turtle> turtleList = getTurtleList();
		
		for(int i = 0; i < turtleList.size(); i++){
			double deltaX = turtleList.get(i).getDeltaX();
			double deltaY = turtleList.get(i).getDeltaY();
			double angle = turtleList.get(i).getAngle();
			turtles.get(i).updateTurtleState(deltaX, deltaY, angle);	
		}
		return turtles;
	}
	


}
