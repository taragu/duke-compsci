package gameengine.properties;

import gameengine.party.PlayerStats;

/**
 * interface for GameOject to have the ability to fight 
 * @author Tara
 *
 */
public interface Fightable {
	
    /**
     * call this method to get the PlayerStats object
     * @return 
     * 
     */
    public PlayerStats obtainPlayerStats();
    
}
