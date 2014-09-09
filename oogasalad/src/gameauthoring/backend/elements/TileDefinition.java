package gameauthoring.backend.elements;

import java.util.Map;

public class TileDefinition extends GenericDefinition{

	public TileDefinition(String type, Map<String, String> attributes) {
		super(type, attributes);
	}

	@Override
	protected void setPublishType() {
		myPublishType = "Tiles";
	}
}
