package gameengine.propertyimplementation;

import java.util.List;

import gameengine.events.DialogueTree;
import gameengine.properties.Talkable;


/**
 * One implementation of Talkable; the player character or npc won't be able to talk
 * 
 * @author Tara
 * 
 */

public class CannotTalk extends Talk implements Talkable {

    public CannotTalk (List<Object> conversationDialogue) {
        super(conversationDialogue);

    }

    @Override
    public DialogueTree talk () {
		return null;
        // do nothing

    }

}
