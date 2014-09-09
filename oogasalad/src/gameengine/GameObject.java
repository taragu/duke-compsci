package gameengine;

import gameengine.events.GameEventManagerInterface;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameMap;
import gameengine.properties.Equipable;
import gameengine.properties.ItemLike;
import gameengine.properties.Pickupable;
import java.util.List;
import java.util.Map;
import util.Reflection;
import jgame.JGObject;


/**
 * The super class of any objects in this world, including items, tiles, characters
 * 
 * @author Tara
 * 
 */

public class GameObject extends JGObject implements DataConstants, ItemLike {

	protected String myType;
	protected Map<String, List<Object>> myPropertyInputMap;
	protected Map<String, String> myPropertyImplementationMap;
	protected GameEventManagerInterface myEventManager;

	protected List<Object> myProperties;

	protected String myMapUniqueID;
	
	protected String myUniqueID;
	protected String myGFXName;
	protected String myName;
	protected GameMap myMap;

	protected Object[] myArgs;
	
	protected static final String IMPLEMENTATION_PACKAGE_LOCATION = "gameengine.propertyimplementation.";

	/**
	 * Constructor for creating a generic Object
	 * 
	 * @param name
	 * @param isUnique
	 * @param x
	 * @param y
	 * @param collisionid
	 * @param gfxname
	 * @param type
	 * @param uniqueID
	 * @param propertyInputMap
	 * @param propertyImplementationMap
	 * @param mapUniqueID
	 */
	public GameObject (String name,
			boolean isUnique,
			double x,
			double y,
			int collisionid,
			String gfxname,
			String type,
			String uniqueID,
			Map<String, List<Object>> propertyInputMap,
			Map<String, String> propertyImplementationMap,
			String mapUniqueID) {
		super(name, true, x, y, collisionid, gfxname);
		//        this.eng.set
		myType = type;
		myPropertyInputMap = propertyInputMap;
		myGFXName = gfxname;
		myName = name;
		myPropertyImplementationMap = propertyImplementationMap;
		//        myProperties = setProperties();
		myMapUniqueID = mapUniqueID;
		myUniqueID = uniqueID;
		myArgs =  new Object[]{name, isUnique, x, y, collisionid, gfxname, type, uniqueID,
				propertyInputMap, propertyImplementationMap, mapUniqueID};
	}

	/**
	 * handles collision with other objects
	 */
	@Override
	public void hit (JGObject object) {
		

	}


	public InventoryItem pickUp() {
		// use reflection to instantiate Pickup
		List<Object> inputArgs = myPropertyInputMap.get(DataConstants.PICKUPABLE);
		System.out.println("IN PICKUP METHOD OF GAME OBJECT CLASS. THE SIZE OF INPUT ARGS IS" + inputArgs.size());

		Pickupable pick = (Pickupable) Reflection.createInstance(
		                              IMPLEMENTATION_PACKAGE_LOCATION+ myPropertyImplementationMap.
		                              get(DataConstants.PICKUPABLE), inputArgs);
		return pick.pickUp();
	}

	public String getType () {
		return myType;

	}

	/**
	 * Return all input arguments of a GameObject so that they can be saved as GameData
	 * @return
	 */
	public Object[] getArgs() {
		return  myArgs;

	}
	
	public String getGFXName() {
		return myGFXName;
	}
	
	public String getObjectName() {
		return myName; 
	}

	@Override
	public void move () {
		// implemented in sub classes

	}

	//    private List<Object> setProperties () {
	//        GameObjectManagerInterface manager = new GameObjectManager(null);
	////        return manager.enable(myType, myPropertyInputMap, myPropertyImplementationMap);
	//        
	//
	//    }

	public void setEventManager(GameEventManagerInterface gameEventManager) {
		myEventManager = gameEventManager;
	}

	/**
	 * Set the map that the GameObject is on;
	 * WARNING: FOR PLAYERCHARACTER, ITS MAP IS NULL BECAUSE IT CAN BE ON ANY MAP
	 * @param map
	 */
	public void setMap(GameMap map) {
		myMap = map;
	}

	
	

	@Override
	public void equipItem () {
	    List<Object> inputs = myPropertyInputMap.get(DataConstants.EQUIPABLE);
	    Equipable equip = (Equipable) Reflection.createInstance(IMPLEMENTATION_PACKAGE_LOCATION
	                                     +myPropertyImplementationMap.get(DataConstants.EQUIPABLE), inputs);

	    equip.equipItem();

	}


	public String getUniqueID() {
		return myUniqueID;
	}
        
        
}