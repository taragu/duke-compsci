package gameengine.state;


/** 
 * Abstract class which defines TimeDecorator's functions
 * Implements the GameStateInterface because it decorates it
 * @author Zanele Munyikwa & Tara Gu 
 *
 */

public abstract class StateDecorator implements MapStateInterface {

	MapState currentMapState;
	
}
