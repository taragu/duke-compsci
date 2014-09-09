package gameengine.party;

import gameengine.GameObject;
import gameengine.InventoryItem;
import gameengine.TransitionObject;
import gameengine.events.GameEventManagerInterface;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.PlayerCharacterData;
import gameengine.gamedata.QuestData;
import gameplayer.PlayerEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import jgame.JGObject;
import jgame.impl.JGEngineInterface;

/**
 * JGObject that contains data collection of Characters, Items, Quests
 * @author Amy
 *
 */
public class PartyObject extends JGObject implements DataConstants{

	private List<PlayerCharacterData> myPlayerCharacters;
	private PlayerCharacterData myActivePlayer;
	private List<InventoryItem> myItems = new ArrayList<InventoryItem>();
	private List<QuestData> myQuests;

	private GameEventManagerInterface myEventManager;
	private PlayerEngine myEngine;

	@SuppressWarnings("unused")
    private static int KEYUP;
	@SuppressWarnings("unused")
    private static int KEYDOWN;
	@SuppressWarnings("unused")
    private static int KEYLEFT;
	@SuppressWarnings("unused")
    private static int KEYRIGHT;

	private PartyObject(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname) {
		super(name, unique_id, x, y, collisionid, gfxname);

	}

	public PartyObject(List<PlayerCharacterData> players, GameEventManagerInterface gameEventManager){
		this(players.get(0).getName(), true, players.get(0).getXPos(), players.get(0).getYPos(),
				PARTYOBJECT_CID, players.get(0).getGFXName());
		myPlayerCharacters = players;
		myActivePlayer = myPlayerCharacters.get(0);
		myEventManager = gameEventManager;
		myQuests = new ArrayList<QuestData>();
	}

	/**
	 * WARNING:
	 * must be called in the engine before the move starts
	 * @param gameEventManager
	 */
	public void setPlayerEngine(PlayerEngine engine) {
		System.out.println("ENGINE SET");
		myEngine = engine;
	}

	/**
	 * Sets the Active Player character by index.
	 * @param index
	 */
	public void setActivePlayer(int index) {
		myActivePlayer = myPlayerCharacters.get(index);
		this.setGraphic(myActivePlayer.getGFXName());
	}

	/**
	 * Sets the active player by the name. If the name does not match, nothing happens
	 * @param name
	 */
	public void setActivePlayer(String name) {
		for(PlayerCharacterData c: myPlayerCharacters) {
			if(c.getName().equals(name))
				myActivePlayer = c;
		}
	}

	/**
	 * Return player stats for the active player character
	 * @return
	 */
	public PlayerStats getPlayerStats() {
		return myActivePlayer.getPlayerStats();
	}

	/**
	 * returns a map of PlayerStats, mapped by character name.
	 * @return
	 */
	public Map<String, PlayerStats> getAllPlayerStats() {
		Map<String, PlayerStats> map = new HashMap<String, PlayerStats>();
		for(int c=0; c<myPlayerCharacters.size(); c++) {
			map.put(myPlayerCharacters.get(c).getName(), myPlayerCharacters.get(c).getPlayerStats());
		}
		return map;
	}

	/**
	 * returns a list of items in the inventory
	 * @return
	 */
	public List<InventoryItem> getItems(){
		return myItems;	
	}

	/**
	 * returns the player characters
	 * @return
	 */
	public List<PlayerCharacterData> getPlayerCharacters(){
		return myPlayerCharacters;
	}

	/**
	 * returns the Player Characters in the party that are alive
	 * @return
	 */
	public List<String> getAlivePlayerCharacters() {
		List<String> charList = new ArrayList<String>();
		for (PlayerCharacterData theChar: myPlayerCharacters) {
			if(theChar.getPlayerStats().getCurrentHealth() > 0)
				charList.add(theChar.getName());
		}
		return charList;

	}

	/**
	 * returns true if the String is the name of a character in the party
	 * @return boolean
	 */
	public boolean isCharacterName(String name) {
		HashSet<String> charNames = new HashSet<String>();
		for(PlayerCharacterData c: myPlayerCharacters) {
			charNames.add(c.getName());
		}
		return charNames.contains(name);
	}

