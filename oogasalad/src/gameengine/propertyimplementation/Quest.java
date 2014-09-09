package gameengine.propertyimplementation;

import java.util.List;

import gameengine.gamedata.QuestData;
import gameengine.properties.Questable;

/**
 * Class that implements Questable; characters that instantiate this class can do quests
 * 
 * @author Tara
 *
 */
public class Quest implements Questable {

	private QuestData myQuestData;
    /**
     * Instantiate a Quest object
     * 
     * @param input
     */
    public Quest(List<Object> input) {
        if (input.size()!=0) {
        
            myQuestData = new QuestData(input.get(0).toString(), input.get(1).toString(), 
                                        input.get(2).toString(), input.get(3).toString());
        }
    }
    
    @Override
    public QuestData offerQuest () {
        
        return myQuestData;
    }
}
