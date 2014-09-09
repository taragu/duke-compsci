package gameengine;

import gameengine.events.DialogueTree;
import gameplayer.PlayerEngine;
import java.util.List;
import java.util.Map;
import jgame.JGObject;
import util.ErrorLogger;


/**
 * PlayerCharacter is the main character of the game, the character that the user controls
 * 
 * @author Tara, Trey
 * 
 */

public class PlayerCharacter extends GameCharacter {


	private static int KEYUP;
	private static int KEYDOWN;
	private static int KEYLEFT;
	private static int KEYRIGHT;

	private PlayerEngine myDisplay;

	/**
	 * Constructor to create a PlayerCharacter with default stats
	 * 
	 * @param name
	 * @param isUnique
	 * @param x
	 * @param y
	 * @param collisionid
	 * @param gfxname
	 * @param type
	 * @param uniqueID
	 * @param abilityInputMap
	 * @param abilityImplementationMap
	 * @param mapUniqueID
	 */
	public PlayerCharacter (String name,
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
		super(name, true, x, y, collisionid, gfxname, type, uniqueID,
				abilityInputMap, abilityImplementationMap, mapUniqueID); 
		myMapUniqueID = null;
		myMap = null;
		KEYUP = Control.getKeyUp();
		KEYDOWN = Control.getKeyDown();
		KEYLEFT = Control.getKeyLeft();
		KEYRIGHT = Control.getKeyRight();
		this.setSpeed(SPEED_CONSTANT);
		

	}

	/**
	 * set the Display object used in this game so that in the move() method
	 * PlayerCharacter can be controlled by user defined keys
	 * 
	 * @param display
	 */
	public void setDisplay (PlayerEngine display) {
		myDisplay = display;
		ErrorLogger.writeToLog("PlayerCharacter setDisplay: display is "+myDisplay.toString(),
				"info");
	}

	@Override
	public void move () {
		setDir(0, 0); 
		if (myDisplay.getKey(KEYLEFT)) {
			xdir = -SPEED_CONSTANT;
		}

		if (myDisplay.getKey(KEYRIGHT)) {
			xdir = SPEED_CONSTANT;
		}

		if (myDisplay.getKey(KEYUP)) {
			ydir = -SPEED_CONSTANT;
		}

		if (myDisplay.getKey(KEYDOWN)) {
			ydir = SPEED_CONSTANT;
		}
	}


	@Override
	public void hit_bg(int tilecid){
//		Object [] args = {"tilecollision", tilecid};
//    	myEventManager.add(args);
		if (tilecid==NOTWALKABLE_CID){
			System.out.println("HIT BG");
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

	@Override

	public void hit(JGObject object) {
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
	public DialogueTree talk () {
		return null;
	}

	


	
}
