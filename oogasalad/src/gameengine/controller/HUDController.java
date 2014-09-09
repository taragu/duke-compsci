package gameengine.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import gameengine.InventoryItem;
import gameengine.party.PartyObject;
import gameengine.party.PlayerStats;
import gameengine.gamedata.DataConstants;
import gameplayer.ActionPanel;
import gameplayer.PlayerEngine;
/**
 * Controller for action panel and engine interaction
 * 
 * @author Amy/Ashley
 * 
 */
public class HUDController implements DataConstants {

    private ActionPanel myActionPanel;
    @SuppressWarnings("unused")
    private PlayerEngine myEngine;
    private Queue<String> userInputs;
    private List<String> myCurrentDialogue = new ArrayList<String>();
    
    public HUDController (ActionPanel panel) {
        myActionPanel = panel;
        myActionPanel.setController(this);
        userInputs = new LinkedList<String>();
    }
    
    public void updateActionPanelStats(PartyObject party) {
    	myActionPanel.updateStatPanel(party.getPlayerStats());
	}
    
    public void updateActionPanelStats(PlayerStats playerStats){
    	myActionPanel.updateStatPanel(playerStats);
    }

	public void updateActionPanelDialogue(String dialogueString) {
		System.out.println("I AM BEING UPDATED");
		myActionPanel.updateNotificationPanel(dialogueString);
	}
	
	public void addToActionPanelDialogue(String update) {
		myCurrentDialogue.add(update);
		myActionPanel.updateNotificationPanelListed(myCurrentDialogue);
	}
	
	public void clearCurrentDialogue() {
		myCurrentDialogue.clear();
	}
	
	public void clearActionPanelNotificationUserInput(){
		myActionPanel.clearNotificationUserInput();
	}
	
	public void updateActionPanelInventory(List<InventoryItem> inventoryList){
		myActionPanel.updateInventoryPanel(inventoryList);
	}
	
	public void updateActionPanelDialogue(List<String> actions){	
		myActionPanel.updateNotificationPanel(actions);
	}
	
	public void updateActionPanelParty(PartyObject party){
		myActionPanel.updatePartyPanel(party);
	}

	public void updateActionPanelQuests(List<String> quests){
		myActionPanel.updateQuestPanel(quests);
	}
	
	public void setCommand(String command){
		userInputs.add(command);
	}
	
	public Queue<String> getUserInputQueue(){
		System.out.println(userInputs);
		return userInputs;
	}


	public void setEngine(PlayerEngine engine) {
		myEngine = engine;
	}
}
