package mastercode;

import gameplayer.PlayerEngine;
import java.util.List;
import java.util.Map;

/**
 * Interfact of GameObjectManager to restrict access
 * @author Tara
 *
 */
public interface GameObjectManagerInterface {

    /**
     * set the properties/abilities of game objects such as Talkable
     * @param type
     * @param propertyInputMap
     * @param propertyImplementationMap
     * @return
     */
    public List<Object> enable (String type, Map<String, List<Object>> propertyInputMap,
                                Map<String, String> propertyImplementationMap);

    /**
     * Set the engine
     * @param playerEngine
     */
    public void setPlayerCharacterDisplay (PlayerEngine playerEngine);
    
                                
}
