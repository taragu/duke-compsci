package mastercode;

import gameengine.GameObject;
import gameengine.PlayerCharacter;
import gameengine.events.GameEventManagerInterface;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameDataLibrary;
import gameplayer.PlayerEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Class that keeps track of the GameObjects that are load from the file
 * 
 * @author Tara
 * 
 */

public class GameObjectManager implements DataConstants, GameObjectManagerInterface {

	private List<GameObject> myActiveObjects;

	private GameDataManager myGameDataManager;

	/**
	 * Create a GameObjectManager object
	 * @param gameDataManager
	 */
	public GameObjectManager (GameDataManager gameDataManager) {
		myActiveObjects = new ArrayList<GameObject>();
		myGameDataManager = gameDataManager;

	}

	/**
	 * return an unmodifiable list of all game objects
	 * 
	 * @return
	 */
	public List<GameObject> getActiveGameObjects () {
		return Collections.unmodifiableList(myActiveObjects);

	}

	/**
	 * Return the list of CharacterLike objects (abilities) to enable the character to
	 * perform certain tasks in the game
	 * 
	 * @param propertyImplementationMap
	 * @param propertyInputMap
	 * @param type
	 * @return
	 */

	public List<Object> enable (String type, Map<String, List<Object>> propertyInputMap,
			Map<String, String> propertyImplementationMap) {

		List<Object> propertyObjects = new ArrayList<Object>();
		for (String thisProperty : propertyInputMap.keySet()) {
			Object property =
					GameDataLibrary.createPropertyObject(thisProperty,
							propertyImplementationMap
							.get(thisProperty),
							propertyInputMap.get(thisProperty));
			propertyObjects.add(property);
		}

		return propertyObjects;
	}

	/**
	 * Set the display (JGEngine) class of player character
	 * 
	 * @param playerEngine
	 */
	public void setPlayerCharacterDisplay (PlayerEngine playerEngine) {
		if (myActiveObjects!=null) {
			for (GameObject thisObject : myActiveObjects) {
				if (thisObject instanceof PlayerCharacter) {
					((PlayerCharacter) thisObject).setDisplay(playerEngine);
					// ErrorLogger.writeToLog("GameObjectManager setPlayerCharacterDisplay called ",
					// "info");
				}
			}
		}

	}

	/**
	 * Set the event manager 
	 * @param myEventManager
	 */
	public void setEventManager(GameEventManagerInterface myEventManager) {
		for (GameObject thisObject : myActiveObjects) {
			thisObject.setEventManager(myEventManager);
		}

	}

	/**
	 * add a GameObject to the list of active object on the curremt map
	 * WARNING: HAVE TO USE THIS METHOD WHENEVER WE CREATE NEW GAMEOBJECTS
	 *  WHEN SWITCHING TO A DIFFERENT MAP
	 * @param gameObject
	 */
	public void addActiveObject (GameObject gameObject) {
		myActiveObjects.add(gameObject);

	}

	/**
	 * when going to a new state, destroy active and save them as GameData
	 */
	public void destroyActiveObjects(){
		for (GameObject thisObject: myActiveObjects) {
			myGameDataManager.saveObjectData(thisObject);
			thisObject.remove();
		}

		myActiveObjects.clear();
	}


}
