package gameengine.properties;

import gameengine.InventoryItem;

/**
 * interface for items that can be picked up by the player
 * @author Tara
 *
 */
public interface Pickupable {

    /**
     * player picking up an item
     */
    public InventoryItem pickUp();
    
}