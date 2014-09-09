package gameengine.events;

import gameengine.GameObject;

/**
 * Abstract class defining all events related to items
 * 
 * @author Zanele
 * 
 */
 
public abstract class ItemGameEvent implements GameEvent {

    GameObject thisItem;

    ItemGameEvent (String eventType,GameObject myItem) {
    	thisItem = myItem;

    }

}
