package gameengine.propertyimplementation;

import java.util.List;
import gameengine.gamedata.DataConstants;
import gameengine.party.PlayerStats;
import gameengine.properties.Fightable;

/**
 * class that implements the ability Fightable
 * @author Tara
 *
 */
public class Fight implements Fightable, DataConstants {

    private PlayerStats myPlayerStats;
    
    private List<Object> myUnprocessedStats;
    
    
    /**
     * create a Fight object by inputting the initial stats such as health, xp, etc
     * @param stats stores information of the background image (first argument) and the player stats
     */
    public Fight(List<Object> stats) {
        myUnprocessedStats = stats;
        System.out.println("^^^^^^Fight or cannot fight: unprocessed stats are "+myUnprocessedStats
        		+", size is "+myUnprocessedStats.size());
        if (!myUnprocessedStats.isEmpty() ||
        		myUnprocessedStats.size()!=0) {
        	createPlayerStats();
        }
    }
    
    /*
     * Create the PlayerStats object
     */
    private void createPlayerStats () {
        
        int strength = new Integer(myUnprocessedStats.get(0).toString());
        int defence = new Integer(myUnprocessedStats.get(1).toString());
        int agility = new Integer(myUnprocessedStats.get(2).toString());
        int health = new Integer(myUnprocessedStats.get(3).toString());
        int experiencelvl = new Integer(myUnprocessedStats.get(4).toString());
        int experiencefactor = new Integer(myUnprocessedStats.get(5).toString());
        int strengthIncr = new Integer(myUnprocessedStats.get(6).toString());
        int defenceIncr = new Integer(myUnprocessedStats.get(7).toString());
        int agilityIncr = new Integer(myUnprocessedStats.get(8).toString());
        int healthIncr = new Integer(myUnprocessedStats.get(9).toString());
        
        myPlayerStats = new PlayerStats(strength, defence, agility, health, experiencelvl, 
                                        experiencefactor, strengthIncr, defenceIncr, agilityIncr,
                                        healthIncr);
    }


    @Override
    public PlayerStats obtainPlayerStats () {
        return myPlayerStats;
    }
    
    
    /*
     * IMPLEMENT THIS METHOD EVERYTIME WE ADD A NEW IMPLEMENTATION OF AN CHARACTER ABILITY
     */
    public String toString () {
        return getClass().getName();

    }
}
