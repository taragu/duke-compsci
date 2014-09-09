package globalforces;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class FViscosity extends GlobalForce {
    
    private double myVConstant; // viscosity constant provided in the data files
    
    public FViscosity(double vconst) {
    	myVConstant = vconst;
    }
    
    public void setVConstant(double constant) {
        this.myVConstant = constant;
    }
    
    public double getVConstant(){
        return myVConstant;
    }
    
    /**
     * calculate new velocity
     * @param body
     * @return
     */
    public void changeVelocity(Body body){
        Vec2 velocity = body.getLinearVelocity();
        double[] toReturn = new double[2];
        velocity.x = (float) (-velocity.x*myVConstant);
        velocity.y = (float) (-velocity.y*myVConstant);
    }
}
