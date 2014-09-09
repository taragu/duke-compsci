package gameengine.propertyimplementation;

import java.util.List;

import gameengine.InventoryItem;
import gameengine.properties.Pickupable;

/**
 * class that implements Pickupable; the object cannot be picked up
 * @author Tara
 *
 */
public class CannotPickup extends Pickup implements Pickupable {

    public CannotPickup (List<Object> inputs) {
        super(inputs);
        
    }
    
    @Override
    public InventoryItem pickUp() {
        return null;  
    }

}
