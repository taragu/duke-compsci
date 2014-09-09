package gameauthoring.backend.elements;

import gameauthoring.backend.ArrayMap;
import gameauthoring.util.GAUtilMethods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDefinition extends ObjectDefinition{
	
    private static final String SPLIT = "\n";
    private static final String SPLIT_TWO = " ";
    private static final String SPLIT_THREE = ";;";
    private static final String[] MAP_NAMES = {"charitemmap","tilemap"};
    private static final String[] NO_NAMES = {"","empty"};
	
	public MapDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
	}    	

	@Override
	protected void compressAttributes(Map<String, String> attributes, String splitter) {
		attributes.put("map", attributes.get("tilemap"));
	}

	@Override
	protected void setPublishType() {
		myPublishType = "Maps";
	}

	@Override
	public ArrayMap<String, Map<String, String>> getUsedIDs(String ID) {
		
		ArrayMap<String, Map<String, String>> toReturn = new ArrayMap<String, Map<String, String>>();
		for (String mapName: MAP_NAMES){
			String currentMap = myAttributes.get(mapName);
			String[] byLine = currentMap.split(SPLIT);
			for (Integer lineCount=0; lineCount<byLine.length; lineCount++){
				String[] currentLineArray = byLine[lineCount].split(SPLIT_TWO);

				for(Integer spotCount=0; spotCount<currentLineArray.length; spotCount++){
					String current = currentLineArray[spotCount];
					current = current.trim();

					String[] currentSplit = current.split(SPLIT_THREE);

					for (String currentString: currentSplit){

						List<String> noNameList = Arrays.asList(NO_NAMES);
						if (!noNameList.contains(currentString)){
							Map<String, String> info = new HashMap<String, String>(); 
							info.put("x", GAUtilMethods.translateValue(spotCount.toString()));
							info.put("y", GAUtilMethods.translateValue(lineCount.toString()));
							info.put("map", ID);
							toReturn.put(currentString, info);

						}	
					}
				}
			}
		}

		Map<String, String> end = new HashMap<String, String>();
		toReturn.put(ID, end);

		return toReturn;
	}
	

}
