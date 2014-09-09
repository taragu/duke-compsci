package turtle;

import java.util.HashMap;
import java.util.Map;

public class Turtle extends AbstractModel implements ITurtle{

	// public Turtle() {
	// TODO: set default initial turtle position
	// }
	// TODO ONE BACKEND FOR ONE GAME
	// keep track of what's active: parse once and apply the changes to active
	// turtles
	// TODO expand the backend: model;

	private final double PEN_DOWN = 1;
	private final double PEN_UP = 0;
	private final double VISIBLE = 1;
	private final double INVISIBLE = 0;
	private final double DEFAULT_HEADING = 0;

	private final String ORANGE = "ORANGE";
	private final String PURPLE = "PURPLE";
	private final String YELLOW = "YELLOW";
	private final String BROWN = "BROWN";
	private final String BLACK = "BLACK";
	private final String GREEN = "GREEN";
	private final String PINK = "PINK";
	private final String BLUE = "BLUE";
	private final String RED = "RED";

	private final String TRIANGLE = "TRIANGLE";
	private final String RECTANGLE = "RECTANGLE";
	private final String SQUARE = "SQUARE";
	private final String PENTAGON = "PENTAGON";
	private final String HEXAGON = "HEXAGON";

	Map<Double, String> shapeMapping = new HashMap<Double, String>();
	Map<Double, String> colorMapping = new HashMap<Double, String>();

	private double myX;
	private double myY;
	private double myDeltaX;
	private double myDeltaY;
	private double myPrevX;
	private double myPrevY;
	private double myPen;
	private String myPenColor;
	private String myLanguage;
	private String myTurtleShape;
	private double clearToggle;
	private double myPenColorIndex;
	private double myShapeIndex;
	private double myVisibility;
	private double myAngle = DEFAULT_HEADING;

	private String myID; // TODO assign ID to turtles (done in class Model)

	/**
	 * 0 degrees: Up. 90 degrees: left. 180 degrees: down. 270 degrees: right.
	 */

	public Turtle() {
		// TODO: set default initial turtle position
	}

	/*
	 * myShape left uninitialized because default is image.
	 */

	public Turtle(double initialX, double initialY, double angle) {
		myX = initialX;
		myY = initialY;
		//myLanguage = language;
		myAngle = DEFAULT_HEADING;
		myPen = PEN_UP;
		myPenColor = BLACK;
		myPenColorIndex = 9;
		myShapeIndex = 0;
		mapColors();
	}
	
	public Turtle(ITurtle iturtle){
		myAngle = iturtle.getAngle();
		myX = iturtle.getCurX();
		myY = iturtle.getCurY();
		myDeltaX = iturtle.getDeltaX();
		myDeltaY = iturtle.getDeltaY();
		myPenColor = iturtle.getPenColor();
		myPen = iturtle.getPenToggle();
	}

	/*
	 * Display Mappings below.
	 */

	private void mapColors() {

		colorMapping.put((double) 1, PINK);
		colorMapping.put((double) 2, RED);
		colorMapping.put((double) 3, ORANGE);
		colorMapping.put((double) 4, YELLOW);
		colorMapping.put((double) 5, GREEN);
		colorMapping.put((double) 6, BLUE);
		colorMapping.put((double) 7, PURPLE);
		colorMapping.put((double) 8, BROWN);
		colorMapping.put((double) 9, BLACK);
	}

	private void mapShapes() {
		
		shapeMapping.put((double) 1, TRIANGLE);
		shapeMapping.put((double) 2, RECTANGLE);
		shapeMapping.put((double) 3, SQUARE);
		shapeMapping.put((double) 4, PENTAGON);
		shapeMapping.put((double) 5, HEXAGON);
	}

	public void setPenColor(double colorIndex) {
		myPenColor = colorMapping.get(colorIndex);

	}
	
	public void setTurtleShape(double shapeIndex) {
		myTurtleShape = shapeMapping.get(shapeIndex);

	}

	public void setID(String id) {
		myID = id;
	}

	public String getID() {
		return myID;
	}

	public void updatePosition(double changeInAngle, double changeInDistance) {
		myAngle += changeInAngle;
		if (myAngle >= 360) {
			myAngle -= 360;
		}

		myDeltaX = -changeInDistance * Math.sin(myAngle * (Math.PI / 180));
		myDeltaY = -changeInDistance * Math.cos(myAngle * (Math.PI / 180));
		
		myPrevX = myX;
		myPrevY = myY;
		
		myX += changeInDistance * Math.cos(myAngle * (Math.PI / 180));
		myY += changeInDistance * Math.sin(myAngle * (Math.PI / 180));
	}

	/**
	 * Benson to Tara: Couldn't think of anything better than to create a toggle
	 * for the clear.
	 */

	public void clearScreen() {
		clearToggle = 1;
	}

	public void resetClear() {
		clearToggle = 1;
	}

	/*
	 * Sets
	 */

	public void setPosition(double xCoord, double yCoord) {
		myX = xCoord;
		myY = yCoord;
	}

	public void setHeading(double angle) {
		myAngle = angle;
	}

	public void setPenUp() {
		myPen = PEN_UP;
	}

	public void setPenDown() {
		myPen = PEN_DOWN;
	}

	public void setInvisible() {
		myVisibility = INVISIBLE;
	}

	public void setVisible() {
		myVisibility = VISIBLE;
	}

	/*
	 * Gets
	 */

	public double getXPos() {
		return myX;
	}

	public double getYPos() {
		return myY;
	}

	public double getAngle() {
		return myAngle;
	}

	public double getPen() {
		return myPen;
	}

	public double getVisibility() {
		return myVisibility;
	}

	public String getLangauge() {
		return myLanguage;
	}
	
	public double getPenColorIndex(){
		return myPenColorIndex;
	}
	
	public double getMyShapeIndex(){
		return myShapeIndex;
	}
	
	/**
	 * ITurtle Methods Below
	 */

	@Override
	public double getPrevX() {
		// TODO Auto-generated method stub
		return myPrevX;
	}

	@Override
	public double getPrevY() {
		// TODO Auto-generated method stub
		return myPrevY;
	}

	@Override
	public double getDeltaX() {
		// TODO Auto-generated method stub
		return myDeltaX;
	}

	@Override
	public double getDeltaY() {
		// TODO Auto-generated method stub
		return myDeltaY;
	}

	@Override
	public double getPenToggle() {
		// TODO Auto-generated method stub
		return myPen;
	}

	@Override
	public String getPenColor() {
		// TODO Auto-generated method stub
		return myPenColor;
	}

	@Override
	public double getCurX() {
		// TODO Auto-generated method stub
		return myX;
	}

	@Override
	public double getCurY() {
		// TODO Auto-generated method stub
		return myY;
	}

}

