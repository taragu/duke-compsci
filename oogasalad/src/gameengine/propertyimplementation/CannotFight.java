package gameengine.propertyimplementation;

import java.util.List;
import gameengine.party.PlayerStats;
import gameengine.properties.Fightable;


/**
 * class that implements Fightable; disable the object's ability to fight
 * @author Tara
 *
 */
public class CannotFight extends Fight implements Fightable {

    public CannotFight (List<Object> initialStats) {
        super(initialStats);
    }
    
    @Override
    public PlayerStats obtainPlayerStats() {
        // do nothing
        return null;
    }

}
