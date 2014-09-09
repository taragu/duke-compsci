package gameengine.events;

import gameengine.GameObject;
import gameengine.InventoryItem;
import gameengine.gamedata.DataConstants;
import gameplayer.PlayerEngine;

public class ItemPickupEvent extends ItemGameEvent {
	
	private String[] powers = new String[] {"strength", "defence", "agility", "health", "level"};
	
	public ItemPickupEvent(String eventType, GameObject myItem) {
		super(eventType, myItem);	
	}

	@SuppressWarnings("unused")
    @Override
	public String execute(PlayerEngine thisEngine) {
		InventoryItem ii = thisItem.pickUp();
		String notification = "You got: " + ii.getName(); 
		String type = ii.getType();
		if (type.equals("Weapon") && (thisEngine.getActivePlayer().getPlayerStats() != null)){
			int[] itemPowers = ii.getPowers();
			for (int i=0;i<5;i++) {
				String stat = powers[i];
				int incrementBy = ii.getPowers()[i];
			    thisEngine.boostStats(stat, incrementBy);
			    System.out.println("In item pickup event. BOOSTED " + stat + "by " + incrementBy); 
			    notification = "You gained: ";
				
				for (int j=0;j<5;j++) {
					if(ii.getPowers()[j]>0)
					notification = notification + ii.getPowers()[j] + " " + powers[j] + "! ";
				}
			}
			thisEngine.getController().updateActionPanelStats(thisEngine.getActivePlayer().getPlayerStats());
		}
		
		thisEngine.addToInventory(ii);

		boolean questAccomplished = thisEngine.checkQuestCompletionandClear(DataConstants.ITEM_QUEST, thisItem.getUniqueID());
		thisEngine.removeObject(thisItem);
		if (questAccomplished) {
			notification = "You just accomplished a quest! View your remaining quests in the quest tab!";
		}
		return notification;
	}

}
