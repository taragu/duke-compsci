package gameengine.properties;

import gameengine.gamedata.QuestData;

/**
 * Interface for characters that can make quests
 * 
 * @author Tara
 *
 */
public interface Questable {

    /**
     * method for a character to make a quest
     * 
     */
	QuestData offerQuest();	
    
}
