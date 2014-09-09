package turtle_graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import turtle.ITurtle;

public class TurtleImage implements ITurtle {

	private int myWidth;
	private int myHeight;
	private int myTopLeftX;
	private int myTopLeftY;
	private BufferedImage displayTurtle;
	private double curX;
	private double curY;
	private double prevX;
	private double prevY;
	private double deltaX;
	private double deltaY;
	private double myAngle;
	private double penToggle;
	private Color myColor;
	private List<Line2D> lineList;
	private Map<Line2D, Color> lineColorMap;
	private BufferedImage myTurtleImage;
	private File myTurtleFile;

	public TurtleImage(int x, int y) {
		curX = (double) x / 2;
		curY = (double) y / 2;
		prevX = x;
		prevY = y;
		myAngle = 0;
		lineList = new ArrayList<Line2D>();
		lineColorMap = new HashMap<Line2D, Color>();
		myTurtleFile = TurtleFileChooser.chooseImageFile();
		try {
			displayTurtle = setImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage setImage() throws IOException {

		myTurtleImage = ImageIO.read(myTurtleFile);
		myWidth = myTurtleImage.getWidth();
		myHeight = myTurtleImage.getHeight();
		return myTurtleImage;
	}

	/*
	 * Rotates image on screen. Step 1: Save state of image via AffineTransform.
	 * Step 2: Move relative to current location via translate and dX & dY. Step
	 * 3: Rotate about center of object. Step 4: Draw. Step 5: Restore from Step
	 * 2 to Step 1.
	 */

	public void paintTurtle(Graphics2D pen) {

		initializeTopLeftCorner(prevX, prevY);

		AffineTransform old = new AffineTransform(pen.getTransform());
		pen.translate(deltaX, deltaY);
		pen.rotate(Math.toRadians(-myAngle), prevX, prevY);
		// image drawn at top left corner
		// taking rotation into account
		pen.drawImage(displayTurtle, myTopLeftX, myTopLeftY, null);
		pen.setTransform(old);
	}

	public void paintLines(Graphics2D pen, Color penColor, int myPen) {
		penToggle = (double) myPen;
		myColor = penColor;
		if (myPen == 1) {
			checkLineAddition(penColor);
		}	
		for (Line2D line : lineList) {
				pen.setColor(lineColorMap.get(line));
				pen.draw((Shape) line);
		}
	}

	private void initializeTopLeftCorner(double xCenter, double yCenter) {
		// TODO Auto-generated method stub
		myTopLeftX = ((int) xCenter) - myWidth / 2;
		myTopLeftY = ((int) yCenter) - myHeight / 2;
	}

	private void checkLineAddition(Color penColor) {
		if (prevX != curX | prevY != curY) {
			Line2D newLine = new Line2D.Double(prevX, prevY, curX, curY);
			lineColorMap.put(newLine, penColor);
			lineList.add(newLine);
		}

	}

	public void reinitialTurtle() {
		updateTurtleState(0, 0, this.myAngle);
	}

	public void moveTurtleStandardButtons(double deltaX, double deltaY) {
		updateTurtleState(deltaX, deltaY, this.myAngle);
	}

	public void updateTurtleState(double deltaX, double deltaY, double heading) {
		this.prevX = this.curX;
		this.prevY = this.curY;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.curX = this.prevX + this.deltaX;
		this.curY = this.prevY + this.deltaY;
		this.myAngle = heading;
	}

	public void rotateTurtleRight90() {
		myAngle -= 90;
		if (myAngle <= -360) {
			myAngle = myAngle + 360d;
		}
	}

	public String getCanvasStateInfo(int panelWidth, int panelHeight) {
		double matchedX;
		double matchedY;
		matchedX = curX - (double) panelWidth / 2;
		matchedY = -(curY - (double) panelHeight / 2);

		String positionInfo = "The coordinates of turtle are (" + matchedX
				+ "," + matchedY + ") \n";
		String angleInfo = "The turtle's heading is (" + myAngle + ") \n";
		return positionInfo + angleInfo;
	}

	public void setTurtleCenter(int panelWidth, int panelHeight) {
		curX = (double) (panelWidth / 2);
		curY = (double) (panelHeight / 2);
		prevX = curX;
		prevY = curY;
		deltaX = 0;
		deltaY = 0;
		myAngle = 0;
		lineList.clear();
	}

	@Override
	public double getPrevX() {
		return prevX;
	}

	@Override
	public double getPrevY() {
		return prevY;
	}

	@Override
	public double getDeltaX() {
		return deltaX;
	}

	@Override
	public double getDeltaY() {
		return deltaY;
	}

	@Override
	public double getAngle() {
		return myAngle;
	}

	@Override
	public double getPenToggle() {
		// TODO Auto-generated method stub
		return 0;//penToggle;
	}

	@Override
	public double getCurX() {
		return curX;
	}

	@Override
	public double getCurY() {
		return curY;
	}

	@Override
	public String getPenColor() {
		return null; //myColor.toString();
	}
}
