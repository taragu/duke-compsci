package gameengine.propertyimplementation;

import java.util.List;
import gameengine.properties.Transitionable;

/**
 * Class that implements objects that CANNOT be transitional objects
 * 
 * @author Tara
 *
 */
public class CannotTransition extends Transition implements Transitionable {

    public CannotTransition (List<Object> posAndMaps) {
        super(posAndMaps);
        
    }

    @Override
    public String switchMap(){
        //DO NOTHING
        return "";
    }
    
    
}
