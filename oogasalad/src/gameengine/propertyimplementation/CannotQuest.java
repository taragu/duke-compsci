package gameengine.propertyimplementation;

import java.util.List;

import gameengine.gamedata.QuestData;
import gameengine.properties.Questable;

/**
 * Class that implements interface Questable for characters that cannot do quests
 * 
 * @author Tara
 *
 */
public class CannotQuest extends Quest implements Questable {

    public CannotQuest (List<Object> input) {
        super(input);
        
    }

    public QuestData offerQuest() {
        //DO NOTHING
        return null;
    }
    
}
