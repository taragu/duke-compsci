package gameauthoring.inputfields;

/**
 * An input interface that makes getting and setting properties values very easy
 *
 */
public interface GAInput {
	/**
	 * Gets the value for a specific input field
	 * @return
	 */
	String getValue();
	
	/**
	 * Sets the value of the specific input field 
	 * @param input
	 */
	void setValue(String input);
	
	/**
	 * Makes the input field uneditable by the user, but can be updated from 
	 * backend
	 */
	void setUneditable();
	
	/**
	 * Checks whether or not the field can be edited
	 * @return
	 */
	boolean isEditable();
}
