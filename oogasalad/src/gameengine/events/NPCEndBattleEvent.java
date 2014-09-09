package gameengine.events;

import gameengine.GameObject;
import gameengine.gamedata.DataConstants;
import gameplayer.PlayerEngine;


/**
 * NPC End Battle Event allows us to change the game state to the previous state
 * If the battle has been won check with the party object to see if this NPC is tied to a quest. If so, remove quest
 * Then switch to the original map before the map CHECKQUESTCOMPLETION--> DataConstants.BATTLE_EVENT along with the ID
 * of the NPC
 * @author Zanele Munyikwa
 *
 */
public class NPCEndBattleEvent extends NPCGameEvent {

	public NPCEndBattleEvent(String eventType, GameObject thisNPC) {
		super(eventType, thisNPC);
		
	}
	
	

	@Override
	public String execute(PlayerEngine engine) {
		
		engine.returnToPreviousState();
		
		boolean questAccomplished = engine.checkQuestCompletionandClear(DataConstants.BATTLE_QUEST, thisNPC.getUniqueID());
		
		if (questAccomplished) {
			return "You just accomplished a quest! View your remaining quests in the quest tab!";
		}
		return null;
	}

	
}
