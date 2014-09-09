package gameengine.gamedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.ErrorLogger;

/**
 * Class that store information of a map
 * @author Zanele, Tara
 *
 */

public class GameMap {

    private String myUniqueID;
    private String myName;

    /**
     * stores the string representation map: 
     * (consists of unique ids)
     * 
     */
    private List<String> myStringRepresentationOfMap;

    /**
     * The list of tiles which make up the whole map
     */
    private List<GameTile> myTiles;
    
    private Set<String> myTileIDs;
    
    private boolean mySubscriptionBoolean;
    
    private String myCode;


    /**
     * Create a map
     * @param ID
     * @param name
     * @param stringRepresentation
     * @param subscription boolean on or off
     * @param code
     */
    public GameMap (String ID, String name, String stringRepresentation, boolean subscription, String code) {
        myStringRepresentationOfMap = processStringRepresentation(stringRepresentation);
        myUniqueID = ID;
        myName = name;
        myTiles = new ArrayList<GameTile>();
        myTileIDs = new HashSet<String>();
        mySubscriptionBoolean = subscription;
        myCode = code;
    }

    

    /**
     * return the x and y pixel position of the tile on the map
     * @return
     */
    @SuppressWarnings("static-access")
    public int[] getTilePosition(GameTile tile) {
        int[] pos = new int[2];
        String id = tile.getUniqueID();
        // find the xindex and yindex of the tile on the map
        for (int i=0;i<myStringRepresentationOfMap.size();i++) {
            String[] words = myStringRepresentationOfMap.get(i).split(" ");
            for (int j=0;j<words.length;j++) {
                if (words[j].equals(id)) {
                    int xindex = j;
                    int yindex = i;
                    
                    //CONVERSION: 
                    pos[0] = xindex * tile.PIXEL_DIMENSION;
                    pos[1] = yindex * tile.PIXEL_DIMENSION; 
                }
                
            }
        }
        
        return pos;
        
    }

    
    /**
     * FOR JGAME TILE SETUP: return an array of the symbol representation of the map
     * @return
     */
    
    public String[] toSymbolRepresentation() {
        String[] symbolRep = new String[myStringRepresentationOfMap.size()];
        ErrorLogger.writeToLog("GameMap toSymbolRep: myTiles are "+myTiles, "info");
        for (int i=0;i<myStringRepresentationOfMap.size();i++) {
            String uniqueIDLine = myStringRepresentationOfMap.get(i);
            String symbolLine = "";
            ErrorLogger.writeToLog("GameMap toSymbolRep: i = "+i+" this line is: "+uniqueIDLine, "info");
            
            String[] uniqueIDs = uniqueIDLine.split(" ");
            if (uniqueIDLine!=null) {
            for (int j=0;j<uniqueIDs.length;j++) {
                String thisUniqueID = uniqueIDs[j];
                // find the tile with this uniqueid
                for (GameTile thisTileInTileList : myTiles) {
                    
                    if (thisTileInTileList.getUniqueID().equals(thisUniqueID)) {
                        ErrorLogger.writeToLog("GameMap toSymbolRep: line i = "+i+
                                               ",  thisTileInList has the same uniqueID " +
                                               ""+uniqueIDLine, "info");
                        // get the symbol of this tile
                        String symbol = thisTileInTileList.getSymbol();
                        ErrorLogger.writeToLog("GameMap toSymbolRep: symbol "+symbol+" added", "info");
                        symbolLine += symbol;
                    }
                }
                
                
            }
            }
            symbolRep[i] = symbolLine;
            ErrorLogger.writeToLog("GameMap toSymbolRep: symbol line added: "+symbolLine, "info");
        }
        
        return symbolRep;
        
    }

    /**
     * return the symbol of tiles that are NOT walkable
     * @return
     */
    public List<String> getNotWalkableSymbols () {
        List<String> symbols = new ArrayList<String>();
        for (GameTile thisTile : myTiles) {
            if (!thisTile.isWalkable()) {
                symbols.add(thisTile.getSymbol());
            }
        }
        
        return symbols;
    }

    /**
     * add a new tile to the list of tiles on the map
     * WARNING: CANNOT ADD A TILE THAT IS NOT IN THE STRING REPRESENTATION OF THE MAP
     * @param newTile
     */

    public void addTile(GameTile newTile) {
        if (!myTileIDs.contains(newTile.getUniqueID())) {
            myTiles.add(newTile);
            myTileIDs.add(newTile.getUniqueID());
            ErrorLogger.writeToLog("GameMap: addTile is called tile added to the map ", "info" );
        }
    }

    /**
     * get the string representation of the map consists of unique id of tiles
     * @return
     */
    public List<String> getStringRepresentation () {
        return myStringRepresentationOfMap;
    }
    
    

    /**
     * BEFORE A TILE IS ADDED TO THE LIST OF THE TILES THE MAP CONTAINS
     * check of a tile is in the map
     * @param thisTile
     * @return true if the tile is in the map (and add the tile to the list of tiles the map contains)
     */
    
    public boolean hasThisTile (GameTile thisTile) {
        for (String thisLine : myStringRepresentationOfMap) {
            String[] words = thisLine.split(" ");
            for (String thisWord : words) {
                if (thisWord.equals(thisTile.getUniqueID())) {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    /**
     * get the list of tiles the map has
     * @return
     */
    public List<GameTile> getTiles() {
        return myTiles;
    }
    
    /**
     * return the unique ID of the map
     * @return
     */
    public String getUniqueID() {
        return myUniqueID;
        
    }
    
    /**
     * turn subscription boolean off
     */
    public void turnOffSubscription() {
        mySubscriptionBoolean = false;
    }
    
    public boolean getSubscriptionBoolean() {
        return mySubscriptionBoolean;
    }
    
    public String getCode() {
        return  myCode;
    }
    
    public String getName() {
    	return myName;
    }
    
    
    /*
     * process the string rep and break it up into an list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<String> processStringRepresentation (String stringRepresentation) {
        
        
        String[] lines = stringRepresentation.split(" \n");
        List<String> toReturn = new ArrayList(Arrays.asList(lines));
        ErrorLogger.writeToLog("processStringRep: returns "+toReturn, "info");
        return toReturn;
    }
    
    public int getWidth() {
    	String[] tiles = myStringRepresentationOfMap.get(0).split("\\s+");
		return tiles.length;
    }
    
    public int getHeight(){
    	return myStringRepresentationOfMap.size();
    }

}
