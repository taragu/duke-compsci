package gameauthoring.backend;

import gameauthoring.util.HashGenerator;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Controller {

    private ObjectDefinitionManager myDataManager = new ObjectDefinitionManager();
    private Set<Integer> myCharacterSet = new HashSet<Integer>();
    private Set<Integer> myIntegerSet = new HashSet<Integer>();
    private Publisher myPublisher = new Publisher();
    
    public String getNewShortID () {
        int id = HashGenerator.newCharacterHash(myCharacterSet);
        myCharacterSet.add(id);
        return Character.toString((char) id);
    }

    public String getNewLongID () {
        Integer id = HashGenerator.newIntegerHash(myIntegerSet);
        myIntegerSet.add(id);
        return id.toString();
    }

    public void loadFile (File file) {

    }

    public void saveFile (String location) {

    }

    public void updateDefinition (String type, String id, Map<String, String> attributes) {
        myDataManager.updateDefinition(type, id, attributes);
    }

    public Map<String, String> getDefinitionData (String id) {
        return myDataManager.getDefinitionData(id);
    }

    public Map<String, Map<String, String>> getDefinitionsOfType (String type) {
        return myDataManager.getDefinitionOfType(type);
    }
    
    public void publish (String location) {
    	
    	Map<String, ArrayMap<String, Map<String, String>>> toPublish = myDataManager.publish();
    	
    	for (String type: toPublish.keySet()){
    		ArrayMap<String, Map<String, String>> publish = toPublish.get(type);
    		myPublisher.addToJSON(type, publish);
    	}
    	
        myPublisher.publishJSON(location);
    }

}
