package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import controller.AbstractController;
import controller.ModelController;
import turtle.ITurtle;
import turtle.Turtle;
import turtle_graphics.TurtleImage;


//this class holds the logic for the main pieces of each window - each has separate parser (model) and text input 
public class WorkspacePanel extends JPanel implements IView{

	private List<String> variables;
	private String curFile;
	private int numTurtles;

	/**
	 * Interfacing from Frontend to Backend via Controller
	 */
	
	private ModelController controller;

	//private ParserTest myParser = new ParserTest();

	//private Turtle myTurtle = new Turtle(0, 0, 0);
	private ProgrammingPanel myProgrammingPanel;
	private ActionDisplayPanel myActionDisplayPanel;
	
	public WorkspacePanel(AbstractController controller){
		variables = new ArrayList<String>();
		this.setBackground(Color.GRAY);
		GridLayout gl = new GridLayout(1,2);

		this.setBorder(
	            BorderFactory.createTitledBorder("Workspace"));
		this.setLayout(gl);
		this.setPreferredSize(new Dimension(1360, 768));
		this.add(setAndMakeActionDisplay());
		this.add(setAndMakeCommandCenter());
		addPropertyListener();
		this.revalidate();
		
		this.controller = (ModelController) controller;
	}

	private ProgrammingPanel setAndMakeCommandCenter() {

		myProgrammingPanel = new ProgrammingPanel();
		return myProgrammingPanel;
	}
	
	
	
	private ActionDisplayPanel setAndMakeActionDisplay(){

		myActionDisplayPanel = new ActionDisplayPanel();
		return myActionDisplayPanel;
	}
	private TurtleDisplayPanel getTurtleDisplayPanel(){
		return myActionDisplayPanel.getInstance();
	}
	private void setTurtleDisplayPanelList(List<TurtleImage> list){
		myActionDisplayPanel.getInstance().setList(list);
	}
	
	private void addPropertyListener(){
		myProgrammingPanel.getTextArea().addPropertyChangeListener("command", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				// TODO Auto-generated method stub
				//myProgrammingPanel.getTextArea().append("Change happened");
				String commandToController = myProgrammingPanel.getCommand();
				List<TurtleImage> listToController = getTurtleDisplayPanel().getTurtleList();
				myActionDisplayPanel.showState();
				try {
					setTurtleDisplayPanelList(controller.passToEnglishModel(listToController, commandToController));
					myActionDisplayPanel.showState();
					variables.addAll(controller.getVariables());
					myProgrammingPanel.addInstanceVariables(variables);
				} 
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
			
		});
	}
	
	public Color getWorkspaceColor(){
		return this.getBackground();
	}
	
	public String getCurrentFile(){
		return curFile;
	}
	
	public int getNumTurtles(){
		return numTurtles;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setBackgroundColor(Color c){
		this.setBackground(c);
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

		
	}
}
