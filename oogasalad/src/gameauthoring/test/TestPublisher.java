package gameauthoring.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gameauthoring.backend.ArrayMap;
import gameauthoring.backend.Publisher;
import gameauthoring.backend.elements.ObjectDefinition;

import org.junit.Test;


/**
 * Test for the Publisher.
 * Creates its own map of ObjectDefinitions
 * 
 * @author Amy
 * 
 */
public class TestPublisher {

    private Publisher myPublisher = new Publisher();

    @Test
    public void testPublishJSON () {
        ArrayMap<String, Map<String, String>> infoMap = createObjectMap();
        String JSON =
                "{\"objects\":[{\"uniqueid\":\"2\",\"name\":\"Misty\"},{\"uniqueid\":\"1\",\"name\":\"Ash\"}]}";
        myPublisher.addToJSON("objects", infoMap);
        myPublisher.publishJSON("foo.json");
        
		try {
			BufferedReader f = new BufferedReader(new FileReader("foo.json"));
			String line = f.readLine();
			System.out.println(line);
	        assertTrue(line.toString().equals(JSON));
	        f.close();
	        File g = new File("foo.json");
	        g.delete();
		} catch (Exception e){} 
		
        
    }

    private ArrayMap<String, Map<String, String>> createObjectMap () {
        ArrayMap<String, Map<String, String>> map = new ArrayMap<String, Map<String, String>>();
        // One object
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("name", "Ash");
        attributes.put("uniqueid", "1");
        map.put("1", attributes);
        // Two object
        Map<String, String> attributes2 = new HashMap<String, String>();
        attributes2.put("name", "Misty");
        attributes2.put("uniqueid", "2");
        map.put("2", attributes2);

        return map;
    }
}
