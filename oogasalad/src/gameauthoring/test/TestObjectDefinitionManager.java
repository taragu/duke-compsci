package gameauthoring.test;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gameauthoring.backend.ObjectDefinitionFactory;
import gameauthoring.backend.ObjectDefinitionManager;
import gameauthoring.backend.elements.ObjectDefinition;
import gameauthoring.util.HashGenerator;


public class TestObjectDefinitionManager {

    private ObjectDefinitionManager myObjectDefinitionManager = new ObjectDefinitionManager();
    private ObjectDefinitionFactory sampleObjectFactory = new ObjectDefinitionFactory();
    private Map<String, String> sampleAttributes = new HashMap<String, String>();
    private Map<String, String> sampleAttributes1 = new HashMap<String, String>();
    private Map<String, String> sampleAttributes2 = new HashMap<String, String>();
    private Map<String, String> sampleAttributes3 = new HashMap<String, String>();
    private Map<String, Map<String, String>> mapofAttributes;

    @org.junit.Test
    public void testUpdateDefinition () {

        sampleAttributes.put("name", "pikachu");
        sampleAttributes.put("id", "001");
        sampleAttributes.put("special", "lightning");
        sampleAttributes.put("color", "yellow");

        myObjectDefinitionManager.updateDefinition("pokemontype", "0001", sampleAttributes);

        // System.out.println(myObjectDefinitionManager.getDefinitionData("0001"));
        assertTrue(myObjectDefinitionManager.getDefinitionData("0001") != null);
        assertNull(myObjectDefinitionManager.getDefinitionData(""));

        sampleAttributes.put("name", "squirtle");
        sampleAttributes.put("id", "002");
        sampleAttributes.put("special", "water");
        sampleAttributes.put("color", "blue");

        myObjectDefinitionManager.updateDefinition("pokemontype", "0001", sampleAttributes);

        // System.out.println(myObjectDefinitionManager.getDefinitionData("0001"));
        assertEquals("squirtle", myObjectDefinitionManager.getDefinitionData("0001").get("name"));
        assertFalse("pikachu".equals(myObjectDefinitionManager.getDefinitionData("0001")
                .get("name")));
    }

    @org.junit.Test
    public void testgetDefinitionOfType () {

        sampleAttributes1.put("name", "pikachu");
        sampleAttributes1.put("id", "001");
        sampleAttributes1.put("special", "lightning");
        sampleAttributes1.put("color", "yellow");
        myObjectDefinitionManager.updateDefinition("pokemontype", "0001", sampleAttributes1);

        sampleAttributes2.put("name", "squirtle");
        sampleAttributes2.put("id", "002");
        sampleAttributes2.put("special", "water");
        sampleAttributes2.put("color", "blue");
        myObjectDefinitionManager.updateDefinition("pokemontype", "0002", sampleAttributes2);

        sampleAttributes3.put("name", "ash ketchum");
        sampleAttributes3.put("id", "003");
        sampleAttributes3.put("special", "human");
        sampleAttributes3.put("color", "human color");
        myObjectDefinitionManager.updateDefinition("trainertype", "0003", sampleAttributes3);

        mapofAttributes = myObjectDefinitionManager.getDefinitionOfType("pokemontype");

        assertEquals(sampleAttributes1, mapofAttributes.get("0001"));
        assertEquals(sampleAttributes2, mapofAttributes.get("0002"));
        assertNull(mapofAttributes.get("0003"));
    }

    @org.junit.Test
    public void testgetDefinitionData () {

        sampleAttributes.put("name", "pikachu");
        sampleAttributes.put("id", "001");
        sampleAttributes.put("special", "lightning");
        sampleAttributes.put("color", "yellow");
        myObjectDefinitionManager.updateDefinition("pokemontype", "0001", sampleAttributes);

        assertEquals(myObjectDefinitionManager.getDefinitionData("0001"), sampleAttributes);
    }
    
}
