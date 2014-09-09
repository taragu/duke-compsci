package gameauthoring.workspace;

import gameauthoring.elements.MapTile;
import gameauthoring.error.ValueNotDefinedException;
import gameauthoring.inputfields.GAInput;

/**
 * An interface that allows the user to update the event manager view.
 * @author LeeYuZhou, DanZhang
 *
 */
public interface EventUpdatable {

	/**
	 * Method that is called by the inspector to update the event view
	 * @param tile
	 * @param component
	 * @param type
	 * @throws ValueNotDefinedException
	 */
	void updateEventView(MapTile tile, GAInput component, String type) throws ValueNotDefinedException;

}
