package gameengine.properties;

import gameengine.events.DialogueTree;

/**
 * Interface for characters/objects that can talk using user pre-written dialog
 * 
 * @author Tara
 * 
 */

public interface Talkable {

    /**
     * call this method to let the character speak its line
     * 
     */
    public DialogueTree talk ();

}
