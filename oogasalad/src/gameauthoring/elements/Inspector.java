package gameauthoring.elements;

import gameauthoring.error.GAError;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.PropertyUpdatable;

/**
 * Inspector superclass
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public abstract class Inspector extends GameObject{

	protected PropertyUpdatable myUpdateProperty;
	/**
	 * Constructors for an inspector
	 * @param width
	 * @param height
	 * @param activable
	 * @param updateProperty
	 * @param type
	 */
	public Inspector(int width, int height, Activable activable, PropertyUpdatable updateProperty, String type) {
		super(width, height, activable, type);
		myUpdateProperty = updateProperty;
	}
	
	public Inspector(Activable activable, String type) {
		super(0,0,activable, type);
	}
	
	protected void cannotInspect() {
		GAError.displayError("No " + myType + " present!");
	}
}
