package gameauthoring.backend.elements;

import gameauthoring.backend.ArrayMap;

import java.util.HashMap;
import java.util.Map;

public class MetadataDefinition extends GenericDefinition{

	private static String GAME_NAME = "Game Name";
	private static String GENERAL = "General";
	private static String TIME_ON = "Time On";
	private static String GAME_TIME_OBJECT = "GameTimeObject";
	
	public MetadataDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
	}
	
	@Override
	public void setPublishType(){
		if (this.getMap().containsKey(GAME_NAME))
			myPublishType = GENERAL;
		if (this.getMap().containsKey(TIME_ON))
			myPublishType = GAME_TIME_OBJECT;
	}
	
	@Override
	public ArrayMap<String, Map<String, String>> getUsedIDs(String ID) {
		
		ArrayMap<String, Map<String, String>> toReturn = new ArrayMap<String, Map<String, String>>();
		
		toReturn.put(ID, new HashMap<String, String>());
		
		return toReturn;
		
	}
}
