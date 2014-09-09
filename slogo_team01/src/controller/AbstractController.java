package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Model;
import turtle.ITurtle;
import turtle.Turtle;
import view.IView;

public abstract class AbstractController implements PropertyChangeListener {

	private ArrayList<IView> registeredViews;
	//private ArrayList<Model> registeredModels;
	private Model myModel;

	public AbstractController() {
		registeredViews = new ArrayList<IView>();
		//registeredModels = new ArrayList<Model>();
		myModel = new Model();
	}

	/*public void addModel(Model model) {
		registeredModels.add(model);
	}*/

	public void addViews(IView view) {
		registeredViews.add(view);
	}

	public void propertyChange(PropertyChangeEvent evt) {

		for (IView view : registeredViews) {
			view.modelPropertyChange(evt);
		}
	}

	public void setModelProperty(List<Turtle> turtles, String commands,
			String language) throws Exception {
		myModel.processCommands(turtles, commands, language);
	}
	
	public List<Turtle> getTurtleList(){
		return myModel.getTurtles();
	}
	
	public List<String> getVariables(){
		return myModel.getVariables();
	}
	/*
	 * Object[] parameters = { newValue1, newValue2, newValue3 };
	 * 
	 * 
	 * try {
	 * 
	 * Method method = model.getClass().getMethod( "processCommands", new
	 * Class[] { newValue1.getClass(), newValue2.getClass(),
	 * newValue3.getClass() } // Check line 37 for errors!
	 * 
	 * ); method.invoke(model, parameters);
	 * 
	 * } catch (Exception ex) { // Handle exception. } }
	 */

}