package gameengine.gamedata;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mastercode.Translator;
import util.Reflection;

/**
 * Class for the ugly things of creating maps and tiles
 * @author Tara
 *
 */
public class MapsAndTilesFactory implements DataConstants {

    private String myFilePath;
    
    private Translator myTranslator;
    
    
    
    /**
     * Package of GameTile and GameMap;
     * WARNING: IF THE FILES ARE MOVED, THIS LOCATION HAS TO CHANGE TOO
     */
    private final String PACKAGE = "gameengine.gamedata.";
    
    /**
     * WARNING: HAVE TO CALL THIS METHOD BEFORE CREATING THE MAP!!
     * Initial setup of the class
     * @param filePath
     */
    public MapsAndTilesFactory (String filePath) {
        myFilePath = filePath;
        myTranslator = new Translator();
    }

    /**
     * map creation
     * @return
     */
    protected Set<GameMap> createMaps () { 
        Set<GameMap> mapsCreated = new HashSet<GameMap>();
        Set<GameTile> tilesCreated = new HashSet<GameTile>();
        List<Object[]> mapsData = myTranslator.translateSpecifiedItem("Maps", myFilePath, PROPERTIES_FILEPATH);
        List<Object[]> tilesData = myTranslator.translateSpecifiedItem("Tiles", myFilePath, PROPERTIES_FILEPATH);
        Map<String, String> uniqueIDSymbolMap = myTranslator.getUniqueIDSymbolMap();
        for (Object[] thisTileData : tilesData) {
            GameTile gameTile = (GameTile) Reflection.createInstance(PACKAGE+"GameTile", thisTileData);
            gameTile.setSymbol(uniqueIDSymbolMap.get(gameTile.getUniqueID()));
            tilesCreated.add(gameTile);
        }
        for (Object[] thisMapData : mapsData) {
            mapsCreated.add((GameMap) Reflection.createInstance(PACKAGE+"GameMap", thisMapData));
        }
        
        setMapForTiles(tilesCreated, mapsCreated);
        
        return mapsCreated;
    }

    /* 
     * for each tile, use setMap method in the GameTile class to set their maps
     * 
     * */
    private void setMapForTiles (Set<GameTile> tilesCreated, Set<GameMap> mapsCreated) {
        // for each tile
        for (GameTile thisTile : tilesCreated) {
            for (GameMap thisMap : mapsCreated) {
                if (thisMap.hasThisTile(thisTile)) {
                    thisTile.setMap(thisMap);
                    thisMap.addTile(thisTile);
                }
            }
        }
    }
}
