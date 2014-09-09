package gameengine.propertyimplementation;

import gameengine.events.DialogueTree;
import gameengine.properties.Talkable;
import java.util.ArrayList;
import java.util.List;


/**
 * One implementation of Talkable; talk normally line by line
 * 
 * @author Tara
 * 
 */

public class Talk implements Talkable {

    private List<String> myConversationDialogue;

    /**
     * Create a Talk object (an implementation of Talkable interface)
     * 
     * @param conversationDialogue
     */
    public Talk (List<Object> conversationDialogue) {
        if (conversationDialogue.size()!=0) {
            myConversationDialogue = new ArrayList<String>();
            String wholeDialogueBeforeSplit = (String) conversationDialogue.get(0);
            String [] splitConversationDialogue = wholeDialogueBeforeSplit.split("@@");
            
          
            for (String dialogue : splitConversationDialogue) {
            	System.out.println(dialogue);
                
                myConversationDialogue.add(dialogue);
            }
        }

    }

    @Override
    public DialogueTree talk () {
    	
    	DialogueTree myDialogueTree = new DialogueTree(myConversationDialogue);
    	
		return myDialogueTree;

    }
    
    
    

    /*
     * IMPLEMENT THIS METHOD EVERYTIME WE ADD A NEW IMPLEMENTATION OF AN CHARACTER ABILITY
     */
    public String toString () {
        return getClass().getName();

    }

}
