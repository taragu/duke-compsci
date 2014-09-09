package springies;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JFileChooser;
import parse.ParserEnvironment;
import parse.ParserObjects;
import jboxGlue.Mass;
import jboxGlue.Muscle;
import jboxGlue.Spring;
import jboxGlue.WorldManager;
import jboxGlue.XMLParserException;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGPoint;
import jgame.platform.JGEngine;


@SuppressWarnings("serial")
public class Springies extends JGEngine
{
    private boolean hasSpring = false;
    private static final JFileChooser INPUT_CHOOSER = new JFileChooser(System.getProperties().getProperty("user.dir"));
    private boolean myGravityBooleanSpringies = true;
    private boolean myViscosityBooleanSpringies = true;
    private boolean myCMassBooleanSpringies = true;
    private boolean myWall1Force = true;
    private boolean myWall2Force = true;
    private boolean myWall3Force = true;
    private boolean myWall4Force = true;
    private static int assemblyID = 0;
    
    private String myObjectsToParse = "lamp.xml";
    private String myEnvironmentToParse = "gravityandviscosity.xml";
    private String myConstantsToParse = "constants.xml";
    
//    private ObjectManager om;

    public Springies (){
        // set the window size
        int height = 600;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
    }

    @Override
    public void initCanvas () {
        // I have no idea what tiles do...
        setCanvasSettings(1, // width of the canvas in tiles
                          1, // height of the canvas in tiles
                          displayWidth(), // width of one tile
                          displayHeight(), // height of one tile
                          null,// foreground colour -> use default colour white
                          null,// background colour -> use default colour black
                          null); // standard font -> use default font
    }

