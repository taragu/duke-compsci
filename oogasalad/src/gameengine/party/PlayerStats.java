package gameengine.party;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains, records down, and udpates stats of player characters and NPCs and
 * the methods that change them.
 * 
 * @author Ujo, tn52, Tim
 * 
 */

public class PlayerStats {
	protected int myHealth;
	protected int myStrength;
	protected int myDefence;
	protected int myAgility;
	protected int myExperienceLevel;
	protected int myExperienceFactor;
	protected int myStrengthIncr;
	protected int myDefenceIncr;
	protected int myAgilityIncr;
	protected int myHealthIncr;
	protected int currentExperiencePoints;
	protected int currentHealth;

	/**
	 * Constructor: first five int parameteters are player/character stats and
	 * the last four are the increment amount of the stats when experience level
	 * increases
	 * 
	 * @param strength stat
	 * @param defence stat
	 * @param agility stat
	 * @param health stat
	 * @param experiencelvl stat
	 * @param experiencefactor: this number multiplied by the experience level is the number
	 *            of points required for player to gain to reach the next exp level
	 * @param strengthIncr: amount to increment strength by when experience level gains
	 * @param defenceIncr: amount to increment defence by when experience level gains
	 * @param agilityIncr: amount to increment agility by when experience level gains
	 * @param healthIncr: amount to increment health by when experience level gains
	 * @param description
	 */
	public PlayerStats(int strength, int defence, int agility, int health,
			int experiencelvl, int experiencefactor, int strengthIncr,
			int defenceIncr, int agilityIncr, int healthIncr) {

		myStrength = strength;
		myDefence = defence;
		myAgility = agility;
		myHealth = health;
		myExperienceLevel = experiencelvl;
		myExperienceFactor = experiencefactor;
		myStrengthIncr = strengthIncr;
		myDefenceIncr = defenceIncr;
		myAgilityIncr = agilityIncr;
		myHealthIncr = healthIncr;
		currentExperiencePoints = 0;
		currentHealth = myHealth;
	}

	/**
	 * Contains algorithm that calculates and returns the damage done on a
	 * player character or NPC after default attack during a battle.
	 * 
	 * @param otherPlayer
	 * @return
	 */
	public int fight(PlayerStats otherPlayer) {
		int damage = (int) Math
				.ceil((myStrength * (1 - otherPlayer.myDefence / 15.0)));
		int amount;

		if (myAgility >= otherPlayer.myAgility) {
			amount = (damage <= 0) ? 1 : damage;
		} else {
			double chanceOfMiss = (otherPlayer.myAgility - myAgility)
					/ (otherPlayer.myAgility * 1.0);
			double randomizer = Math.random();
			amount = (randomizer < chanceOfMiss / 2.0) ? 0 : damage;
		}

		otherPlayer.currentHealth -= amount;
		if (otherPlayer.currentHealth < 0)
			otherPlayer.currentHealth = 0;
		return amount;
	}

	/**
	 * Contains algorithm that calculates and returns the increase in experience
	 * points.
	 * 
	 * @param opponent
	 * @return
	 */
	private int experienceBoost(PlayerStats opponent) {
		return opponent.myExperienceLevel
				+ (opponent.myAgility + opponent.myDefence + opponent.myStrength)
				/ 3;
	}

	/**
	 * Increases and updates experience points, and if enough experience points
	 * are reached, increases experience level and calculates offset experience
	 * points.
	 */
	public void experienceLevelUp(PlayerStats opponent) {
		currentExperiencePoints += experienceBoost(opponent);
		while (currentExperiencePoints >= myExperienceLevel
				* myExperienceFactor) {
			currentExperiencePoints = -myExperienceLevel * myExperienceFactor;
			myExperienceLevel++;
			statsLevelUp();
		}
	}

	/**
	 * Updates increase in stats of a particular character after it levels up to
	 * a higher experience level
	 */
	public void statsLevelUp() {
		myStrength += myStrengthIncr;
		myDefence += myDefenceIncr;
		myAgility += myAgilityIncr;
		myHealth += myHealthIncr;
	}

	/**
	 * Updates increase in stats of a particular character after an item is used
	 * on it (or in the event of other events)
	 */
	public void statsBoost(int[] boostamount) {
		myStrength += boostamount[0];
		myDefence += boostamount[1];
		myAgility += boostamount[2];
		myHealth += boostamount[3];
	}

	/**
	 * Method to display all stats in String form
	 * 
	 * @return
	 */
	public String toString() {
		return "Strength: " + myStrength + " Defence : " + myDefence
				+ " Agility: " + myAgility + "Health: " + myHealth;
	}

	/**
	 * Returns Max health
	 * 
	 * @return
	 */
	public int getHealth() {
		return myHealth;
	}

	/**
	 * returns the current health of the character
	 * 
	 * @return
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * returns the current number of experience points of the character
	 * 
	 * @return
	 */
	public int getExperiencePoints() {
		return currentExperiencePoints;
	}

	/**
	 * returns the experience level of the character
	 * 
	 * @return
	 */
	public int getExperienceLevel() {
		return myExperienceLevel;
	}

	/**
	 * returns all stats held in a map.
	 * 
	 * @return
	 */
	public Map<String, Integer> getStats() {
		Map<String, Integer> currentStats = new HashMap<String, Integer>();
		currentStats.put("strength", myStrength);
		currentStats.put("defence", myDefence);
		currentStats.put("agility", myAgility);
		currentStats.put("current health", currentHealth);
		currentStats.put("health", myHealth);
		currentStats.put("level", myExperienceLevel);
		return currentStats;
	}

	/**
	 * updates the stats and sets them based on a map
	 * 
	 * @param updatedStats
	 */
	public void updateStats(Map<String, Integer> updatedStats) {
		myStrength = updatedStats.get("strength");
		myDefence = updatedStats.get("defence");
		myAgility = updatedStats.get("agility");
		myHealth = updatedStats.get("health");
	}

	/**
	 * Sets how much each stat should increment every level up
	 * 
	 * @param strincr
	 * @param defincr
	 * @param agilityincr
	 * @param healthincr
	 */
	public void setLevellingInfo(int strincr, int defincr, int agilityincr,
			int healthincr) {
		myStrengthIncr = strincr;
		myDefenceIncr = defincr;
		myAgilityIncr = agilityincr;
		myHealthIncr = healthincr;
	}

	/**
	 * sets the defence
	 * 
	 * @param defence
	 */
	public void setDefence(int defence) {
		myDefence = defence;
	}

}
