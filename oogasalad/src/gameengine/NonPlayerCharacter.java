package gameengine;

import gameengine.events.DialogueTree;
import gameengine.gamedata.DataConstants;
import gameengine.party.PartyObject;
import gameengine.properties.Talkable;
import java.util.List;
import java.util.Map;
import util.Reflection;
import jgame.JGObject;


/**
 * NonPlayerCharacter includes all moving people and monsters
 * 
 * @author Tara
 * 
 */

public class NonPlayerCharacter extends GameCharacter implements DataConstants {

    public NonPlayerCharacter (String name,
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

    }

    /**
     * handles collision with other objects
     */
    @Override
    public void hit (JGObject object) {
    
    	object.x = object.getLastX();
    	object.y = object.getLastY();
    	
        if (object instanceof PartyObject) {
//        	System.out.println("NPC HIT");
        	Object [] talkEventArgs = {"npcTalk", this};
        	myEventManager.add(talkEventArgs);	
        	Object [] battleEventArgs = {"npcStartBattle", this};
        	myEventManager.add(battleEventArgs);
        	Object [] questEventArgs = {"npcQuest", this};
        	myEventManager.add(questEventArgs);
        }

        
    }

    @Override
    public DialogueTree talk () {
      List<Object> inputs = myPropertyInputMap.get(DataConstants.TALKABLE);
      Talkable talk = (Talkable) Reflection.createInstance(IMPLEMENTATION_PACKAGE_LOCATION
                               +myPropertyImplementationMap.get(DataConstants.TALKABLE), inputs);

      DialogueTree myDialogue = talk.talk();
      return myDialogue;
    }

    

}
