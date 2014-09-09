package gameengine.events;

/**
 * Tree that manages dialogue with user to allow you to traverse a series of Dialogue Nodes
 * @author Zanele Munyikwa
 */
import java.util.List;

public class DialogueTree {
	
	DialogueNode [] allDialogueNodes;
	DialogueNode root;
	int nodeCount;
	

	public DialogueTree(List<String> dialogueStrings) {
		allDialogueNodes = new DialogueNode [dialogueStrings.size()];
		nodeCount = 0;
		root = buildTree(dialogueStrings);
	}
	
	
	
	
	//take in correctly formatted string, return a Dialogue Node
	
	public DialogueNode parseString(String dialogueString) {
		String[] allStrings = dialogueString.split("::");
		
		
		String message = (String) allStrings[0];
		String[] responses = java.util.Arrays.copyOfRange(allStrings, 1, allStrings.length);
		
		
		DialogueNode newNode = createNode(message, responses);
		
		return newNode;
		
	}

	
	//use a message and an array of string responses to create a new DialogueNode
	private DialogueNode createNode(String message, String[] responses) {
		DialogueNode newNode = new DialogueNode(message);
		
		
		for (String response: responses) {
			
			String [] splitResponse = response.split(":");
			
			String responseMessage = splitResponse[0];
			
			if (splitResponse.length > 1) {
				
				int nextNodeID = Integer.parseInt(response.split(":")[1]);

				newNode.addResponse(responseMessage, nextNodeID);
			} else {		
				newNode.addResponse(responseMessage, -1);
				
			}
				
		}
		return newNode;
	}
	
	
	
	//build a tree using a List of Strings
	public DialogueNode buildTree(List<String> dialogueStrings) {
		
		for (int i = 0; i<dialogueStrings.size(); i++) {
			
			
			allDialogueNodes[i] = parseString(dialogueStrings.get(i));
		}
		
		return allDialogueNodes[0];
	}
	
	
	
	
	@SuppressWarnings("unused")
    private DialogueNode getNode(Integer currentNodeID) {
		return allDialogueNodes[currentNodeID];
	} 
	
	
	
	//Given a string response and a dialogueNode, return the next Node in the conversation
	public DialogueNode processResponse(String response,DialogueNode currentNode){
		if (response.equals("I don't want to talk to you anymore.")) {
			return null;
		}
		if (currentNode.decodeResponse(response) == -1) {
			return null;
		}
		return allDialogueNodes[currentNode.decodeResponse(response)];
			
	}
	
	
	//Return the root of the dialogue tree, or the first dialogue that should be displayed
	public DialogueNode getRoot() {
		return root;
	}

}
