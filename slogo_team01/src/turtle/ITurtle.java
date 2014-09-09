package turtle;

public interface ITurtle {
	

	/*
	 * Coordinate Parameters for get from Turtle in Model
	 */

	double getPrevX();

	double getPrevY();

	double getDeltaX();

	double getDeltaY();

	double getAngle();

	double getPenToggle();
	
	double getCurX();
	
	double getCurY();

	String getPenColor();
}
