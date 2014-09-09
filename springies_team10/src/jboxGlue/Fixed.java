package jboxGlue;

import jgame.JGColor;
import jgame.JGObject;

public class Fixed extends Mass {

    public Fixed (String name, int collisionId, JGColor color, 
                    double x, double y, float dx, float dy, double radius, float mass, int assemblyID) {
        super(name, collisionId, color,x, y, dx, dy, radius, mass, assemblyID);
    }
    
    public Fixed (String name, int collisionId, JGColor color, 
                    double x, double y, double radius, int assemblyID) {
        this(name, collisionId, color,x,y, DEFAULT_DX, DEFAULT_DY, radius, 1, assemblyID);
    }
    
    public Fixed (String name, int collisionId, JGColor color, 
                    double x, double y, double radius, float mass, int assemblyID) {
        this(name, collisionId, color,x,y, DEFAULT_DX, DEFAULT_DY, radius,mass, assemblyID);
    }
    
    public Fixed (String name, int collisionId, JGColor color, 
                    double x, double y, float dx, float dy, double radius, int assemblyID) {
        this(name, collisionId, color,x,y,dx,dy,radius, DEFAULT_MASS, assemblyID);
    }
    
    @Override
    public void move(){
        //do nothing
    }
    
    @Override
    public void hit (JGObject other) {
        //do nothing
    }
}
