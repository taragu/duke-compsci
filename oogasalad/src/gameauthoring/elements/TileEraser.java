package gameauthoring.elements;

import gameauthoring.workspace.Activable;

/**
 * Eraser specifically for tile objects
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class TileEraser extends TileObject {
	
	/**
	 * Constructor for a tile eraser
	 * 
	 * @param width
	 * @param height
	 * @param activable
	 * @param type
	 */
    public TileEraser (int width, int height, Activable activable, String type) {
        super(width, height, activable, type);
    }

    protected void setTile (MapTile otherTile) {
        otherTile.removeMapImage();
    }

}