	/**
	 * returns the Names of the characters in the party
	 * @return
	 */
	public List<String> getCharacterNames() {
		List<String> charNames = new ArrayList<String>();
		for(PlayerCharacterData c: myPlayerCharacters) {
			charNames.add(c.getName());
		}
		return charNames;
	}

	/**
	 * Returns the active player character
	 * @return
	 */
	public PlayerCharacterData getActivePlayer(){
		return myActivePlayer;		
	}

	/**
	 * Returns a list of quest Descriptions
	 * @return List of String
	 */
	public List<String> getQuestDescriptions(){
		List<String> questDescriptions = new ArrayList<String>();
		for(int i=0; i<myQuests.size(); i++) {
			questDescriptions.add(myQuests.get(i).getQuestDescription());
		}
		return questDescriptions;
	}

	/**
	 * Increments a stat by a given amount
	 * @param stat - Name of the stat
	 * @param incrementBy - amount to increase the stat by
	 */
	public void incrementStat(String stat, int incrementBy) {
		Map<String, Integer> stats = getPlayerStats().getStats();

		int oldStat = stats.get(stat);

		int newStat = oldStat + incrementBy;

		stats.put(stat, newStat);
		getPlayerStats().updateStats(stats);
		myEngine.getController().updateActionPanelStats(getPlayerStats());

	}

	/**
	 * Adds an item to the party's inventory
	 * @param newInventoryItem - Item to be added
	 */
	public void addToInventory(InventoryItem newInventoryItem) {
		myItems.add(newInventoryItem);
	}

	/**
	 * Adds quest to party. Called when party collides with NPC that carries the quest.
	 * @param quest
	 */
	public void addQuest(QuestData quest) {
		myQuests.add(quest);
	}

	/**
	 * Clears completed quest from list. Called by event/clear quest event.
	 * @param questID
	 */
	public void clearQuest(String questID) {
		for(int i=0; i<myQuests.size(); i++) {
			if(myQuests.get(i).getQuestID().equals(questID)) {
				myQuests.remove(i);
			}
		}
	}

	/**
	 * questTypes are in DataConstants. 
	 * Called by an event; ex: if it's an item pickup event, then event gives ITEM_QUEST type and the unique ID of the item that it has picked up.
	 * ex: if it's a win battle event, the event gives BATTLE_QUEST type and the unique ID of the NPC that has been defeated.
	 * ex: if it's a NPC talk event, the event gives TALK_QUEST type and the unique ID of the NPC that was talked to.
	 * @param questType
	 * @param objectID
	 */
	public boolean checkQuestCompletion(String questType, String objectID) {
		for(int i=0; i<myQuests.size(); i++) {
			if(myQuests.get(i).checkQuestCompletion(questType, objectID)) {
				myQuests.remove(i);	
				return true;
			}
		}
		return false;
	}


	@Override
	public void move () {
		setDir(0, 0);
		yspeed = SPEED_CONSTANT;
		xspeed = SPEED_CONSTANT;
		if (eng.getKey(JGEngineInterface.KeyLeft)) {
			xdir = -DIRECTION_CONSTANT;
		}

		if (eng.getKey(JGEngineInterface.KeyRight)) {
			xdir = DIRECTION_CONSTANT;
		}

		if (eng.getKey(JGEngineInterface.KeyUp)) {
			ydir = -DIRECTION_CONSTANT;
		}

		if (eng.getKey(JGEngineInterface.KeyDown)) {
			ydir = DIRECTION_CONSTANT;
		}
	}

	@Override
	public void hit_bg(int tilecid) {		
		if (xdir<0) {
			x = getLastX();
		}
		if (xdir>0) {
			x = getLastX();
		}
		if (ydir<0) {
			y = getLastY();
		}
		if (ydir>0) {
			y = getLastY();
		}
	}

	@Override
	public void hit(JGObject object) {
		if(object instanceof TransitionObject) {
			Object [] args = {"transition", (GameObject) object};
			myEventManager.add(args);
		}
		
		if (xdir<0) {
			x = getLastX();
		}
		if (xdir>0) {
			x = getLastX();
		}
		if (ydir<0) {
			y = getLastY();
		}
		if (ydir>0) {
			y = getLastY();
		}
	}

}
