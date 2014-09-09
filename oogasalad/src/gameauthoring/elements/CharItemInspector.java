package gameauthoring.elements;

import gameauthoring.workspace.Activable;
import gameauthoring.workspace.PropertyUpdatable;

/**
 * Inspector for Characters an Items. 
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public class CharItemInspector extends Inspector {
	/**
	 * Constructor for an inspector at inspects properties of a Character/Item
	 * on a tile
	 * @param width
	 * @param height
	 * @param activable
	 * @param updateProperty
	 * @param type
	 */
	public CharItemInspector(int width, int height, Activable activable,
			PropertyUpdatable updateProperty, String type) {
		super(width, height, activable, updateProperty, type);
	}

	@Override
	protected void setTile(MapTile otherTile) {
		if (otherTile.hasObject() && myType.equals(otherTile.getObjectType())) {
			myUpdateProperty.updatePropertiesView(otherTile.myObjectIDs);
		} else {
			cannotInspect();
		}
	}

}
