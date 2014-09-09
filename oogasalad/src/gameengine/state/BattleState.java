package gameengine.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import gameengine.InventoryItem;
import gameengine.NonPlayerCharacter;
import gameengine.gamedata.DataConstants;
import gameengine.party.PartyObject;
import gameengine.party.PlayerStats;
import gameplayer.PlayerEngine;

/**
 * Concrete battle game state, implements GameState
 * More of an example BattleState. Has a basic fighting algorithm
 * 
 * @author Timothy/Tze Kang
 *
 */

public class BattleState extends GameState implements DataConstants{

	private PartyObject myParty;
	private Queue<NonPlayerCharacter> myEnemies;
	private NonPlayerCharacter curEnemy;

	private PlayerStats myPlayerStats;
	private PlayerStats myEnemyStats;

	@SuppressWarnings("unused")
    private int playerHealth;
	@SuppressWarnings("unused")
    private int enemyHealth;

	private Queue<String> playerMoves;

	private final static List<String> playerOptions = Arrays.asList("Fight") ;
	
	private List<InventoryItem> itemList;
	private InventoryItem tempInventoryItem;

	private boolean playerturn;
	private boolean enemyturn;
	private String[] powers = new String[] {"strength", "defence", "agility", "health", "level"};
	
	/**
	 * 
	 * @param engine PlayerEngine
	 * @param party PartyObject
	 * @param enemy Queue<NonPlayerCharacter>
	 * @param background
	 */
	public BattleState(PlayerEngine engine, PartyObject party, Queue<NonPlayerCharacter> enemy, String background) {
		super(engine);
		myParty = party;
		myEnemies = enemy;
		curEnemy = myEnemies.poll();
		myPlayerStats = myParty.getPlayerStats();
		myEnemyStats = curEnemy.obtainPlayerStats();
		maptobattleTransition(background);
		generatePlayerOptions();		
	}

	@Override
	/**
	 * Implementation of battle algorithm
	 */
	public void doFrameGameState() {
		
		playerMoves = myEngine.getController().getUserInputQueue();
		
		if(myPlayerStats.getCurrentHealth() <= 0) {
			while(playerMoves.size() > 0) {
				String selection = playerMoves.poll();
				if(myParty.isCharacterName(selection)) {
					myParty.setActivePlayer(selection);
					myPlayerStats = myParty.getPlayerStats();
					myEngine.getController().updateActionPanelDialogue(playerOptions);
				}
			}
			return;
		}
		
		playerturn = ((playerMoves != null) && (playerMoves.size() > 0));
		enemyturn = false;
		
		if(playerturn) {
			String move = playerMoves.poll();
			if(move.equals(FIGHT)){	
				int damage = playerAttack();	
				myEngine.getController().addToActionPanelDialogue("You did " + damage + " to the enemy!");
			} else {
				playerUseItem(move);	
				myEngine.getController().addToActionPanelDialogue("Player Used " + move + "!" );
			}
			playerturn = false;
			enemyturn =  true;
		}

		if(myEnemyStats.getCurrentHealth() <= 0) {	
			myPlayerStats.experienceLevelUp(myEnemyStats);
			if(myEnemies.size() > 0) {
				curEnemy = myEnemies.poll();
				myEnemyStats = curEnemy.obtainPlayerStats();
			}
			else{					
				exitBattle();
			}
			myEngine.getController().updateActionPanelDialogue("You Defeated the Enemy! Your experience points are now " + myPlayerStats.getExperiencePoints());		
			return;
		}

		if(enemyturn){
			int damage = enemyAttack();
			myEngine.getController().addToActionPanelDialogue("The Enemy did " + damage + " damage to you!");
		}
		
		if(myPlayerStats.getCurrentHealth() <= 0) {
			myEngine.getController().updateActionPanelDialogue("Your Player Fainted!");
			List<String> charNames = myParty.getAlivePlayerCharacters();
			if(charNames.size() == 0) {
				myEngine.setGameState("GameOver");
			} else {
				myEngine.getController().updateActionPanelDialogue(charNames);
			}
		}
	}
	
