package gameengine.gamedata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ErrorLogger;
import util.Reflection;


/**
 * Class that holds all the abilities and their implementations
 * 
 * @author Tara
 * 
 */

public class GameDataLibrary implements DataConstants {

    /**
     * Map that holds the specific game object name and their type name
     */
    private static Map<String, String> myGameObjectMap; // example: "Character", "CharacterLike";
                                                        // "NonPlayerCharacter","CharacterLike"

    /**
     * Map that maps a property to its specific implementation
     */
    private static Map<String, String> myPropertyImplementationMap; // example: "Talk", "Talkable"

    /**
     * Map that holds the properties with their respective input types
     */
    private static Map<String, String> myPropertyInputMap; // example: "Talkable", LIST_OF_STRING

    /**
     * Fields in the properties file
     */
    private static List<String> myFieldsForGameObjects;
    
    /**
     * holds all the abilities a GameObject can have
     */
    private static List<String> myAbilitiesForGameObjects;

    /**
     * Fields to look for in the json file for maps
     */
    private static List<String> myFieldsForMaps;
    
    /**
     * Fields to look for in the json file for tiles
     */
    private static List<String> myFieldsForTiles;
    
    
    /**
     * Package of all the property classes;
     * WARNING: IF THE FILES OF TALK, WALK ARE MOVED, THIS LOCATION HAS TO CHANGE TOO
     */
    private static final String PACKAGE = "gameengine.propertyimplementation.";

    static {
        myGameObjectMap = new HashMap<String, String>();
        myPropertyImplementationMap = new HashMap<String, String>();
        myPropertyInputMap = new HashMap<String, String>();
        myFieldsForGameObjects = new ArrayList<String>();
        myFieldsForMaps = new ArrayList<String>();
        myFieldsForTiles = new ArrayList<String>();
        myAbilitiesForGameObjects = new ArrayList<String>();
    }

    /**
     * Initialize property library and execute necessary stuff to setup
     */
    public static void init () {
        buildPropertyLibrary();

    }

    /**
     * Create and return property object
     * 
     * @param propertyName
     * @param implementationName
     * @param inputs
     * @return
     */
    public static Object createPropertyObject (String propertyName,
                                               String implementationName, List<Object> inputs) {
        // perform type check; log
        if (!myPropertyImplementationMap.get(implementationName).equals(propertyName)) {
            ErrorLogger.writeToLog("Hey guys! Property does not match the implementation!! ",
                                   "severe");
        }

        String inputType = inputs.get(0).getClass().getName();
        String correctInputType = myPropertyInputMap.get(propertyName).getClass().getName();
        ErrorLogger.writeToLog("input type is " + inputType, "info");
        ErrorLogger.writeToLog("correct input type is " + correctInputType, "info");
        if (!correctInputType.equals(inputType)) {
            ErrorLogger.writeToLog("Hey guys! Input type does not match the implementation!! ",
                                   "severe");
        }

        return Reflection.createInstance(PACKAGE + implementationName, inputs);

    }

    /**
     * Return an unmodifiable list of the fields in the properties file
     * 
     * @return
     */
    public static List<String> getFieldsForGameObjects () {
        return Collections.unmodifiableList(myFieldsForGameObjects);
    }

    /**
     * Return an unmodifiable list of the fields for maps
     * @return
     */
    public static List<String> getFieldsForMaps() {
        return Collections.unmodifiableList(myFieldsForMaps);
        
    }
    
    /**
     * Return an unmodifiable list of the fields for tiles
     * @return
     */
    public static List<String> getFieldsForTiles() {
        return Collections.unmodifiableList(myFieldsForTiles);
        
    }
    
    /**
     * Return an unmodifiable list of all the abilities a GameObject can have
     * @return
     */
    public static List<String> getAbilitiesForGameObjects() {
        return Collections.unmodifiableList(myAbilitiesForGameObjects);
    }
    
    
    
    /**
     * Get the interface from implementation
     * 
     * @param implementation
     * @return String
     */
    protected static String getProperty (String implementation) {
        return myPropertyImplementationMap.get(implementation);
    }

    /*
     * build the property library (ugly stuff);
     * have to include ALL interfaces and implementations;
     * add more interfaces when we have more
     */

