package gameengine.events;
import java.util.ArrayList;

/**
 * Special type of node, holds a display string and a series of responses that are mapped to other dialogue nodes
 * @author Zanele Munyikwa
 * 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueNode {
	
	@SuppressWarnings("unused")
    private int myID;
	private String myDisplayString;
	
	private Map<String, Integer> myResponses;
	
	DialogueNode(String displayString) {
		//myID = ID;
		myDisplayString = displayString;
		myResponses = new HashMap<String, Integer>();
		
	}
	
	
	
	public void addResponse(String message, Integer nextDialogueNodeID) {
		myResponses.put(message, nextDialogueNodeID);
	}
	
	
	//given a string response, return the id of the next node 
	//CURRENTLY MUST BE A RESPONSE, no EXCEPTIONS written yet
	public Integer decodeResponse(String message) {
		return myResponses.get(message);
	}
	
	
	//given a node, list the strings that you can choose from to respond
	public List<String>getResponses() {
		List<String>responseStrings = new ArrayList<String>();
		for ( String key : myResponses.keySet() ) {
		    responseStrings.add(key);
		}
		
		return responseStrings;
		
	}
	public String getMessage() {
		return myDisplayString;
	}
	
	

}
