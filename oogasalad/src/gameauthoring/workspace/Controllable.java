package gameauthoring.workspace;
import java.util.Map;

/**
 * This interface allows a compacted version of the controller to be passed 
 * around so as to keep the controller methods more isolated
 * @author LeeYuZhou, DanZhang
 */
public interface Controllable {

	/**
	 * This uses the parameters passed in to update the data stored in the 
	 * back-end for a certain tile/object id. 
	 * 
	 * @param type
	 * @param id
	 * @param attributes
	 */
	void updateDefinition(String type, String id, Map<String, String> attributes);

	/**
	 * Gets the data for a certain id from the back end.
	 * 
	 * @param id
	 * @return
	 */
	Map<String, String> getDefinitionData(String id);
	
	/**
	 * Gets the specific data from the back end based on the type
	 * 
	 * @param type
	 * @return
	 */
	Map<String, Map<String, String>> getDefinitionsOfType(String type);
	
	/**
	 * Generates a new long ID from the back end.
	 * 
	 * @return
	 */
	String getNewLongID();
	
	/**
	 * Generates a new short ID from the back end.
	 * 
	 * @return
	 */
	String getNewShortID();
	
	/**
	 * Publishes the current data in the back end to a JSON format. 
	 * 
	 * @param s
	 */
	void publish(String s);

}
