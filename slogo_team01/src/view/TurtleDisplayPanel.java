package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import turtle.ITurtle;
import turtle.Turtle;
import turtle_graphics.TurtleImage;

//choose JPanel because this is more of a container
public class TurtleDisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private Graphics2D g2d;
	private boolean initialize;
	private List<TurtleImage> frontTurtleList;
	private Color penColor;
	private static final Color Default_Pen_Color = Color.black;
	private int myPen;

	/*
	 * Pass in ITurtle from backend into TurtleDisplayPanel
	 */

	// private List<ITurtle> myFrontEndTurtles = ;

	public TurtleDisplayPanel() {

		this.setBackground(Color.white);
		initialize = true;
		penColor = Default_Pen_Color;

		frontTurtleList = new ArrayList<TurtleImage>();
		createNewTurtleImage();
	}

	public void createNewTurtleImage() {
		// TODO Auto-generated method stub
		frontTurtleList.add(new TurtleImage(this.getWidth(), this.getHeight()));

		if (!initialize) {
			updateTurtles();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g2d = (Graphics2D) g;
		if (initialize) {
			setCenter();
		}
		for (TurtleImage t : frontTurtleList) {
			t.paintTurtle(g2d);
			t.paintLines(g2d, penColor, myPen);
		}
		initialize = false;
	}

	public void moveTurtleLeft() {
		for (TurtleImage t : frontTurtleList) {
			t.moveTurtleStandardButtons(-5d, 0);
		}

		repaint();
	}

	public void moveTurtleRight() {
		for (TurtleImage t : frontTurtleList) {
			t.moveTurtleStandardButtons(5d, 0);
		}

		repaint();
	}

	public void moveTurtleDown() {
		for (TurtleImage t : frontTurtleList) {
			t.moveTurtleStandardButtons(0, 5d);
		}
		repaint();
	}

	public void moveTurtleForward() {
		for (TurtleImage t : frontTurtleList) {
			t.moveTurtleStandardButtons(0, -5d);
		}
		repaint();
	}

	public void rotateTurtlesRight() {
		for (TurtleImage t : frontTurtleList) {
			t.rotateTurtleRight90();
		}
		repaint();
	}

	public String getAllPositionInfos() {
		String stateInfo = "";
		for (TurtleImage t : frontTurtleList) {
			stateInfo += t.getCanvasStateInfo(this.getWidth(), this.getHeight());
		}
		return stateInfo;
	}

	public Color getColor() {
		return penColor;
	}

	public List<TurtleImage> getTurtleList() {
		return frontTurtleList;
	}

	public void setPenToggle() {
		if (myPen == 0)
			myPen = 1;
		else
			myPen = 0;
	}

	public void setColor(Color c) {
		penColor = c;
	}

	// set it to private as it is never called by external methods
	private void setCenter() {
		for (TurtleImage t : frontTurtleList) {
			t.setTurtleCenter(this.getWidth(), this.getHeight());
		}
	}

	public void resetTurtle() {
		setCenter();
		repaint();
	}

	private void updateTurtles() {
		// TODO Auto-generated method stub
		for (TurtleImage t : frontTurtleList) {
			t.reinitialTurtle();
		}
		repaint();
	}

	public void setList(List<TurtleImage> list) {
		// TODO Auto-generated method stub
		frontTurtleList = list;
		//updateTurtles();
		repaint();
	}

}
