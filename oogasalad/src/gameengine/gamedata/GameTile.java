package gameengine.gamedata;

import jgame.platform.JGEngine;

/**
 * Class for tile objects which makes up a map
 * @author Tara
 *
 */

public class GameTile implements DataConstants {
    
    private String myUniqueID;
    
    @SuppressWarnings("unused")
    private String myName;
    
    @SuppressWarnings("unused")
    private String myImagePath;
    
    @SuppressWarnings("unused")
    private int myCollisionID;
    
    /**
     * The map the tile is on
     */
    private GameMap myMap;
    
    /**
     * is this tile walkable?
     */
    private boolean myIsWalkableBoolean;
    
    
    /**
     * the symbol representation of the tile
     */
    private String mySymbol;
    
    /**
     * how many pixels does a tile take up? (same in x and y direction)
     */
    public static final int PIXEL_DIMENSION = 20;
    
    
    /**
     * create a tile object
     * @param uniqueID
     * @param imagePath
     * @param isWalkable
     */
    public GameTile(String uniqueID, String name, String imagePath, boolean isWalkable) {
        myUniqueID = uniqueID;
        myName = name;
        myImagePath = imagePath;
        myIsWalkableBoolean = isWalkable;
        assignCollisionID();
//        myMap = map;
        
    }
    

    /**
     * set the map the tile is on
     * @param map
     */
    public void setMap(GameMap map) {
        myMap = map;
    }
    
    
    /**
     * add a tile to JGEngine
     */
    
    public void addToGame(JGEngine engine) {
        
//        engine.setTile(x, y, myUniqueID);

        int[] pos = myMap.getTilePosition(this);
        engine.setTile(pos[0], pos[1], myUniqueID);
        
    }
    
    
    /**
     * return the map the tile is on
     * @return
     */
    public GameMap getMap() {
        return myMap;
        
    }
    
    /**
     * return true if the tile is walkable; return false if it's not
     * @return
     */
    public boolean isWalkable() {
        return myIsWalkableBoolean;
        
    }
    
    /**
     * return the unique id of a tile
     * @return
     */
    public String getUniqueID() {
        return myUniqueID;
        
    }
    
    /**
     * Return the symbol rep of the tile
     * @return
     */
    public String getSymbol() {
        return mySymbol;
    }

    /**
     * set the symbol representation of the tile
     * @param symbolOfTile
     */
    public void setSymbol (String symbolOfTile) {
        mySymbol = symbolOfTile;
    }
    
    /* assign cid based on isWalkableBoolean */
    private void assignCollisionID () {
        if (myIsWalkableBoolean) {
            myCollisionID = WALKABLE_CID;
        } else {
            myCollisionID = NOTWALKABLE_CID;
        }
        
    }
    
}
