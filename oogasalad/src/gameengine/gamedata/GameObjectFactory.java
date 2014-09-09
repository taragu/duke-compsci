package gameengine.gamedata;

import util.Reflection;
import gameengine.GameObject;

/**
 * class to create GameObjects
 * @author Tara
 *
 */
public class GameObjectFactory {
    
    private Object[] myArgs;
    
    /**
     * Package of all the property classes;
     * WARNING: IF THE FILES OF TALK, WALK ARE MOVED, THIS LOCATION HAS TO CHANGE TOO
     */
    private final String PACKAGE = "gameengine.";
    
    /**
     * the index of the entry that indicate the specific type of GameObject in the 
     *  GameObject constructor
     */
    private final int INDEX_OF_TYPE = 6;
    
    
    /**
     * Create a GameObjectFactory object by inputing the arguments for one GameObject
     * @param args
     */
    public GameObjectFactory(Object[] args) {
        myArgs = args;
        
    }
    
    /**
     * create a GameObject object from args
     * @return
     */
    public GameObject createGameObject() {
        GameObject gameObject =
                (GameObject) Reflection.createInstance(PACKAGE + myArgs[INDEX_OF_TYPE], myArgs);
        
        return gameObject;
        
    }
    
}
