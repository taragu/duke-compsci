package globalforces;
import jboxGlue.Mass;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGObject;

import org.jbox2d.dynamics.Body;

public abstract class FWall extends GlobalForce {

	
    protected double myWallConstant;
    protected double myExponent;
    protected Body myBody;
    protected JGObject myOther; // the object myBody hits
    protected int myID;
    
	public FWall(double magnitude, double exponent, int id) {
    	myWallConstant = magnitude;
    	myExponent = exponent;
    	myID = id;
	}
	
	public double getWallConstant () {
        return myWallConstant;
    }

    public void setWallConstant (double myWallConstant) {
        this.myWallConstant = myWallConstant;
    }
    
    public Body getBody () {
        return myBody;
    }

    public void setBody (Body myBody) {
        this.myBody = myBody;
    }

    public JGObject getOther () {
        return myOther;
    }

    public void setOther (JGObject myOther) {
        this.myOther = myOther;
    }
    
    public abstract double calculateForce(Mass mass);

}
