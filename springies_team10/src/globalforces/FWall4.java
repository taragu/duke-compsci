package globalforces;

import jboxGlue.Mass;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGObject;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class FWall4 extends FWall {
    
    
    public FWall4(double magnitude, double exponent, int id) {
    	super(magnitude, exponent, id);
    }

    public double calculateForce(Mass mass) {
    	double toReturn = 0;
    	PhysicalObjectRect r1 = WorldManager.myWalls.get(3);
    	double distance = mass.x - r1.x;
    	toReturn = myWallConstant / Math.pow(distance, myExponent);
    	return toReturn;
    }
    
}
