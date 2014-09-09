package jboxGlue;

import globalforces.FGravity;
import globalforces.GlobalForce;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.xml.parsers.ParserConfigurationException;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.xml.sax.SAXException;
import parse.ParserObjects;
import springies.Springies;


public class WorldManager
{
    public static World ourWorld;
    
    // myGlobalForceMap:  map of global forces for each assembly
    public static List<Map<String, GlobalForce>> myGlobalForceMapList = new ArrayList<Map<String, GlobalForce>>(); 
    public static List<List<Mass>> myMassAssemblies = new ArrayList<List<Mass>>();
    public static List<Map<String, Mass>> myMassMapList = new ArrayList<Map<String, Mass>>();
    public static List<List<Spring>> mySpringAssemblies = new ArrayList<List<Spring>>();
    public static List<double[]> myCMLocations = new ArrayList<double[]>();
    public static List<PhysicalObjectRect> myWalls = new ArrayList<PhysicalObjectRect>();
    public static Map<String, JGColor> myColorMap = new HashMap<String, JGColor>();
    
    private static double WALL_THICKNESS = 10;
    private static final int WALL_COLLISION_ID = 2;

    private static final int NUM_WALLS = 4;
    
    static {
        ourWorld = null;
    }

    public static World getWorld ()
    {
        // make sure we have a world, just in case...
        if (ourWorld == null) {
            throw new RuntimeException("call initWorld() before getWorld()!");
        }
        return ourWorld;
    }

    public static List<Mass> toAllMassesList(){
        List<Mass> allMasses = new ArrayList<Mass>();
        for (List<Mass> thisList : myMassAssemblies) {
            allMasses.addAll(thisList);
        }
        return allMasses;
    }
    
    public static void clearAll(){
        for (List<Mass> thisList: myMassAssemblies) {
            for (Mass thisMass : thisList) {
                thisMass.remove();
            }
        }
        for (List<Spring> thisList: mySpringAssemblies) {
            for (Spring thisSpring : thisList) {
                thisSpring.remove();
            }
        }
    }
    
    public static void initWorld (JGEngine engine) {
        AABB worldBounds = new AABB(new Vec2(0, 0),
                                    new Vec2(engine.displayWidth(), engine.displayHeight()));
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        ourWorld = new World(worldBounds, gravity, true);
    }
    
    public static void loadNewAssembly(File file, int assemblyID) throws XMLParserException{
        Queue<String> tagsForObjects = new LinkedList<String>();
        tagsForObjects.add("model");
        List<String> attributesForMass = new ArrayList<String>();
        List<String> attributesForSpring = new ArrayList<String> ();
        
        ParserObjects parser = new ParserObjects(file, tagsForObjects);
        try {
            parser.doParse();
        }
        catch (Exception e) {
            throw new XMLParserException();
        }

        WorldManager.myMassAssemblies.add(parser.getAllMasses());
        WorldManager.myMassMapList.add(parser.getMassMap());
        WorldManager.mySpringAssemblies.add(parser.getSpringList());
        WorldManager.myCMLocations.add(parser.getCMLocation());
        // compute center of mass
        parser.computeCenterOfMass(parser.getAllMasses());
    }

    public static void addWalls (JGEngine engine, double amt) {
        // add walls to bounce off of
        // NOTE: immovable objects must have no mass
        final double WALL_MARGIN = 10;
        WALL_THICKNESS = WALL_THICKNESS + amt;
        final double WALL_WIDTH = engine.displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
        final double WALL_HEIGHT = engine.displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
        // make it a list so that it's more extendable and not restricted to only 4 walls
        List<PhysicalObjectRect> wallList = new ArrayList<PhysicalObjectRect>();
        for (int i=0;i<NUM_WALLS;i++) {
            if (i%2 == 0) {
                wallList.add(new PhysicalObjectRect("wall", WALL_COLLISION_ID, JGColor.green,WALL_WIDTH, WALL_THICKNESS));
            }
            if (i%2 == 1) {
                wallList.add(new PhysicalObjectRect("wall", WALL_COLLISION_ID, JGColor.green, WALL_THICKNESS, WALL_HEIGHT));
            }
        }
        // this part is more specific to having only 4 walls; change this part if number of walls is greater than 4
        wallList.get(0).setPos(engine.displayWidth() / 2, WALL_MARGIN);
        wallList.get(1).setPos(engine.displayWidth() - WALL_MARGIN, engine.displayHeight() / 2);
        wallList.get(2).setPos(engine.displayWidth() / 2, engine.displayHeight() - WALL_MARGIN);
        wallList.get(3).setPos(WALL_MARGIN, engine.displayHeight() / 2);
        WorldManager.myWalls.addAll(wallList);
    }
}
