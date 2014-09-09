package gameauthoring.elements;

import gameauthoring.workspace.Activable;

/**
 * A game object that can be placed on the map. This is created
 * in the Game Object Tab
 * @author LeeYuZhou, DanZhang
 *
 */

@SuppressWarnings("serial")
public class CharItemObject extends GameObject {
	/**
	 * Constructor for a CharItemObject with an image given a filePath of the image
	 * @param width
	 * @param height
	 * @param filePath
	 * @param id
	 * @param activable
	 * @param type 
	 */
	public CharItemObject(final int width, final int height, String filePath, String id, Activable activable, String type) {
		super(width, height, filePath, id, activable, type);
	}
	
	/**
	 * Constructor for a CharItemObject that does not have an image to represent it 
	 * @param width
	 * @param height
	 * @param activable
	 * @param type 
	 */
	public CharItemObject(int width, int height, Activable activable, String type) {
		super(width, height, activable, type);
	}
	
	
	protected void setTile(MapTile mapTile) {
		mapTile.addObject(image, myObjectID, myType);
	}
	

}
