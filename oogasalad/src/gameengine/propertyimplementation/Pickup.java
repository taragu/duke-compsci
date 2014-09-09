package gameengine.propertyimplementation;

import java.util.List;
import gameengine.InventoryItem;
import gameengine.properties.Pickupable;

/**
 * Class that implements Pickable
 * @author Tara
 * 
 * 
 * @param strength
 * @param defence
 * @param agility
 * @param health
 * @param experiencelvl
 */
public class Pickup implements Pickupable {

	private String myName;
	private int[] myPowers = new int[5];
	private String myType;
	

	public Pickup(List<Object> inputs) {
	    if (inputs.size()!=0) {
	        myName = (String) inputs.get(0);
	        System.out.println("IN PICKUP CONSTRUCTOR" + myName);

	        for (int i=1; i < 6; i++) {
	            myPowers[i-1] = Integer.parseInt((String) inputs.get(i));
	        }

	        myType = (String) inputs.get(6);
	    }

	}

	@Override
	public InventoryItem pickUp () {
		return new InventoryItem(myName, myPowers, myType);
	}

}