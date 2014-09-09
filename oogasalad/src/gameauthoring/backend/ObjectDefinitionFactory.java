package gameauthoring.backend;

import gameauthoring.backend.elements.ObjectDefinition;

import java.util.Map;

import util.Reflection;

public class ObjectDefinitionFactory {

    public ObjectDefinition create (String type, Map<String, String> attributes) {
    	
    	final String DEFINITION_PATH = "gameauthoring.backend.elements.";
    	final String DEFINITION = "Definition";
    	final String GENERIC = "Generic";
    	
    	ObjectDefinition toReturn;
    	
    	try{
    		toReturn = (ObjectDefinition) Reflection.createInstance(DEFINITION_PATH + type + DEFINITION, type, attributes);
    	}
    	catch (Exception e){
    		toReturn = (ObjectDefinition) Reflection.createInstance(DEFINITION_PATH+ GENERIC + DEFINITION, type, attributes);
    	}

        return toReturn;
    }

}
