package gameauthoring.backend.elements;

import gameauthoring.backend.ArrayMap;
import gameauthoring.util.Bundles;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.workspace.ObjectTab;
import gameplayer.PlayerEngine;

import java.util.Map;
import java.util.ResourceBundle;

public class GenericDefinition extends ObjectDefinition{

	private ResourceBundle myLanguage = Bundles.getLanguageBundle();
	
	public GenericDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
	}

	@Override
	protected void compressAttributes(Map<String, String> attributes,
			String splitter) {
		for (String attribute: attributes.keySet()){
    		if (attribute.endsWith("able")){
    			String finalString = "";
    			String value = attributes.get(attribute);
    			String[] splitted = value.split(splitter);
    			for (Integer count=0; count<splitted.length; count++){
    				if (count%2==0)
    					finalString += splitted[count] + splitter;
    			}
    			attributes.put(attribute, finalString.substring(0, finalString.length()-splitter.length()));
    		}
    	}
		this.removeSpaces(attributes, "name");
		this.removeSpaces(attributes, "type");
		
    	if (attributes.containsKey("imagepath")){
    		changeImageFilePath("imagepath", attributes.get("imagepath"), attributes, PlayerEngine.DEFAULT_TILE_SIZE, PlayerEngine.DEFAULT_TILE_SIZE);
    		
    	}
    	
    	if (attributes.containsKey("Fight Background")) {
    		int width = Integer.parseInt(myLanguage.getString("BackgroundWidth"));
    		int height = Integer.parseInt(myLanguage.getString("BackgroundHeight"));
    		changeImageFilePath("Fight Background", attributes.get("Fight Background"), attributes, width, height);
    	}
	}
	
	private void removeSpaces(Map<String, String> attributes, String attribute){
		if (attributes.containsKey(attribute)){
			String finalString = "";
			String value = attributes.get(attribute);
			finalString = value.replaceAll(" ", "");
			attributes.put(attribute, finalString);
		}
	}
	
	protected void changeImageFilePath(String key, String originalValue, Map<String, String> attributes, int width, int height) {
		String valueToRemove = ObjectTab.BASE_FILE_PATH;
		String newFilePath = "";
		String valueToAdd = "media/resources/";
		if (originalValue.startsWith(valueToRemove)) {
    		newFilePath = originalValue.substring(valueToRemove.length());
    		String saveFilePath = "src/gameplayer/" + valueToAdd + newFilePath;
    		newFilePath = valueToAdd + newFilePath;
    		attributes.put(key, newFilePath);
    		GAUtilMethods.resaveImage(originalValue, saveFilePath, width, height);
		}	
	}
	
	@Override
	protected void setPublishType() {
		myPublishType = "Objects";
		
	}

	@Override
	public ArrayMap<String, Map<String, String>> getUsedIDs(String ID) {
		return new ArrayMap<String, Map<String, String>>();
	}
}
