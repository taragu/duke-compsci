package gameauthoring.backend.elements;

import gameauthoring.backend.ArrayMap;
import gameauthoring.util.GAUtilMethods;

import java.util.HashMap;
import java.util.Map;

public class EventDefinition extends GenericDefinition{

	public EventDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
		System.out.println(attributes);
	}

	@Override
	protected void compressAttributes(Map<String, String> attributes, String splitter) {
		super.compressAttributes(attributes, splitter);
		
		if (attributes.get("Type").equals("Transition")){
			String position =  GAUtilMethods.translateValue(attributes.get("Tile 1 MapX")) 
					 + splitter + GAUtilMethods.translateValue(attributes.get("Tile 1 MapY")) + splitter +  
					 GAUtilMethods.translateValue(attributes.get("Tile 2 MapX")) + splitter + 
					 GAUtilMethods.translateValue(attributes.get("Tile 2 MapY")) + splitter + 
					 attributes.get("Tile 1 MapMapID") + splitter + attributes.get("Tile 2 MapMapID");
			
			attributes.put("x", GAUtilMethods.translateValue(attributes.get("Tile 1 MapX")));
			attributes.put("y", GAUtilMethods.translateValue(attributes.get("Tile 1 MapY")));
			attributes.put("map", attributes.get("Tile 1 MapMapID"));
			attributes.put("type", "TransitionObject");
			attributes.put("abilities", "Transitionable");
			attributes.put("Transitionable", position);
		}		
		
	}
	

	@Override
	public ArrayMap<String, Map<String, String>> getUsedIDs(String ID) {
		ArrayMap<String, Map<String, String>> toReturn = new ArrayMap<String, Map<String, String>>();
		
		if (this.getMap().get("Type").equals("Transition"))
			toReturn.put(ID, new HashMap<String, String>());
		
		return toReturn;
	}

}
