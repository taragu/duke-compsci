package gameengine.tests;

import java.util.ArrayList;
import java.util.List;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameDataLibrary;
import gameengine.propertyimplementation.Talk;


public class TestPropertyLibrary implements DataConstants {

    @org.junit.Test
    public void test_createPropertyObjects () {

        GameDataLibrary.init();
        List<Object> dialogue = new ArrayList<Object>();
        dialogue.add("Hello!");
        dialogue.add("Bye!");

        Talk newTalkObject = (Talk) GameDataLibrary.createPropertyObject(TALKABLE, TALK, dialogue);
        newTalkObject.talk();

    }
}
