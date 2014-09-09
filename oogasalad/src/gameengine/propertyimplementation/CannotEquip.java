package gameengine.propertyimplementation;

import gameengine.properties.Equipable;
import java.util.List;

/**
 * Class that implements Equipable; game objects that instantiate this class cannot be equiped
 * @author Tara
 *
 */

public class CannotEquip extends Equip implements Equipable {

    public CannotEquip (List<Object> input) {
        super(input);

        
    }
    
    @Override
    public void equipItem() {
        //DO NOTHING
        
    }

}
