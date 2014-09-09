package jboxGlue;

import jgame.JGColor;

public class Muscle extends Spring {
    
	private static final int DEFAULT_TIME_INCREMENT = 5;
	
	private double myTime = 1;
    private double myAmplitude;
    
    public Muscle (String name, int collisionId, JGColor color, Mass[] list,
                      double restlength, double kconstant, double amplitude) {

        super(name,collisionId, color, list, restlength, kconstant);
        myAmplitude = amplitude;
    }

    public Muscle (String name, int collisionId, JGColor color, Mass[] list,
                      double restlength, double amplitude) {
        this(name, collisionId, color, list, restlength, 1, amplitude);
    }

    public void contractMuscle() {
    	myRestLength = myRestLength + myAmplitude*Math.sin(myTime);
    	myTime = myTime + DEFAULT_TIME_INCREMENT;
    	
    	if (Math.sin(myTime) >=0) {
    	    this.setColor(JGColor.magenta);
    	} else {
            this.setColor(JGColor.green);
        }
    }
    
    public void changeAmplitude(double amt) {
    	myAmplitude += amt;
    }
    
    @Override
    public void move(){
        // calculate force; find dx --> times spring constant --> call setForce
    	contractMuscle();
    	
        double t1 = Math.pow((myConnectList[0].x - myConnectList[1].x), 2);
        double t2 = Math.pow((myConnectList[0].y - myConnectList[1].y), 2);
        double distance = Math.sqrt(t1+t2);
        double delta_x = myRestLength - distance;
                                   
        double force = delta_x * myKConstant;
        double i = (myConnectList[0].x - myConnectList[1].x) / distance;
        double j = (myConnectList[0].y - myConnectList[1].y) / distance;
        double force_i = force*i;
        double force_j = force*j;
        myConnectList[0].setForce(force_i, force_j);
        
    }
}