    private static void buildPropertyLibrary () {
        myFieldsForGameObjects = new ArrayList<String>();
        myFieldsForMaps = new ArrayList<String>();
        myFieldsForTiles = new ArrayList<String>();


        myGameObjectMap.put(CHARACTER, CHARACTERLIKE);
        myGameObjectMap.put(PLAYERCHARACTER, CHARACTERLIKE);
        myGameObjectMap.put(NONPLAYERCHARACTER, CHARACTERLIKE);
        myGameObjectMap.put(SINGLEUSECOMBAT, ITEMLIKE);
        myGameObjectMap.put(STATSBOOSTER, ITEMLIKE);
        myGameObjectMap.put(TRANSITIONOBJECT, DOORLIKE);

        myPropertyInputMap.put(TALKABLE, LIST_OF_STRING);
        myPropertyInputMap.put(FIGHTABLE, LIST_OF_STRING); 
        myPropertyInputMap.put(QUESTABLE, LIST_OF_STRING);
        myPropertyInputMap.put(PICKUPABLE, LIST_OF_STRING);
        myPropertyInputMap.put(EQUIPABLE, LIST_OF_STRING);
        myPropertyInputMap.put(TRANSITIONABLE, LIST_OF_STRING);

        myPropertyImplementationMap.put(TALK, TALKABLE);
        myPropertyImplementationMap.put(CANNOTTALK, TALKABLE);
        myPropertyImplementationMap.put(FIGHT, FIGHTABLE);
        myPropertyImplementationMap.put(CANNOTFIGHT, FIGHTABLE);
        myPropertyImplementationMap.put(QUEST, QUESTABLE);
        myPropertyImplementationMap.put(CANNOTQUEST, QUESTABLE);
        myPropertyImplementationMap.put(PICKUP, PICKUPABLE);
        myPropertyImplementationMap.put(CANNOTPICKUP, PICKUPABLE);
        myPropertyImplementationMap.put(EQUIP, EQUIPABLE);
        myPropertyImplementationMap.put(CANNOTEQUIP, EQUIPABLE);
        myPropertyImplementationMap.put(TRANSITION, TRANSITIONABLE);
        myPropertyImplementationMap.put(CANNOTTRANSITION, TRANSITIONABLE);

        // WARNING: THE ORDER NEEDS TO BE EXACTLY THE SAME AS THE GAMEOBJECT CONSTRUCTOR
        myFieldsForGameObjects.add(NAME);
        myFieldsForGameObjects.add(ISUNIQUE);
        myFieldsForGameObjects.add(X);
        myFieldsForGameObjects.add(Y);
        myFieldsForGameObjects.add(COLLISIONID);
        myFieldsForGameObjects.add(IMAGEPATH);
        myFieldsForGameObjects.add(TYPE);
        myFieldsForGameObjects.add(UNIQUEID);
        myFieldsForGameObjects.add(ABILITIES);
        myFieldsForGameObjects.add(IMPLEMENTATION);
        myFieldsForGameObjects.add(MAP);
        
        //WARNING: ADD NEW ABILITY INTERFACES WHEN WE HAVE MORE
        myAbilitiesForGameObjects.add(TALKABLE);
        myAbilitiesForGameObjects.add(FIGHTABLE);
        myAbilitiesForGameObjects.add(QUESTABLE);
        myAbilitiesForGameObjects.add(EQUIPABLE);
        myAbilitiesForGameObjects.add(PICKUPABLE);
        myAbilitiesForGameObjects.add(TRANSITIONABLE);

        /* for maps */
        myFieldsForMaps.add(UNIQUEID);
        myFieldsForMaps.add(NAME);
        myFieldsForMaps.add(MAP);
        
        myFieldsForTiles.add(UNIQUEID);
        myFieldsForTiles.add(NAME);
        myFieldsForTiles.add(IMAGEPATH);
        myFieldsForTiles.add(WALKABLE);
        
        
        
        ErrorLogger.writeToLog("PropertyLibrary buildPropertyLibrary: success", "info");
        // ADD MORE AS WE HAVE MORE INTERFACES
        
        
        
        
        
    }

}
