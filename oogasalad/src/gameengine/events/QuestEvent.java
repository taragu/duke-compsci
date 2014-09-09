package gameengine.events;

import java.util.Arrays;
import java.util.List;


import gameengine.GameObject;
import gameengine.gamedata.QuestData;
import gameplayer.PlayerEngine;
/**
 * NPC offers Party the QuestData and gives it away if accepted
 * @author Amy/Zanele
 *
 */
public class QuestEvent extends NPCGameEvent{

	public QuestEvent(String eventType, GameObject thisNPC) {
		super(eventType, thisNPC);
		
	}

	@Override
	public String execute(PlayerEngine engine) {
		QuestData offeredQuest = thisNPC.offerQuest();

		if (offeredQuest != null) {

			String offer = "You are offered a quest!\n" + offeredQuest.getQuestDescription() + "\n Do you accept?";

			engine.getController().updateActionPanelDialogue(offer);
			List<String> possibleResponses = Arrays.asList("Yes", "No");

			engine.getController().updateActionPanelDialogue(possibleResponses);

			String response = engine.getController().getUserInputQueue().poll();



			while (response == null) {
				response = engine.getController().getUserInputQueue().poll();
			}


			if (response.equals("Yes")) {
				engine.addPartyQuest(offeredQuest);
				engine.updateQuestStatus();
				engine.getController().clearActionPanelNotificationUserInput();
				//thisNPC.removeQuest();
				return "You have accepted! Check the Quest panel for details.";
			} else {
				engine.getController().clearActionPanelNotificationUserInput();
				return "You have declined the quest. Your lack of enthusiastic consent is respected.";
			}

		}
		

		return null;


	}
}
