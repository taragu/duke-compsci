package gameauthoring.workspace;

import gameauthoring.elements.GameObject;

/**
 * This interface provides a way for the game authoring to pass around the
 * setActiveTile/getActiveTile methods, rather than an entire controller.
 * @author LeeYuZhou, DanZhang
 *
 */
public interface Activable {
	/**
	 * This sets the passed in tile as the current active tile. This is good
	 * for inspector/eraser purposes, as well as painting the map.
	 * 
	 * @param tile
	 */
    void setActiveTile (GameObject tile);
    
    
    /**
     * This gets the current active tile. 
     * @return GameObject
     */
    GameObject getActiveTile ();
}
