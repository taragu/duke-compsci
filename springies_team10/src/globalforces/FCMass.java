package globalforces;

import java.util.List;
import jboxGlue.Mass;


public class FCMass extends GlobalForce {
    private List<Mass> myMassesList;
    private double myCMConstant;
    private double myExponent;

    
    public FCMass(double constant, double exponent) {
    	myExponent = exponent;
    	myCMConstant = constant;
    }
    
    public double getExponent () {
        return myExponent;
    }

    public void setExponent (double myExponent) {
        this.myExponent = myExponent;
    }
    
    public List<Mass> getMassesList () {
        return myMassesList;
    }

    public void setMassesList (List<Mass> myMassesList) {
        this.myMassesList = myMassesList;
    }

    public double getCMConstant () {
        return myCMConstant;
    }

    public void setCMConstant (double myCMConstant) {
        this.myCMConstant = myCMConstant;
    }

    public double[] calculateForce(Mass mass, double[] cmLoc) {
        double[] toReturn = new double[2];      
        double xDir = (cmLoc[0] - mass.x) / Math.abs((cmLoc[0] - mass.x));
        double yDir = (cmLoc[1] - mass.y) / Math.abs((cmLoc[1] - mass.y));
        double temp1 = Math.pow((cmLoc[0] - mass.x), 2);
        double temp2 = Math.pow((cmLoc[1] - mass.y), 2);
        double distance = Math.sqrt(temp1+temp2);
        toReturn[0] = xDir * (temp1/distance) * myCMConstant / Math.pow(distance, myExponent);
        toReturn[1] = yDir * (temp2/distance) * myCMConstant / Math.pow(distance, myExponent);
        return toReturn;
    }
}
