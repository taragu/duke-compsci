package gameengine;

import java.util.List;
import java.util.Map;

import jgame.JGObject;

public class MapItem extends GameObject {
	
	
	public MapItem (String name,
			boolean isUnique,
			double x,
			double y,
			int collisionid,
			String gfxname,
			String type,
			String uniqueID,
			Map<String, List<Object>> abilityInputMap, // "Pickable" maps to [String name, String graphic, Map<String, Object> effects]
			Map<String, String> abilityImplementationMap,
			String mapUniqueID) {
		super(name, true, x, y, collisionid, gfxname, type, uniqueID, abilityInputMap,
				abilityImplementationMap, mapUniqueID); 
		}
	
	
	@Override
	public void hit(JGObject object) {
		
			Object [] itemPickUpargs = {"itemPickUp", this};
			myEventManager.add(itemPickUpargs);
		
	}
}