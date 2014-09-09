package gameauthoring.elements;

import gameauthoring.workspace.Activable;

/**
 * CharItemEraser is an object that can be used to remove 
 * a Character or Item object that was placed on a map tile.
 * 
 *  @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class CharItemEraser extends CharItemObject {
	/**
	 * Constructor for 
	 * @param width
	 * @param height
	 * @param activable
	 */
    public CharItemEraser (int width, int height, Activable activable, String type) {
        super(width, height, activable, type);
    }

    protected void setTile (MapTile mapTile) {
        mapTile.removeObjects();
    }

}
