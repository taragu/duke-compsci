package mastercode;


import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameDataLibrary;
import gameplayer.PlayerEngine;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import util.Reflection;

/**
 * Class that takes in a json file, looks up properties file for items to expect and their types, and output
 *  the processed data into a list of arguments and they're ready to be instantiated whenever the user wants
 *  by calling ObjectFactory.createObjects(List<Object[] args>) which is the output you'll get from this 
 *  class.
 * 
 * @author Tara
 *
 */
public class Translator implements DataConstants {

    /**
     * path to save the media table
     */
	@SuppressWarnings("unused")
    private final String PATH_TO_SAVE = "src" + File.separator + "gameplayer"
			+File.separator+"media"+File.separator+"media.tbl";
    
    private PropertiesFileReader myPropertiesFileReader;
    
    private MediaProcessor myMediaProcessor;
    
    /**
     * a list of all the abilities a game object can have
     */
    private List<String> myAbilitiesForGameObjects;
    
    public Translator() {
        myAbilitiesForGameObjects = GameDataLibrary.getAbilitiesForGameObjects();
    }
    
    /**
     * Translate only the specified key in the json file
     * @param itemKey
     * @param jsonFilePath
     * @param propertiesFilePath
     * @return
     */
    protected List<Object[]> translateSpecifiedItem(String itemKey, String jsonFilePath,
                                                 String propertiesFilePath) {
        List<Object[]> processedObjects = new ArrayList<Object[]>();
        myPropertiesFileReader = new PropertiesFileReader(propertiesFilePath);
        JSONParser parser = new JSONParser();
        myMediaProcessor = new MediaProcessor();
        
        try {
            Object obj = parser.parse(new FileReader(jsonFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            List<String> subObjectKeys = myPropertiesFileReader.getSubObjects("SubObjectNames");
            
            for (Object thisKey : jsonObject.keySet()) {
                
                if ((itemKey!=null && ((String) thisKey).equals(itemKey)) || itemKey == null) {
                
                    JSONArray jsonArray = (JSONArray) jsonObject.get(thisKey);
                    String thisObjectKey = (String) thisKey;
                    
                    
                    if (subObjectKeys.contains(thisObjectKey)) {
                        List<String> attributeKeys = myPropertiesFileReader.getSubObjects(thisObjectKey);
                        Object[] args = new Object[attributeKeys.size()];
                        // look for attributes in the json file, and cast it to the type the user defined in the
                        // properties file
                        
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject subObject = (JSONObject) jsonArray.get(i);
                            
                            List<String> thisObjectsAbilities = splitToList(subObject, ABILITIES);
                            for (int j=0; j<attributeKeys.size();j++){
                                String thisAttribute = attributeKeys.get(j);
                                String expectingType = myPropertiesFileReader.getSubObjects(thisAttribute).get(0);
                                
                                if (expectingType.equals(ASSIGN_CID)) {
                                    args[j] = myMediaProcessor.assignedCID(subObject);
                                } else if (expectingType.equals(TOMEDIATABLE)) {
                                    args[j] = myMediaProcessor.processMedia((String) thisKey, subObject);
                                } else if (expectingType.equals(ABILITYPROCESSING)) {
                                    args[j] = processAbility(thisAttribute, thisObjectsAbilities, subObject);
                                } else if (expectingType.equals(FIXED_VALUE)) {
                                    Object value = myPropertiesFileReader.getSubObjects(thisAttribute).get(1);
                                    args[j] = castValueToCorrectType(value, "boolean");
                                } else if (expectingType.equals(TILESIZEPROCESSING)) { 
                                    Object value = subObject.get(thisAttribute);
                                    args[j] = ((Double) castValueToCorrectType(value, "double")) 
                                            * PlayerEngine.DEFAULT_TILE_SIZE;
                                
                                } else {
                                    Object value = subObject.get(thisAttribute);
                                    System.out.println("Translator: value = "+value);
                                    //CAST THE VALUE TO THE CORRECT TYPE
                                    value = castValueToCorrectType(value, expectingType);
                                    
                                    args[j] = value;
                                }
                            }
                            processedObjects.add(args);
                            if (((String)thisKey).equals("Tiles")) {
                                myMediaProcessor.addCreatedTile(args);
                            }
                            args = new Object[attributeKeys.size()];
                        }
                    } else {
                        System.out.println("THERE'S NO KEY IN THE PRE-DEFINED PROPERTIES FILE FOR "+ thisKey);
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("Translator translateJsonFile: IOException");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return processedObjects;
    }
    
    
    /**
     * Method to translate json file into a list of arguments (of objects) that are ready to be instantiated
     * 
     * @param jsonFilePath example: "src/translator/tests/Test.json"
     * @param propertiesFilePath example: "src/translator/ExpectingKeys.properties"
     * @return list of processed objects that are ready to be instantiated
     */
    protected List<Object[]> translateJsonFile(String jsonFilePath,
                                                        String propertiesFilePath) {
        return translateSpecifiedItem(null, jsonFilePath, propertiesFilePath);
    }

    /**
     * Call this method to instantiate the processed objects created in the translateJsonFile method
     *  in this class (Translator)
     *  
     * @param args
     * @param packagePathPlusClassName example: "translator.tests.GameObject"
     * @return instantiated object
     */
    protected Object instantiateProcessedObject(Object[] args, String packagePathPlusClassName){
        return Reflection.createInstance(packagePathPlusClassName, args);
    }

    
    protected Map<String, String> getUniqueIDSymbolMap() {
        return myMediaProcessor.getUniqueIDSymbolMap();
    }
    
    /* 
     * cast the value to correct type so that they can be instantiated with Reflection in the future
     * 
     * */
    private Object castValueToCorrectType (Object value, String expectingType) {
        if (expectingType.equals("int")) {
            return new Integer(value.toString());
            
        } else if (expectingType.equals("double")) {
            return Double.parseDouble(value.toString());
            
        } else if (expectingType.equals("float")) {
            return new Float(value.toString());
            
        } else if (expectingType.equals("boolean")) {
            return Boolean.valueOf(value.toString());
            
        } else if (expectingType.equals("String")) {
            return (String) value;
            
        } else if (expectingType.equals("byte")) {
            return new Byte(value.toString());
            
        } else if (expectingType.equals("short")) {
            return new Short(value.toString());
            
        } else if (expectingType.equals("long")) {
            return new Long(value.toString());
            
        } else if (expectingType.equals("char")) {
            return (Character) value;
        }
        return null;
    }
    

    /*
     * process the inputs and implementation of abilities
     */
    private Object processAbility (String attribute, List<String> thisObjectsAbilities, JSONObject subObject) {
        if (attribute.equals(PROPERTYINPUTMAP)) {
            Map<String, List<Object>> inputMap = new HashMap<String, List<Object>>();
            for (String thisAbility : myAbilitiesForGameObjects) {
                // find this ability in thisObjectsAbilities
                List<Object> inputs = new ArrayList<Object>();
                if (thisObjectsAbilities.contains(thisAbility)) {
                    // if it has it, then add to the abilities and input map
                    String inputString = (String) subObject.get(thisAbility);
                    inputs.addAll(new ArrayList<Object>(Arrays.asList
                                                (inputString.split(myPropertiesFileReader.getSplitPattern()))));
                    inputMap.put(thisAbility, inputs);
                    
                } else {
                    inputMap.put(thisAbility, inputs);
                }
            }
            return inputMap;
        } else if (attribute.equals(PROPERTYIMPLEMENTATIONMAP)) {
            Map<String, String> implementationMap = new HashMap<String, String>();
            for (String thisAbility : myAbilitiesForGameObjects) {
                if (subObject.get(thisAbility)!=null) {
                    implementationMap.put(thisAbility, 
                                          thisAbility.substring(0, thisAbility.length()-4));
                } else {
                    implementationMap.put(thisAbility, "Cannot"+
                             thisAbility.substring(0, thisAbility.length()-4));
                }
            }
            return implementationMap;
        }
        return null;
    }
    
    /*
     * split a string into a list;
     * items separated by ";;"
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<String> splitToList (JSONObject subObject, String field) {
        String abilities = (String) subObject.get(field);
        if (abilities!=null) {
            List<String> abilitiesList = new ArrayList<String>();
            abilitiesList = new ArrayList(Arrays.asList(abilities.split(myPropertiesFileReader.getSplitPattern())));
            return abilitiesList;
        } else {
            return null;
        }
    }
}
