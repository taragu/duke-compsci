package gameengine.events;
import java.util.ResourceBundle;
import util.Reflection;


/**
 * Factory to create events based on notification string
 * 
 * @author Zanele
 * 
 */

public class GameEventFactory {


    private ResourceBundle myEvents;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    public GameEventFactory () {
        myEvents = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "GameEvents");

    }



    public GameEvent makeEvent (Object [] args) {
        String eventType = (String) args[0];

        String className = myEvents.getString(eventType);



        GameEvent newEvent = (GameEvent) Reflection.createInstance(className, args);

        return newEvent;

    }


}
