package gameauthoring.elements;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.PropertyUpdatable;

/**
 * Extends Inspector super class, examines tiles specifically and updates the
 * tile properties pane.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class TileInspector extends Inspector {

	/**
	 * Constructor for tile inspector.
	 * @param width
	 * @param height
	 * @param activable
	 * @param updateProperty
	 * @param type
	 */
	public TileInspector(int width, int height, Activable activable,
			PropertyUpdatable updateProperty, String type) {
		super(width, height, activable, updateProperty, type);
	}
	
	@Override
	protected void setTile(MapTile otherTile) {
		if (otherTile.hasTile()) {
			myUpdateProperty.updatePropertiesView(otherTile.myMapTileID);
		} else {
			cannotInspect();
		}
	}

}
