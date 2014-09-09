package gameauthoring.backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * This class takes in a map ObjectDef objects and publishes the information to a data file.
 * 
 * @author Amy
 *         
 * 
 */

public class Publisher {

    private JSONObject myJSON = new JSONObject();

    @SuppressWarnings("unchecked")
	public void addToJSON (String tag, ArrayMap<String, Map<String, String>> valueMap) {
        // Writing the objects
        JSONArray list = new JSONArray();
        myJSON.put(tag, writeToJSONArray(list, valueMap));
    }
    
    public void publishJSON(String location) {
        // Create the JSON
        try {
            File f = new File(location);
            FileWriter file = new FileWriter(f);
            file.write(myJSON.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myJSON.clear();
    }

    @SuppressWarnings("unchecked")
	private JSONArray writeToJSONArray (JSONArray array, ArrayMap<String, Map<String, String>> map) {
        JSONArray list = array;
        for (String key : map.getUniqueKeys()) {
            List<Map<String, String>> attributeSet = map.getValueList(key);
            for (Map<String, String> attribute: attributeSet){
            	JSONObject oneObject = new JSONObject();
            	
            	for (String attributeKey : attribute.keySet()) {
                    oneObject.put(attributeKey, attribute.get(attributeKey));
                }
                list.add(oneObject);	
            }
        }
        return list;
    }
}
