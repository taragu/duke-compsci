package gameengine.gamedata;
/**
 * Class that stores quest information
 * @author Amy
 *
 */
public class QuestData implements DataConstants{

	private String myType;
	private String myQuestID;
	private String myGoalID;
	private String myDescription;
	/**
	 * questTypes are already created in DataConstants.
	 * 
	 * @param questType
	 * @param goalID
	 */
	public QuestData(String questType, String questID, String goalID, String description) {
		myType = questType;
		myQuestID = questID;
		myGoalID = goalID;
		myDescription = description;
	}
	
	public String getQuestID() {
		return myQuestID;
	}
	
	/**
	 * Called for HUD display.
	 * @return
	 */
	public String getQuestDescription() {
		return myDescription;
	}
	
	/**
	 * questTypes are in DataConstants. 
	 * Called by party.
	 * @param questType
	 * @return
	 */
	public boolean checkQuestCompletion(String questType, String objectID) {
		System.out.println("Quest in List has questtype: " + questType);
		System.out.println("Quest in List has objectID: " + objectID);
		
		System.out.println(questType.equals(myType));
		System.out.println(objectID.equals(myGoalID));
		
		if(questType.equals(myType) && objectID.equals(myGoalID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
