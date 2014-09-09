package globalforces;

import jboxGlue.Mass;

public class FGravity extends GlobalForce {
    private double myGConstant;
    private double[] myDirection;
    private double myAngle; // in degree

    public FGravity(double gConstant, double angle) {
        myGConstant = gConstant;
        myAngle = angle; // in degree
        myDirection = calculateDirection();
    }

    public double getGConstant(){
        return myGConstant;
    }

    public double[] getDirection(){
        return myDirection;
    }

    public double[] calculateDirection(){
        try {
            double rad = myAngle * Math.PI / 180;
            double[] direction = {Math.cos(rad), Math.sin(rad)};
            myDirection = direction;
            return direction;
        } catch (NullPointerException e) {
            return new double[]{0.0, 0.0};
        }
    }
}
