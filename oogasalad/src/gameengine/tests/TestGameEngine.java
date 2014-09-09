package gameengine.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WARNING: this test will NOT pass because the JGEngine needs to up
 * running for the GameObjects to be created!
 * 
 * @author Tara
 * 
 */
public class TestGameEngine {

    @org.junit.Test
    public void testAbilities_Talk () {
        Map<String, List<String>> talkAbility = new HashMap<String, List<String>>();
        List<String> lines = new ArrayList<String>();
        lines.add("Hello");
        talkAbility.put("Talkable", lines);

         //make a new Character object
//        PlayerCharacter character = new PlayerCharacter("Person", false, 0,0, 1, "None",
//        talkAbility);
//        
//        character.talk();

    }

}
