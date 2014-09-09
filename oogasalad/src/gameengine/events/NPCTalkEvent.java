package gameengine.events;

import java.util.ArrayList;
import java.util.List;

import gameengine.GameObject;
import gameengine.gamedata.DataConstants;
import gameplayer.PlayerEngine;

/**
 * NPC event that occurs when 
 * @author Zanele Munyikwa
 *
 */

public class NPCTalkEvent extends NPCGameEvent {

	public NPCTalkEvent(String eventType, GameObject thisNPC) {
		super(eventType, thisNPC);
		
	}

	public String toString() {
		return "I'm an NPC Talk Event!";
	}



	@Override
	public String execute(PlayerEngine thisEngine) {
		System.out.println(thisNPC.toString());
		DialogueTree npcDialogue = thisNPC.talk();
		DialogueNode currentNode = null;

		if (npcDialogue != null) {
			currentNode = npcDialogue.getRoot();
			
			//Display and process responses until you have reached a leaf by using getMessage(), getResponses(), and processResponse()
			while (currentNode !=null) {

				String displayNodeString = currentNode.getMessage();

				// send displayNodeString to front end, also list responses 
				thisEngine.getController().updateActionPanelDialogue(thisNPC.getObjectName() + " says: " +displayNodeString);
				System.out.println("Current Node Responses: " + currentNode.getResponses());
				List<String> possibleResponses = currentNode.getResponses();
				possibleResponses.add("I don't want to talk to you anymore.");
				thisEngine.getController().updateActionPanelDialogue(possibleResponses);
				
				

				// receive the string that the user clicked as a String response from the HUD or engine

				String response = thisEngine.getController().getUserInputQueue().poll();

				while (response == null) {
					response = thisEngine.getController().getUserInputQueue().poll();
				}
				// plug this response into the processResponse method of the dialogueTree

				currentNode = npcDialogue.processResponse(response, currentNode);

				// repeat until node is null :D
			}
		}


		List<String> clearResponses = new ArrayList<String>();
		thisEngine.getController().updateActionPanelDialogue(clearResponses);

		boolean questAccomplished = thisEngine.checkQuestCompletionandClear(DataConstants.TALK_QUEST, thisNPC.getUniqueID());


		System.out.println(questAccomplished);
		String notification= null;
		if (questAccomplished) {
			notification = "You just accomplished a quest! View your remaining quests in the quest tab!";
		}

		return notification;

	}
}
