package gameauthoring.util;

import java.util.HashMap;
import java.util.Map;

public class DefaultCreator {
	private static Map<String, Integer> COUNTER = new HashMap<String, Integer>();
	static {
		COUNTER.put("Character", 0);
		COUNTER.put("Item", 0);
		COUNTER.put("Tile", 0);
	}
	public static String createDefaultID(String type) {
		int counter = COUNTER.get(type);
		int count = counter + 1;
		COUNTER.put(type, count);
		return type + count;
	}
}
