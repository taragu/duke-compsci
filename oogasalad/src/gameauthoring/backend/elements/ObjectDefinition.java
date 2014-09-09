package gameauthoring.backend.elements;

import gameauthoring.backend.ArrayMap;

import java.util.HashMap;
import java.util.Map;


public abstract class ObjectDefinition {

    protected Map<String, String> myAttributes;
    protected String myType;
    protected String myPublishType;

    public ObjectDefinition(String type, Map<String, String> attributes){
    	update(type, attributes);
    }
    
    public String getType () {
        return myType;
    }
    
    public String getPublishType(){
    	return myPublishType;
    }

    public Map<String, String> getMap () {
        Map<String, String> myMapCopy = new HashMap<String, String>(myAttributes);
        return myMapCopy;
    }

    public void update (String type, Map<String, String> attributes) {
        myAttributes = attributes;
        myType = type;
        this.setPublishType();        	
    }    
    
    public Map<String, String> publish(Map<String, String> attributes, String splitter){
    	
    	Map<String, String> toReturn = new HashMap<String, String>(myAttributes);
    	for (String key: attributes.keySet()){
    		String value = attributes.get(key);
    		toReturn.put(key, value);
    	}
    	
    	compressAttributes(toReturn, splitter);
    	return toReturn;
    }
    
    protected abstract void compressAttributes(Map<String, String> attributes, String splitter);
    protected abstract void setPublishType();
    public abstract ArrayMap<String, Map<String, String>> getUsedIDs(String ID);
}