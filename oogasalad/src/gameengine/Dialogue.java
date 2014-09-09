package gameengine;

import gameplayer.ActionPanel;
import java.util.List;
import util.ErrorLogger;


public class Dialogue {

    List<String> conversationDialogue;
    ActionPanel npcCharacterDialoguePanel;

    public Dialogue (List<String> myConversationDialogue) {
        conversationDialogue = myConversationDialogue;
        ErrorLogger.writeToLog("Setting dialogue list", "info");
        init();
    }

    private void init () {
        // doesn't work.. don't want singleton.. How do we implement?
        // npcCharacterDialoguePanel = ActionPanel.getInstance();
        // ErrorLogger.writeToLog("Creating dialoguepanel", "info");
        // npcCharacterDialoguePanel.setNotification("NPC:" + conversationDialogue.get(0));

    }

}
