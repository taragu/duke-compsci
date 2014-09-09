package mastercode;

import gameengine.gamedata.DataConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * A helper class of Translator: write necessary information to the 
 * media table as well as assign collision ids
 * 
 * @author Tara
 *
 */
public class MediaProcessor implements DataConstants{

    private String myType;
    
    private JSONObject mySubObject;
    
    /**
     * path to save the media table
     */
    private final String PATH_TO_SAVE = "src"+File.separator+"gameplayer"+File.separator+"media"+File.separator+"media.tbl";
    

    /**
     * FOR TILES: MAPS A UNIQUE ID TO A SYMBOL
     */
    private Map<String, String> myUniqueIDSymbolMap = new HashMap<String, String>();

    private List<String> mySymbolLibrary = new ArrayList<String>();
    
    /**
     * store unique id's of the tiles created and pass that to MapsAndTilesManager
     */
    private Set<String> myTilesCreated = new HashSet<String>();
    
    /**
     * Create a MediaProcessor object
     * @param type Tiles or Objects
     * @param subObject
     */
    public MediaProcessor () {
        initSymbolLibrary();
    }
    
    protected String processMedia(String type, JSONObject subObject) {
        myType = type;
        mySubObject = subObject;
        String path = (String) mySubObject.get(IMAGEPATH);
        String mediaName = ((String) mySubObject.get(NAME)) + "media";
        int cid = assignedCID(mySubObject);
        MediaTableWriter writer = null;
        if (myType.equals("Objects")) {
            writer = new MediaTableWriter(mediaName,  null, cid, path, PATH_TO_SAVE);
        } else if (myType.equals("Tiles")) {
            if (!myTilesCreated.contains(mySubObject.get(UNIQUEID))) {
                mediaName = (String) mySubObject.get(UNIQUEID);
                //ASSIGN CID
                int cidOfTile = assignedCID(mySubObject);
                //ASSIGN SYMBOL 
                String symbolOfTile = assignedSymbol(mediaName);
                writer = new MediaTableWriter(mediaName, symbolOfTile,
                                                   cidOfTile, path, PATH_TO_SAVE);
            }
        } else if (myType.equals("General")) {
            mediaName = BATTLE_GFX;
            writer = new MediaTableWriter(mediaName,  null, BATTLE_BACKGROUND_CID, 
                                          (String) mySubObject.get("Fight Background"), PATH_TO_SAVE);
        }
        writer.write();
        return mediaName;
    }
    
    /**
     * add the tile to the list of tiles have already been created
     * @param tileArgs
     */
    protected void addCreatedTile(Object[] tileArgs) {
        myTilesCreated.add((String) tileArgs[0]);
    }
    
    /*
     * Assign GameObjects with collision id: 
     * 1) if it's a PlayerCharacter: cid = 10
     * 2) if it's a NonPlayerCharacter: cid = 11;
     * 3) if it's another type of game object: cid = 12;
     */
    protected int assignedCID (JSONObject subObject) {
        if (subObject.get(TYPE) != null) {
            if (subObject.get(TYPE).equals(PLAYERCHARACTER)) {
                return PARTYOBJECT_CID;
                
            } else if (subObject.get(TYPE).equals(NONPLAYERCHARACTER)) {
                return NONPLAYERCHARACTER_CID;
                
            } else if (subObject.get(TYPE).equals(TRANSITIONOBJECT)) {
                return TRANSITION_CID;
                
            } else {
                return OTHEROBJECTS_CID;
                
            }
        } else if (subObject.get(WALKABLE)!=null){ // is a tile!
            Object value = subObject.get(WALKABLE);
            boolean walkable = Boolean.valueOf(value.toString());
            if (walkable) {
                return WALKABLE_CID;
            } else {
                return NOTWALKABLE_CID;
            }
        } else {
            return 0;
        }
    }
    
    
    
    /*
     * Assign symbol to map tiles
     */
    private String assignedSymbol (String name) {
        if (myUniqueIDSymbolMap.containsKey(name)) {
            return myUniqueIDSymbolMap.get(name);
        } else {
            String newSymbol = mySymbolLibrary.get(0);
            myUniqueIDSymbolMap.put(name, newSymbol);
            mySymbolLibrary.remove(newSymbol);
            return newSymbol;
        }
        
    }
    
    /**
     * get the map of uniqueid of tiles to their assigned symbols
     * 
     * @return
     */
    protected Map<String, String> getUniqueIDSymbolMap() {
        return myUniqueIDSymbolMap;
    }

    
    /*
     * add symbols to symbol library
     */
    private void initSymbolLibrary () {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        list.add("k");
        list.add("l");
        list.add("m");
        list.add("n");
        list.add("o");
        list.add("p");
        list.add("q");
        list.add("r");
        list.add("s");
        list.add("t");
        list.add("u");
        list.add("v");
        list.add("w");
        list.add("x");
        list.add("y");
        list.add("z");
        list.add("!");
        list.add("#");
        list.add("$");
        list.add("@");
        list.add("%");
        list.add("^");
        list.add("&");
        list.add("*");
        
        mySymbolLibrary.addAll(list);
    }
}
