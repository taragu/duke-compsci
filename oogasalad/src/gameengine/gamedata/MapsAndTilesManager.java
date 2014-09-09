package gameengine.gamedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that keeps track of the maps including the creation 
 * and creating actual JGame tiles in the game
 * @author Tara
 *
 */

public class MapsAndTilesManager {
    
    /**
     * Maps that stores unique ids as keys and the actual maps as values
     */
    private Map<String, GameMap> myUniqueIDMap;
    
    private MapsAndTilesFactory myFactory;
    
    private Map<GameMap, List<GameTile>> myMapTileMap; 
    
    public MapsAndTilesManager(String jsonFilePath){
        if (jsonFilePath!=null) {
            GameDataLibrary.init();
            myFactory = new MapsAndTilesFactory(jsonFilePath);
            myUniqueIDMap = new HashMap<String, GameMap>();
            myMapTileMap = new HashMap<GameMap,List<GameTile>>();
        }
        
    }
    
    
    public void createMaps(){
        Set<GameMap> maps = myFactory.createMaps();
        // store as a map
        for (GameMap thisMap : maps) {
            String uniqueID = thisMap.getUniqueID();
            myUniqueIDMap.put(uniqueID, thisMap);
            // update myMapTileMap
            myMapTileMap.put(thisMap, thisMap.getTiles());
            
        }
        
        
        
        
    }
    
    
    
    
    /**
     * return a specific map in the game
     * @return
     */
    public GameMap getMapByUniqueID(String uniqueID) {
        return myUniqueIDMap.get(uniqueID);
        
    }
    
    /**
     * return the number of tiles in X and Y directions of a map
     * @param uniqueID
     * @return X and Y tile dimensions of map
     */
    public int[] getMapDimensions(String uniqueID) {
    	GameMap map = myUniqueIDMap.get(uniqueID);
    	int[] dimensions = {map.getWidth(),map.getHeight()};
    	return dimensions;
    }
    
}