    @Override
    public void initGame () {
        setFrameRate(60, 2);
        // NOTE:
        //   world coordinates have y pointing down
        //   game coordinates have y pointing up
        // so gravity is up in world coords and down in game coords
        // so set all directions (e.g., forces, velocities) in world coords
        WorldManager.initWorld(this);
//        om = new ObjectManager(this);
        WorldManager.addWalls(this, 1);
        hasSpring = false;
        File fileEnvironment = new File(myEnvironmentToParse);
        File fileObjects = new File(myObjectsToParse);
        File fileConstants = new File(myConstantsToParse);
        Queue<String> tagsForObjects = new LinkedList<String>();
        tagsForObjects.add("environment");
        ParserEnvironment parseEnvironment = new ParserEnvironment(fileEnvironment, tagsForObjects);
        try {
            parseEnvironment.doParseAndCreate();
            WorldManager.loadNewAssembly(fileObjects, assemblyID++);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        setMsgFont(new JGFont("Cambria",0,15));
    }

    @Override
    public void doFrame () {
        try {
            handleKeys(getLastKey());
        }
        catch (XMLParserException e) {
            e.printStackTrace();
        }
        clearLastKey();
        // update game objects
        WorldManager.getWorld().step(1f, 1);
        moveObjects();
        checkCollision(1+2, 1);
    }

    private void handleKeys (int key) throws XMLParserException {
    	
        if (getKey(KeyMouse1)) {
            if (!hasSpring) {
                hasSpring = true;
                JGPoint locationPoint= getMousePos();
                Point mousePoint = new Point(locationPoint.x, locationPoint.y);
                List<Mass> allMasses = new ArrayList<Mass>();
                allMasses = WorldManager.toAllMassesList(); 
                for (Mass m : allMasses) {
                    m.setCursor(mousePoint);
                }
                Collections.sort(allMasses);
                Mass nearestMass = allMasses.get(0); //TEST
                moveNearestMass(mousePoint, nearestMass);
            } else { //hasSpring
            }
        } else {
            hasSpring = false;
        }
        //load new assembly
        if (key == KeyEvent.VK_N) {
            loadNewAssembly();
        }
        //clear all assemblies
        if (key == KeyEvent.VK_C) {
            WorldManager.clearAll();
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
            for (int i=0; i<WorldManager.myWalls.size(); i++){
                WorldManager.myWalls.get(i).remove();
            }
            WorldManager.myWalls.clear();
            if (key == KeyEvent.VK_UP) {
                WorldManager.addWalls(this,15);
            } else {
                WorldManager.addWalls(this,-15);
            }
        }

        for (List<Spring> thisList : WorldManager.mySpringAssemblies) {
        	for (Spring thisSpring : thisList) {
        		if (thisSpring instanceof Muscle) {
        			if (key == KeyEvent.VK_EQUALS) {
            			((Muscle) thisSpring).changeAmplitude(5);
        			}
        			if (key == KeyEvent.VK_MINUS) {
            			((Muscle) thisSpring).changeAmplitude(-5);
        			}
        		}
        	}
        }
        
        for (List<Mass> thisList : WorldManager.myMassAssemblies) {
            for (Mass thisMass : thisList){

                if (key == KeyEvent.VK_G) {
                    //turn on and off gravity
                    thisMass.switchGravityBoolean(!thisMass.getGravityBoolean());
                    myGravityBooleanSpringies = !myGravityBooleanSpringies;
                    System.out.println("**springies doframe: key pressed, thisMass's gravity boolean is "
                            + thisMass.getGravityBoolean());
                }
                

                if (key == KeyEvent.VK_V) {
                    //turn on and off viscosity
                    thisMass.switchViscosityBoolean(!thisMass.getViscosityBoolean());
                    myViscosityBooleanSpringies = !myViscosityBooleanSpringies;
                    System.out.println("**springies doframe: key pressed, thisMass's viscosity boolean is "
                            + thisMass.getViscosityBoolean());
                }

                if (key == KeyEvent.VK_M) {
                    //turn on and off center of mass
                    thisMass.switchCMassBoolean(!thisMass.getCMassBoolean());
                    myCMassBooleanSpringies = !myCMassBooleanSpringies;
                    System.out.println("**springies doframe: key pressed, thisMass's cmass boolean is "
                            + thisMass.getCMassBoolean());
                }
                
                if (key == KeyEvent.VK_1) {
                	WorldManager.myWalls.get(0).toggleForce();
                	thisMass.switchWall1Boolean();
                	myWall1Force = !myWall1Force;
                }
                if (key == KeyEvent.VK_2) {
                	WorldManager.myWalls.get(1).toggleForce();
                	thisMass.switchWall2Boolean();
                	myWall2Force = !myWall2Force;
                }
                if (key == KeyEvent.VK_3) {
                	WorldManager.myWalls.get(2).toggleForce();
                	thisMass.switchWall3Boolean();
                	myWall3Force = !myWall3Force;
                }
                if (key == KeyEvent.VK_4) {
                	WorldManager.myWalls.get(3).toggleForce();
                	thisMass.switchWall4Boolean();
                	myWall4Force = !myWall4Force;
                }
                
            }
        }
    }

    public void loadNewAssembly () throws XMLParserException {
        int loadedFile = INPUT_CHOOSER.showOpenDialog(null);
        if (loadedFile == JFileChooser.APPROVE_OPTION) {
            // parse XML file: INPUT_CHOOSER.getSelectedFile();
            File file = INPUT_CHOOSER.getSelectedFile();
        	WorldManager.loadNewAssembly(file, assemblyID++);
        }
    }

    private void moveNearestMass (Point mousePoint, Mass nearestMass) {
        // create a spring between the nearest mass and the mouse location
        Mass mouseMass = new Mass("mousemass", 1, JGColor.cyan, mousePoint.x, mousePoint.y,
                                  0.00001, 0.00001f, nearestMass.getAssemblyID()); // the 1000 is so it doesn't interfere with other masses
        Mass[] massList = {nearestMass, mouseMass};
        Spring mouseSpring = new Spring("mousespring", 4, JGColor.green, massList, 
                                        mousePoint.distance(nearestMass.x, nearestMass.y), 1);
    }

    @Override
    public void paintFrame () {
        drawString("Gravity Toggle 'g': "+myGravityBooleanSpringies,20,30,-1);
        drawString("Viscosity Toggle 'v': "+myViscosityBooleanSpringies,20,50,-1);
        drawString("Center of Mass Toggle 'm': "+myCMassBooleanSpringies,20,70,-1);
        drawString("Wall 1 Toggle '1': "+ myWall1Force,20,90,-1);
        drawString("Wall 2 Toggle '2': "+ myWall2Force,20,110,-1);
        drawString("Wall 3 Toggle '3': "+ myWall3Force,20,130,-1);
        drawString("Wall 4 Toggle '4': "+ myWall4Force,20,150,-1);
        drawString("Increase wall size 'up'",20,170,-1);
        drawString("Decrease wall size 'down'",20,190,-1);
        drawString("Load New Assembly 'n'",20,210,-1);
    }
}
