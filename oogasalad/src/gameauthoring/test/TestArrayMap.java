package gameauthoring.test;

import static org.junit.Assert.*;
import gameauthoring.backend.ArrayMap;

public class TestArrayMap {

	@org.junit.Test
	public void testCombine(){
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("a", "a");
		
		ArrayMap<String, String> map2 = new ArrayMap<String, String>();
		map2.put("b", "b");
		
		map.combine(map2);
		
		assertTrue(map.size()==2);
		assertTrue(map.getKeys().contains("a"));
		assertTrue(map.getKeys().contains("b"));
	}
	
	
	@org.junit.Test
	public void testGetUniqueKeys(){
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("a", "a");
		map.put("a", "b");
		map.put("b", "a");
		map.put("b", "b");
		
		assertTrue(map.getUniqueKeys().size()==2);
	}
	
	@org.junit.Test
	public void testGetValueList(){
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("a", "a");
		map.put("a", "b");
		map.put("b", "a");
		map.put("b", "b");
		
		assertTrue(map.getValueList("a").size()==2);
		assertTrue(map.getValueList("a").contains("a"));
		assertTrue(map.getValueList("a").contains("b"));
	}
	
	
}
