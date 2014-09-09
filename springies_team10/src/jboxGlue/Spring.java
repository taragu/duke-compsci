package jboxGlue;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import jgame.JGColor;

public class Spring extends PhysicalObject {

    protected Mass [] myConnectList = new Mass[2]; 
    protected double myRestLength;
    protected double myKConstant;


    public Spring (String name, int collisionId, JGColor color, Mass[] list, 
                   double restlength, double kconstant) {
        super(name, collisionId, color);
        myConnectList = list;
        myRestLength = restlength;
        myKConstant = kconstant;
        init(restlength);
    }

    public Spring (String name, int collisionId, JGColor color, Mass[] list, 
                   double restlength) {
        this(name, collisionId, color, list, restlength, 1);
    }

    public void init (double length) {
        myRestLength = length;
        float width = (float) 0.01;
        // make it a rect
        PolygonDef shape = new PolygonDef();
        shape.setAsBox(width, (float) length);
        createBody(shape);
        setBBox(-(int) width / 2, -(int) length / 2, (int) width, (int) length);
    }

    @Override
    public void move(){
        if (myConnectList[0] == null || myConnectList[1] == null) {
            this.remove();
        }
    }

    @Override
    protected void paintShape() {
        if (myConnectList[0]!=null && myConnectList[1]!=null) {
            myEngine.setColor(myColor);
            double t1 = Math.pow((myConnectList[0].x - myConnectList[1].x), 2);
            double t2 = Math.pow((myConnectList[0].y - myConnectList[1].y), 2);
            double distance = Math.sqrt(t1+t2);
            double delta_x = distance - myRestLength;
            
            if (delta_x >= 0) {
                this.setColor(JGColor.orange);
            } else {
                this.setColor(JGColor.green);
            }

            double force = delta_x * myKConstant;
            double i = (myConnectList[1].x - myConnectList[0].x) / distance;
            double j = (myConnectList[1].y - myConnectList[0].y) / distance;
            double force_i = force*i;
            double force_j = force*j;
            myConnectList[0].setForce(force_i, force_j);
            myConnectList[1].setForce(-force_i,-force_j);
            myEngine.drawLine(myConnectList[0].x, myConnectList[0].y, myConnectList[1].x, 
                              myConnectList[1].y);
        }
    }
}
