package gameauthoring.elements;

import gameauthoring.workspace.Activable;

/**
 * The tiles that are placed within the map tiles. Refer to the areas on which
 * a player can walk (or not walk). 
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class TileObject extends GameObject {

	/**
	 * Constructors for tile objects.
	 * @param width
	 * @param height
	 * @param filePath
	 * @param id
	 * @param active
	 * @param type
	 */
	public TileObject(final int width, final int height, String filePath, String id, final Activable active, String type) {
		super( width, height, filePath, id, active, type);
	}

	public TileObject(final int width, final int height, final Activable activable, String type) {
		super(width, height, activable, type);
	}
	
	protected void setTile(MapTile otherTile) {
		otherTile.setMapImage(image, myObjectID, imageFilePath);
	}

}
