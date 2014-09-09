package gameauthoring.backend;

import gameauthoring.backend.elements.ObjectDefinition;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.util.HashGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectDefinitionManager {

    private static final String TILE = "Tiles";
    private static final String EVENT = "Event";
    private static final String MAP = "Map";
    private static final String MAPPER = "map";
    private static final String OBJECT = "Objects";
    private static final String SPLIT = ";;";
    private static final String questX = "Terminator ImageX";
    private static final String questY = "Terminator ImageY";
    private static final String questMAP = "Terminator ImageMapID";
    private static final String ABILITIES = "abilities";
    private static final String TYPE = "Type";
    private static final String QUEST = "Quest";
    private static final String IMAGEPATH = "imagepath";
    private static final String UNIQUEID = "uniqueid";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String TERMINATOR = "Terminator";
    private static final String NAME = "name";
    private static final String CHARACTER_ID = "Initiator ImageObjectID";
    private static final String CHARACTER_X = "Initiator ImageX";
    private static final String CHARACTER_Y = "Initiator ImageY";
    private static final String CHARACTER_MAP = "Initiator ImageMapID";
    private static final String QUESTABLE = "Questable";
    private static final String DESCRIPTION = "description";
	
	private ObjectDefinitionFactory myDefinitionFactory = new ObjectDefinitionFactory();
    private Map<String, ObjectDefinition> myDefinitionMap = new HashMap<String, ObjectDefinition>();
    
    /**
     * If id exists (key) in Definition Map, the ObjectDefinition object is replaced by obj.
     * If id does not exist, new entry is added to Map.
     * 
     * @param id key of Map entry
     * @param obj ObjectDefinition to be added or replaced
     */
    public void updateDefinition (String type, String id, Map<String, String> attributes) {
    	
       	ObjectDefinition obj = myDefinitionFactory.create(type, attributes);
       	myDefinitionMap.put(id, obj);
       	
    }

    /**
     * Returns a that contains only the ObjectDefinition values of
     * the specified type.
     * 
     * @param type specified type of the ObjectDefinition to be returned
     * @return Definition Map with Objects of specified type
     */
    public Map<String, Map<String, String>> getDefinitionOfType (String type) {
        return getObjectDataOfTypeFromMap(type, myDefinitionMap);
    }

    /**
     * Returns attributes map of an entry in Definition Map
     * 
     * @param id key of entry in Definition Map
     * @return attributes map of CharacterDefinition with specified id
     */ 
    public Map<String, String> getDefinitionData (String id) {

        ObjectDefinition obj = myDefinitionMap.get(id);
        if (obj!=null){
        	return obj.getMap();
        }
        return null;
    }
    
    public Map<String, ArrayMap<String, Map<String, String>>> publish(){
    	
    	Map<String, ArrayMap<String, Map<String, String>>> toReturn = new HashMap<String, ArrayMap<String, Map<String, String>>>();
    	
    	ArrayMap<String, Map<String, String>> map = this.getUsedIDs();
		this.updateEventsImagePath(map);
		Map<String, String[][]> uniqueIDMap = this.uniqueIdMap();
		this.addQuests(map, uniqueIDMap);
    	
    	for(String type: this.getPublishTypes()){
    		ArrayMap<String, Map<String, String>> publish = this.getObjectDataOfPublishTypeFromMap(type, SPLIT, uniqueIDMap, map);
    		
    		if (type.equals(TILE)) 
    				publish.compress();
    		
    		toReturn.put(type, publish);
    	}    	
    	return toReturn;
    }
    
    private Map<String, String[][]> uniqueIdMap(){
    	Map<String, Map<String, String>> map = this.getDefinitionOfType(MAP);
    	Set<Integer> uniques = new HashSet<Integer>();
    	Map<String, String[][]> uniqueKeyMap = new HashMap<String, String[][]>();
    	for (String key: map.keySet()){
    		String currentMap = map.get(key).get("charitemmap");
    		String[] byLine = currentMap.split("\n");
    		Integer dimension2 = byLine.length;
    		Integer dimension = byLine[0].split(" ").length;
    		String[][] uniqueMap = new String[dimension][dimension2];
    		for (Integer dimensionCount=0; dimensionCount<dimension; dimensionCount++){
    			for (Integer dimension2Count=0; dimension2Count<dimension2; dimension2Count++){
    				uniqueMap[dimensionCount][dimension2Count]=HashGenerator.newIntegerHash(uniques).toString();
    			}
    		}
    		uniqueKeyMap.put(key, uniqueMap);
    	}
    	
    	return uniqueKeyMap;
    }
    
    private void addQuests(ArrayMap<String, Map<String, String>> map, Map<String, String[][]> uniqueMap){
    	Map<String, Map<String, String>> eventMap = this.getDefinitionOfType(EVENT);
    	for (Map<String, String> event: eventMap.values()){
    		if (event.get(TYPE).equals(QUEST)){
    			
    			Integer xPos = Integer.parseInt(event.get(questX)); 
				Integer yPos = Integer.parseInt(event.get(questY));
    			
    			String[][] currentUniqueMap = uniqueMap.get(event.get(questMAP));
    			String uniqueID = currentUniqueMap[xPos][yPos];
    			String questInfo = event.get(TERMINATOR)+ SPLIT + SPLIT+ event.get(NAME) + SPLIT + SPLIT + uniqueID + SPLIT + SPLIT + event.get(DESCRIPTION);
    			String id = event.get(CHARACTER_ID);
    			String x = GAUtilMethods.translateValue(event.get(CHARACTER_X));
    			String y = GAUtilMethods.translateValue(event.get(CHARACTER_Y));
    			String mapid = event.get(CHARACTER_MAP);
    			List<Map<String, String>> characterList = map.getValueList(id);
    			for (Map<String, String> character: characterList){
    				if (character.get(X).equals(x) & character.get(Y).equals(y) && character.get(MAPPER).equals(mapid)){
    					character.put(QUESTABLE, questInfo);
    					if (myDefinitionMap.get(id).getMap().containsKey(ABILITIES))
    						character.put(ABILITIES, myDefinitionMap.get(id).getMap().get(ABILITIES)+SPLIT + QUESTABLE);
    					else
    						character.put(ABILITIES, "Questable");
    					
    					
    				}
    			}
    		}
    	}
    }
     
    private Set<String> getPublishTypes(){
    	Set<String> variables = new HashSet<String>();
    	for (ObjectDefinition obj: myDefinitionMap.values()){
    		variables.add(obj.getPublishType());
    	}
    	return variables;
    }
    
    private Map<String, Map<String, String>> getObjectDataOfTypeFromMap (String type, Map<String, ObjectDefinition> map) {
        Map<String, Map<String, String>> toReturn = new HashMap<String, Map<String, String>>();
        
        for (String id : map.keySet()) {
            ObjectDefinition object = map.get(id);

            if (object == null)
                return null;

            if (object.getType().equals(type)) {
                toReturn.put(id, object.getMap());
            }
        }
        return toReturn;
    }
    
    private ArrayMap<String, Map<String, String>> getObjectDataOfPublishTypeFromMap (String type, String splitter, Map<String, String[][]> ids, 
    		ArrayMap<String, Map<String, String>> idMap) {
        
        ArrayMap<String, Map<String, String>> toReturn = new ArrayMap<String, Map<String, String>>();
        for (String id: idMap.getUniqueKeys()) {
        	List<Map<String, String>> attributes = idMap.getValueList(id);
            ObjectDefinition object = myDefinitionMap.get(id);
            if (object.getPublishType().equals(type)){
            	
            	for (Map<String, String> attribute: attributes){
            		
            		if (object.getPublishType().equals(OBJECT) & !object.getType().equals(EVENT)){
            			
            			String[][] currentMap = ids.get(attribute.get(MAPPER));
            			String x = GAUtilMethods.unTranslateValue(attribute.get(X));
            			String y = GAUtilMethods.unTranslateValue(attribute.get(Y));
            			attribute.put(UNIQUEID, currentMap[Integer.parseInt(x)][Integer.parseInt(y)]);            			
            		}
            		toReturn.put(id, object.publish(attribute, splitter));
                }	
            }
        }
        return toReturn;
    }
        
    private ArrayMap<String, Map<String, String>> getUsedIDs (){
      
    	ArrayMap<String, Map<String, String>> toReturn = new ArrayMap<String, Map<String, String>>(); 

    	//gets map positions for tiles and stuff
    	for (String key: myDefinitionMap.keySet()){
    		toReturn.combine(myDefinitionMap.get(key).getUsedIDs(key));
    	}
    	
    	return toReturn;
    }
    
    private void updateEventsImagePath(ArrayMap<String, Map<String, String>> map){
    	for (String key: map.getKeys()){
    		ObjectDefinition obj = myDefinitionMap.get(key);
    		if (obj.getType().equals(EVENT)){
    			Map<String, String> event = obj.getMap();
    			String id = event.get("Tile 1 MapTileID");
    	    	if (id ==null)
    	    		id = event.get("Tile 1 ObjectID");
    	    	event.put(IMAGEPATH, getImagePathOfItem(id));
    	    	obj.update(obj.getType(), event);
    		}    		
    	}
    }
    
    private String getImagePathOfItem (String id){
    	Map<String,String> map = this.getDefinitionData(id);
    	if (map!=null){
    		if (map.containsKey(IMAGEPATH)){
    			return map.get(IMAGEPATH);
    		}
    	}
    	return "";
    }
}
