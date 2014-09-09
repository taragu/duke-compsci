package globalforces;

import jboxGlue.Mass;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGObject;
import jgame.impl.JGEngineInterface;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class FWall1 extends FWall {
    
    
    public FWall1(double magnitude, double exponent, int id) {
    	super(magnitude, exponent, id);

    }
    
    
    public double calculateForce(Mass mass) {
    	double toReturn = 0;
    	PhysicalObjectRect r1 = WorldManager.myWalls.get(0);
    	double distance = mass.y - r1.y;
    	toReturn = myWallConstant / Math.pow(distance, myExponent);
    	return toReturn;
    }
}
