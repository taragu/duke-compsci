package gameengine;

import java.util.List;
import java.util.Map;
import util.Reflection;
import jgame.JGObject;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.QuestData;
import gameengine.party.PlayerStats;
import gameengine.properties.CharacterLike;
import gameengine.properties.Fightable;
import gameengine.properties.Questable;
import gameplayer.ActionPanel;


/**
 * Abstract class with common behaviors of PlayerCharacter and NPC in the game
 * 
 * @author Tara
 * 
 */

public abstract class GameCharacter extends GameObject implements CharacterLike {

    protected static final int SPEED_CONSTANT = 2;

    private List<GameObject> myInventory;
    
    protected ActionPanel myActionPanel;

    /**
     * Use this constructor if we want to set the Character's abilities: for example: talk, attack,
     * etc
     * 
     * @param name
     * @param isUnique
     * @param x
     * @param y
     * @param collisionid
     * @param gfxname
     * @param abilityInputMap
     * @param abilityImplementationMap
     * @param mapUniqueID
     */
    public GameCharacter (String name,
                          boolean isUnique,
                          double x,
                          double y,
                          int collisionid,
                          String gfxname,
                          String type,
                          String uniqueID,
                          Map<String, List<Object>> abilityInputMap,
                          Map<String, String> abilityImplementationMap,
                          String mapUniqueID) {
        super(name, true, x, y, collisionid, gfxname, type, uniqueID, abilityInputMap,
              abilityImplementationMap, mapUniqueID);
        assert (type.equals("CharacterLike")) : "The object you're trying to create is not a character! "
                                                +
                                                "You should change the type field to 'GameCharacter' if you want to create a character";
        
        
    }

    /**
     * handles collision with other objects
     */
    @Override
    public void hit (JGObject object) {
        

    }

    @Override
    public void move () {
        // implemented in sub classes
    }
    
    /**
     * Set the action panel for PlayerCharacter and NonPlayerCharacter
     * @param actionPanel
     */
    public void setActionPanel(ActionPanel actionPanel) {
        myActionPanel = actionPanel;
        
    }
    
    public List<GameObject> getInventory(){
    	return myInventory;
    }

    @Override
    public QuestData offerQuest() {
        List<Object> inputs = myPropertyInputMap.get(DataConstants.QUESTABLE);
        Questable quest = (Questable) Reflection.createInstance(IMPLEMENTATION_PACKAGE_LOCATION
                                 +myPropertyImplementationMap.get(DataConstants.QUESTABLE), inputs);
        QuestData questData = quest.offerQuest();
        return questData;
    }
    
    @Override
    public PlayerStats obtainPlayerStats () {
        List<Object> inputs = myPropertyInputMap.get(DataConstants.FIGHTABLE);
        Fightable fight = (Fightable) Reflection.createInstance(IMPLEMENTATION_PACKAGE_LOCATION
                                 +myPropertyImplementationMap.get(DataConstants.FIGHTABLE), inputs);

        return fight.obtainPlayerStats();
    }
    
}
