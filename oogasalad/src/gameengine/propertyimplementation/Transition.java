package gameengine.propertyimplementation;

import java.util.List;
import gameengine.properties.Transitionable;

/**
 * Class that implements the transition to another map
 * 
 * @author Tara
 *
 */
public class Transition implements Transitionable {
    
    
    /*
     * new x position after going to a new map
     */
    private int myX2;
    
    /*
     * new y position after going to a new map
     */
    private int myY2;
    
    /*
     * unique id of the new map
     */
    private String myNewMapID;
    
    
    /**
     * create a Transition object: posAndMaps is a list of:
     * initial x position,
     * initial y position,
     * new x position after going to the new map,
     * new y position after going to the new map,
     * old map unique id,
     * new map unique id
     * 
     * @param posAndMaps
     */
    public Transition(List<Object> posAndMaps) {
        if (posAndMaps.size()!=0) {
            myX2 = Integer.parseInt((String) posAndMaps.get(2));
            myY2 = Integer.parseInt((String) posAndMaps.get(3));
            myNewMapID = (String) posAndMaps.get(5);
//            System.out.println("&&&&&&&ÖTransition: x1 is "+myX1+", y1 is "+myY1+", y1");
        }
    }
    
    
    @Override
    public String switchMap () {
        return myNewMapID;
        
    }
    
    @Override
    public int[] getNewXY () {
    	int[] coordinate = {myX2,myY2};
    	return coordinate;
    }

}
