package gameengine.events;

import java.util.LinkedList;
import java.util.Queue;

import gameengine.GameObject;
import gameengine.NonPlayerCharacter;
import gameplayer.PlayerEngine;

/**
 * Implementation of NPCGameEvent, switches state to battle state
 * @author Zanele
 * 
 */


public class NPCStartBattleEvent extends NPCGameEvent {



	public NPCStartBattleEvent(String eventType, GameObject thisNPC) {


		super(eventType, thisNPC);


	}

	public String toString() {
		return "I'm an NPC Battle Event!";
	}

	@Override

	public String execute(PlayerEngine eventEngine) {

		gameengine.party.PlayerStats npcPlayerStats = thisNPC.obtainPlayerStats();
		if (npcPlayerStats != null) {
			Queue<NonPlayerCharacter> enemyQueue = new LinkedList<NonPlayerCharacter>();

			enemyQueue.offer(thisNPC);
			eventEngine.setBattleState(enemyQueue);
			String returnString = "You have challenged" + thisNPC.toString() + "to a battle.";
			return returnString;
		}
		return null;
	}

}





