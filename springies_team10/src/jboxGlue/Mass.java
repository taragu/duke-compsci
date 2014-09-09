package jboxGlue;

import java.awt.Point;
import globalforces.*;
import jgame.JGColor;
import jgame.JGObject;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;
import springies.Springies;

public class Mass extends PhysicalObject implements Comparable {

	protected static final float DEFAULT_DX = 0;
	protected static final float DEFAULT_DY = 0;
	protected static final float DEFAULT_MASS = 1;
	
    private double mydx;
    private double mydy;
    private float myMass;
    private String myID;
    private double myRadius;
    private int myAssemblyID;

    private boolean myGravityBoolean = true;
    private boolean myViscosityBoolean = true;
    private boolean myCMassBoolean = true;
    private boolean myWall1 = true; // top
    private boolean myWall2 = true; // right
    private boolean myWall3 = true; // bottom
    private boolean myWall4 = true; // left


    private Vec2 myGravityVec;
    private Vec2 myViscosityVec;
    private Vec2 myWallVec;
    private Vec2 myCMassVec;

    private Point myCursor; 

    public double getMass () {
        return myMass;
    }

    public void setCursor(Point p) {
        myCursor = p;
    }

    public boolean getGravityBoolean () {
        return myGravityBoolean;
    }

    public Mass (String name, int collisionId, JGColor color, 
                 double x, double y, float dx, float dy, double radius, float mass, int assemblyID) {
        super(name, collisionId, color);
        this.x = x;
        this.y = y;
        mydx = dx;
        mydy = dy;
        myMass = mass;
        myID = name;
        myRadius = radius;
        myAssemblyID = assemblyID;
        init(radius, mass);
        Vec2 vec = new Vec2(dx,	dy);
        myBody.setLinearVelocity(vec);

    }

    public Mass (String name, int collisionId, JGColor color, 
                 double x, double y, double radius, int assemblyID) {
        this(name, collisionId, color, x, y, DEFAULT_DX, DEFAULT_DY, radius, 1, assemblyID);
    }

    public Mass (String name, int collisionId, JGColor color, 
                 double x, double y, double radius, float mass, int assemblyID) {
        this(name, collisionId, color, x, y, DEFAULT_DX, DEFAULT_DY, radius, mass, assemblyID);
    }

    public Mass (String name, int collisionId, JGColor color, 
                 double x, double y, float dx, float dy, double radius, int assemblyID) {
        this(name, collisionId, color, x, y, dx, dy, radius, 1, assemblyID);
    }

    @Override
    public int compareTo(Object o) {
        Mass otherMass = (Mass) o;
        return (int) (myCursor.distance(this.x, this.y) - myCursor.distance(otherMass.x, otherMass.y));

    }

    private void init(double radius, double mass) {
        // save arguments
        myRadius = radius;
        int intRadius = (int)radius;
        // make it a circle
        CircleDef shape = new CircleDef();
        shape.radius = (float)radius;
        shape.density = (float)mass;
        createBody(shape);
        myBody.m_mass = (float) mass;
        setBBox(-intRadius, -intRadius, 2 * intRadius, 2 * intRadius);
    }

    @Override
    public void hit (JGObject other) {

        // we hit something! bounce off it!
        Vec2 velocity = myBody.getLinearVelocity();
        // is it a tall wall?
        final double DAMPING_FACTOR = 0.8;
        boolean isSide = other.getBBox().height > other.getBBox().width;
        if (isSide) {
            velocity.x *= -DAMPING_FACTOR;
        }
        else {
            velocity.y *= -DAMPING_FACTOR;
        }
        // apply the change
        myBody.setLinearVelocity(velocity);
    }


    @Override
    public void move () {

        // if the JGame object was deleted, remove the physical object too
        if (myBody.m_world != WorldManager.getWorld()) {
            remove();
            return;
        }
        // copy the position and rotation from the JBox world to the JGame world
        Vec2 position = myBody.getPosition();
        x = position.x;
        y = position.y;
        myRotation = -myBody.getAngle();
    }

    public String getID() {
        return myID;
    }

    @Override
    protected void paintShape() {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)myRadius * 2, (float)myRadius * 2, true, true);

        if (myGravityBoolean) { 
            FGravity g = (FGravity) WorldManager.myGlobalForceMapList.get(0).get("gravity");
            myGravityVec = new Vec2((float) (g.getDirection()[0] * g.getGConstant()), 
                                    (float) (g.getDirection()[1] * g.getGConstant()));
            WorldManager.getWorld().setGravity(myGravityVec);
        } else {
            WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.0f));
        }

        if (myViscosityBoolean) {
            FViscosity Vforce = (FViscosity) WorldManager.myGlobalForceMapList.get(0).get("viscosity");
            Vec2 velocity = this.myBody.getLinearVelocity();
            this.setForce(-velocity.x*Vforce.getVConstant(), -velocity.y*Vforce.getVConstant());
        }

        if (myCMassBoolean) {
            FCMass cmForce = (FCMass) WorldManager.myGlobalForceMapList.get(0).get("centermass");
            double[] cmLocation = WorldManager.myCMLocations.get(myAssemblyID);
            double[] forceVec = cmForce.calculateForce(this, cmLocation);
            this.setForce(forceVec[0], forceVec[1]);
        }

        if (myWall1) {
            FWall1 wallForce = (FWall1) WorldManager.myGlobalForceMapList.get(0).get("wall1");
            double forceMag = wallForce.calculateForce(this);
            this.setForce(0, forceMag);
        }
        if (myWall2) {
            FWall2 wallForce = (FWall2) WorldManager.myGlobalForceMapList.get(0).get("wall2");
            double forceMag = wallForce.calculateForce(this);
            this.setForce(forceMag, 0);
        }
        if (myWall3) {
            FWall3 wallForce = (FWall3) WorldManager.myGlobalForceMapList.get(0).get("wall3");
            double forceMag = wallForce.calculateForce(this);
            this.setForce(0, forceMag);
        }
        if (myWall4) {
            FWall4 wallForce = (FWall4) WorldManager.myGlobalForceMapList.get(0).get("wall4");
            double forceMag = wallForce.calculateForce(this);
            this.setForce(forceMag, 0);
        }
    }

    public void switchGravityBoolean(boolean toSet) {
        myGravityBoolean = toSet;
    }

    public void switchViscosityBoolean(boolean toSet) {
        myViscosityBoolean = toSet;
    }

    public void switchWall1Boolean() {
        myWall1 = !myWall1;
    }

    public void switchCMassBoolean(boolean toSet) {
        myCMassBoolean = toSet;
    }

    public boolean getViscosityBoolean () {
        return myViscosityBoolean;
    }

    public boolean getCMassBoolean () {
        return myCMassBoolean;
    }

    public int getAssemblyID(){
        return myAssemblyID;
    }


    public void switchWall2Boolean() {
        myWall2 = !myWall2;
    }
    public void switchWall3Boolean() {
        myWall3 = !myWall3;
    }
    public void switchWall4Boolean() {
        myWall4 = !myWall4;
    }

}
