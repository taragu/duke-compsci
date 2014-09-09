package gameengine.gamedata;

import gameengine.InventoryItem;
import gameengine.party.PlayerStats;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends GameData to store extra information of a single PlayerCharacter such as 
 *  stats
 *  
 * @author Tara
 *
 */
public class PlayerCharacterData extends GameData {

    private GameData myPlayerGameData;
    
    private PlayerStats myPlayerStats;
    
    private List<InventoryItem> myPlayerItems;
    
    private PlayerCharacterData (Object[] args) {
        super(args);
        
    }
    
    /**
     * Use this constructor to create a PlayerCharacterData
     * 
     * @param playerGameData
     */
    public PlayerCharacterData(GameData playerGameData) {
         
        this(playerGameData.getData());
        myPlayerGameData = playerGameData;
//        myPlayerStats = stats;
        myPlayerItems = new ArrayList<InventoryItem>();
        double x=playerGameData.getXPos();
        double y=playerGameData.getYPos();
        System.out.println("&&&&&&&&PlayerCharacterData: x="+x+", y="+y);
        
    }
    
    /**
     * Return the inventory items the player holds
     * @return
     */
    public List<InventoryItem> exposePlayerItems() {
        return myPlayerItems;
    }
    
    public String getMapUniqueID() {
        return myPlayerGameData.getMapUniqueID();
    }
    
    /**
     * Return the stats of the player
     * @return
     */
    public PlayerStats getPlayerStats() {
        return myPlayerStats;
    }
    
    /**
     * set the stats of the player 
     * WARNING: CALL THIS METHOD RIGHT AFTER PLAYERCHARACTERDATA IS CREATED
     * 
     * @param stats stores information of the player stats
     */
    public void setPlayerStats(List<Object> backgroundAndStats) {
       
        
        int strength = new Integer(backgroundAndStats.get(0).toString());
        int defence = new Integer(backgroundAndStats.get(1).toString());
        int agility = new Integer(backgroundAndStats.get(2).toString());
        int health = new Integer(backgroundAndStats.get(3).toString());
        int experiencelvl = new Integer(backgroundAndStats.get(4).toString());
        int experiencefactor = new Integer(backgroundAndStats.get(5).toString());
        int strengthIncr = new Integer(backgroundAndStats.get(6).toString());
        int defenceIncr = new Integer(backgroundAndStats.get(7).toString());
        int agilityIncr = new Integer(backgroundAndStats.get(8).toString());
        int healthIncr = new Integer(backgroundAndStats.get(9).toString());
        
        myPlayerStats = new PlayerStats(strength, defence, agility, health, experiencelvl, 
                                        experiencefactor, strengthIncr, defenceIncr, agilityIncr,
                                        healthIncr);
        
        
    }
    
    
   
    /**
     * Obtain the graphics name of the PlayerCharacter in media table
     * 
     * @return gfx name
     */
    public String getGFXName() {
        return myPlayerGameData.getGFXName();
        
    }
    

}
