package gameengine.properties;



/**
 * interface for game objects that can be act as a entrance/exit to
 *  transition to another map
 *  
 * @author Tara
 *
 */
public interface Transitionable {

    /**
     * switch from old map to new map
     * @return 
     */
    String switchMap();

	int[] getNewXY();


}
