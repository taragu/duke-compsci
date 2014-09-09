package mastercode;

import gameengine.GameObject;
import gameengine.events.GameEventManagerInterface;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameData;
import gameengine.gamedata.GameMap;
import gameengine.gamedata.GameObjectFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the data of the constructor inputs for GameObjects
 * @author Tara
 *
 */
public class GameDataManager implements DataConstants {

    /**
     * list of input information of GameObjects
     */
    private List<GameData> myData;
    
    private GameObjectManager myGameObjectManager;
    
    private GameEventManagerInterface myEventManager;
    
    /**
     * create a GameDataManager
     * @param listOfArgs
     * @param myEventManager2 
     */
    public GameDataManager (List<GameData> listOfArgs, GameEventManagerInterface myEventManager2) {
        myData = listOfArgs;
        myGameObjectManager = new GameObjectManager(this);
        myEventManager = myEventManager2;
        
        
    }

    /**
     * call this method when transitioning to a new map; create all GameObjects in the new map
     * @param map
     */
    public void createObjectsInMap (GameMap map) {
        // look for the GameObjects that are on this map
        List<GameData> objects = lookForObjectsInMap(map.getUniqueID());
        // use reflection, and then set the event manager
        for (GameData thisData: objects) {
            if (!thisData.getType().equals(PLAYERCHARACTER)) {
                Object[] thisEntry = thisData.getData();
                GameObjectFactory factory = new GameObjectFactory(thisEntry);
                GameObject gameObject = factory.createGameObject();
                System.out.println("GameDataManager: gameObject "+ gameObject.getName()+" is created");
                myGameObjectManager.addActiveObject(gameObject);
                myGameObjectManager.setEventManager(myEventManager);
            }
        }
        myData.removeAll(objects);
        
    }
    
    /**
     * Save the data of a destroyed GameObject
     * @param thisObject
     */
    public void saveObjectData (GameObject thisObject) {
        Object[] args = thisObject.getArgs();
        GameData data = new GameData(args);
        myData.add(data);
    }
    
    /**
     * return the GameObjectManager
     * @return
     */
    public GameObjectManager getGameObjectManager() {
        return myGameObjectManager;
        
    }
    
    /**
     * return unique id of the default map
     * 
     * @return
     */
    public String getFirstMapID() {
        String mapID = "";
        for (GameData thisData: myData) {
            if (thisData.getType().equals(PLAYERCHARACTER)) {
                mapID = thisData.getMapUniqueID();
            }
        }
        assert (!mapID.equals("")):"mapID is not obtained from game data!";
        return mapID;
        
    }

    /* using unique id to look for the objects that are on the map */
    private List<GameData> lookForObjectsInMap (String uniqueID) {
        List<GameData> objects = new ArrayList<GameData>();
        for (GameData thisData : myData) {
            if (!thisData.getType().equals(PLAYERCHARACTER)){
                String thisMap = thisData.getMapUniqueID();
                if (thisMap.equals(uniqueID)){
                    objects.add(thisData);
                }
            }
        }
        return objects;
    }

  
    

}
