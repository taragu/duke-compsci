package gameauthoring.workspace;

import java.util.List;

/**
 * Interface that allows other aspects of the GUI to update the properties tab
 *
 * @author LeeYuZhou, DanZhang
 */
public interface PropertyUpdatable {

	/**
	 * Updates the information of the id that is passed in. 
	 * @param id
	 */
	void updatePropertiesView(String id);
	
	/**
	 * Updates the information of the list of ids that is passed in. 
	 * @param id
	 */
	void updatePropertiesView(List<String> listOfIDs);
}