	/**
	 * Creates an exit event for the battle state to return to map state
	 */
	private void exitBattle() {
		Object [] exitEventArgs = {"npcEndBattle", curEnemy};
		myEngine.getController().clearCurrentDialogue();
		myEngine.getEventManager().add(exitEventArgs);
		myEngine.getController().updateActionPanelDialogue(new ArrayList<String>());
		myParty.resume();
	}

	/**
	 * Converts fight moves of active player and items in inventory 
	 * to send to HUD controller to display.  
	 */
	private void generatePlayerOptions(){
		List<String> displayList = new ArrayList<String>();
		displayList.addAll(playerOptions);
		itemList = myParty.getItems();
		for(InventoryItem i: itemList){
			if (!i.getType().equals("Quest Item")){
				displayList.add(i.getName());
			}
		}
		myEngine.getController().updateActionPanelDialogue(displayList);
		myEngine.getController().updateActionPanelInventory(itemList);
	}
	
	/**
	 * Uses algorithm to calculate new health of active player after an enemy attack
	 */
	private int enemyAttack() {
		return myEnemyStats.fight(myPlayerStats);
	}

	/**
	 * Uses algorithm to calculate new health of enemy after a player attack
	 */
	private int playerAttack() {
		return myPlayerStats.fight(myEnemyStats);
	}

	/**
	 * Take in String name of item and applies the item to active player, 
	 * changing and updating its stats. Item is removed from inventory after. 
	 *  
	 * @param itemName
	 */
	@SuppressWarnings("unused")
    private void playerUseItem(String itemName){

		itemList = myParty.getItems();
		tempInventoryItem = null;
		for(InventoryItem i: itemList){
			if(i.getName().equals(itemName)){
				tempInventoryItem = i;				
			}
		}
		int[] itemPowers = tempInventoryItem.getPowers();
		String notification = "";
		for(int i=0;i<5;i++){
			String stat = powers[i];
			int incrementBy = tempInventoryItem.getPowers()[i];
			myEngine.boostStats(stat, incrementBy);
			
			for(int j=0; j<5;j++){
				if(tempInventoryItem.getPowers()[j]>0)
					notification = "You gained: " + tempInventoryItem.getPowers()[j] + " " + powers[j] + "! ";
				System.out.println(notification);
			}
		}
		myParty.getItems().remove(tempInventoryItem);
		
		generatePlayerOptions();
		
	}

	@Override
	/**
	 * Paints Battle information like HP, Level, HP, etc.
	 */
	public void paintFrameGameState() {
		myEngine.drawString(myParty.getActivePlayer().getName(), 130, 210, 0);
		myEngine.drawString("My Exp Points: " + myPlayerStats.getExperiencePoints(), 130, 270, 0);
		myEngine.drawString("Level: " + myPlayerStats.getExperienceLevel(), 130, 250, 0);
		myEngine.drawString("HP: " + myPlayerStats.getCurrentHealth()+"/"+myPlayerStats.getHealth(), 130, 230, 0);
		myEngine.drawImage( myParty.getActivePlayer().getGFXName(), 110, 150, false);
		myEngine.drawString("Level: " + myEnemyStats.getExperienceLevel(), 480, 490, 0);
		myEngine.drawString("HP: " + myEnemyStats.getCurrentHealth()+"/"+myEnemyStats.getHealth(), 480, 510, 0);
		myEngine.drawImage(curEnemy.getGFXName(), 450, 400, false);
		
	}

	/**
	 * Sets up the JGame for battle, removing things from map state and sets the correct images.
	 * @param bg
	 */
	private void maptobattleTransition(String bg){
		myEngine.getMyGameObjectManager().destroyActiveObjects();
		myParty.suspend();
		
		myEngine.setBGImage(0, bg, false, false);
		//myEngine.setBGImgOffset(0, myEngine.viewXOfs() + myEngine.viewWidth()/2, myEngine.viewYOfs() + myEngine.viewWidth()/2, false);
		String noTiles = ".................................";
		myEngine.setTiles(0,0, new String[] {noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,
				noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,
				noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,
				noTiles,noTiles,noTiles,noTiles,noTiles,noTiles,noTiles});

	}
}