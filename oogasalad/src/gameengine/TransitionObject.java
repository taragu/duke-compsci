package gameengine;

import java.util.List;
import java.util.Map;

import jgame.JGObject;
import util.Reflection;
import gameengine.gamedata.DataConstants;
import gameengine.properties.DoorLike;
import gameengine.properties.Transitionable;

/**
 * Objects that is responsible for transition between game states
 *  such as an entrance/exit of a map
 *  
 * @author Tara
 *
 */
public class TransitionObject extends GameObject implements DoorLike {

    
    
    
    
    public TransitionObject (String name,
                             boolean isUnique,
                             double x,
                             double y,
                             int collisionid,
                             String gfxname,
                             String type,
                             String uniqueID,
                             Map<String, List<Object>> propertyInputMap,
                             Map<String, String> propertyImplementationMap,
                             String mapUniqueID) {
        super(name, isUnique, x, y, collisionid, gfxname, type, uniqueID, propertyInputMap,
              propertyImplementationMap, mapUniqueID);
        
    }

    /**
     * call the specific implementation of Transitionable
     */
    @Override
    public String switchMap () {
     // use reflection to instantiate 
        List<Object> inputArgs = myPropertyInputMap.get(DataConstants.TRANSITIONABLE);

        Transitionable transition = (Transitionable) Reflection.createInstance
                (IMPLEMENTATION_PACKAGE_LOCATION+ 
                 myPropertyImplementationMap.get(DataConstants.TRANSITIONABLE), inputArgs);
        return transition.switchMap();
        
    }

	@Override
	public int[] getNewXY() {
		 List<Object> inputArgs = myPropertyInputMap.get(DataConstants.TRANSITIONABLE);

	        Transitionable transition = (Transitionable) Reflection.createInstance
	                (IMPLEMENTATION_PACKAGE_LOCATION+ 
	                 myPropertyImplementationMap.get(DataConstants.TRANSITIONABLE), inputArgs);
	        return transition.getNewXY();
	}
    
	
	@Override 
	public void hit(JGObject object) {
		System.out.println("HIT A TRANSITION OBJECT");
	
		Object [] args = {"transition", this};
		myEventManager.add(args);
	}
}
