package gameauthoring.backend.elements;

import java.util.Map;

public class ItemDefinition extends GenericDefinition{
	
	public ItemDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
	}

	@Override
	protected void compressAttributes(Map<String, String> attributes, String splitter) {
		super.compressAttributes(attributes, splitter);
		String ability = attributes.get("abilities");
		String[] abilityDef = attributes.get(ability).split(splitter);
		StringBuilder compressedDef = new StringBuilder();
		for (int i = 0; i < abilityDef.length; i += 2) {
			compressedDef.append(abilityDef[i] + splitter);
		}
		compressedDef.append(ability);
		attributes.remove(ability);
		attributes.put("abilities", "Pickupable");
		attributes.put("Pickupable", compressedDef.toString());
	}
}
