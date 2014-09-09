package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import controller.AbstractController;
import controller.ModelController;
import functionStorage.FunctionMenu;
import view.WorkspacePanel;
import view.menuComponents.ColorMenuComponent;
import view.menuComponents.FileMenuComponent;

public class SLogo extends JFrame {

	//List of workspaces on the View
	private List<WorkspacePanel> workspacePanels;
	private JTabbedPane workspaces;
	private int workspaceCount=1;
	private List<Preferences> preferences;
	private List<Preferences> savedPreferences;

	public SLogo(){
		super("SLogo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1600, 900));

		JMenuBar menuBar = buildMenuBar();
		setJMenuBar(menuBar);

		workspacePanels = new ArrayList<WorkspacePanel>();
		workspaces = new JTabbedPane();
		addNewWorkspace();
		add(workspaces);

		setDefaultPreference();

		validate();
		pack();
		repaint();

		setVisible(true);
	}

	private void setDefaultPreference() {
		//Preferences defaultPref = = Preferences.userRoot().node(GENERAL_LOOK_NODE);
	}

	private JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		for (FileMenuComponent component : FileMenuComponent.values()){
			fileMenu.add(makeMenuItem(component.getLabel(), component.getCommand()));
		}

		JMenu editMenu = new JMenu("Display");
		editMenu.add(createColorsMenu());
		menuBar.add(editMenu);
		
		menuBar.add(new FunctionMenu("Functions"));	
		return menuBar;
	}
	
	private JComponent createColorsMenu(){

		JMenu colors = new JMenu("Choose background Color");
		for (final ColorMenuComponent color : ColorMenuComponent.values()){
			
			JMenuItem menuItemToAdd = makeColorMenuItem(color.name(), new ActionListener(){	
				@Override
				public void actionPerformed(ActionEvent e) {
					workspaces.getSelectedComponent().setBackground(color.getColor());	
				}
			});
			
			colors.add(menuItemToAdd);
		}
		return colors;
	}

	private void addNewWorkspace(){
		AbstractController currentController = new ModelController();
		WorkspacePanel temp = new WorkspacePanel(currentController);
		workspaces.add("workspace "+workspaceCount,temp);
		workspaceCount++;
		workspacePanels.add(temp);
	}

	public static void main(String[] args) {
		SLogo mySLogo = new SLogo();
		//createAndShowMainWindow();
	}
	
	private JMenuItem makeMenuItem(String label, String method){
		JMenuItem menuItemToAdd = new JMenuItem(label);
		try {		
			final Method onClickMethod = SLogo.class.getDeclaredMethod(method);
			menuItemToAdd.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						onClickMethod.setAccessible(true);
						onClickMethod.invoke(SLogo.this);
						onClickMethod.setAccessible(false);
					} 	
					catch (Exception e1) {} 
				}
			});
	
		} catch (Exception e1) {}
				
		return menuItemToAdd;
	}
	
	private JMenuItem makeColorMenuItem(String label, ActionListener listener){
		JMenuItem result = new JMenuItem(label);
		result.addActionListener(listener);
		return result;
		
	}
	
}