package gameauthoring.elements;


import gameauthoring.error.ValueNotDefinedException;
import gameauthoring.inputfields.GAInput;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.EventUpdatable;

/**
 * An inspector specifically used by the events manager class.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class EventInspector extends Inspector {
	private EventUpdatable myUpdateEvents;
	private GAInput myComponent;
	
	/**
	 * Constructor for the event inspector that allows the user to inspect events that were created previously
	 * @param activable
	 * @param updateEvents
	 * @param type
	 */
	public EventInspector(Activable activable, EventUpdatable updateEvents, String type) {
		super(activable, type);
		myUpdateEvents = updateEvents;

	}
	/**
	 * Constructor for the event inspector that allows the user to inspect events that were created previously
	 * @param activable
	 * @param updateEvents
	 * @param type
	 * @param input
	 */
	public EventInspector(Activable activable, EventUpdatable updateEvents, String type, GAInput input) {
		this(activable, updateEvents, type);
		myComponent = input;
	}

	@Override
	protected void setTile(MapTile otherTile) {
		if (otherTile.hasTile()) {

			try {
				myUpdateEvents.updateEventView(otherTile, myComponent, myType);
			} catch (ValueNotDefinedException e) {
				e.printStackTrace();
			}

		} else {
			cannotInspect();
		}

	}



}
