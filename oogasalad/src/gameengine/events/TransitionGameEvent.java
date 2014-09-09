package gameengine.events;

import javax.swing.JOptionPane;
import gameengine.TransitionObject;
import gameengine.gamedata.GameMap;
import gameplayer.PlayerEngine;

public class TransitionGameEvent implements GameEvent {
	
	
	TransitionObject transitionSource;
	
	
	public TransitionGameEvent(String eventType, TransitionObject source) {
		transitionSource = source;
		
		
	}


	public String execute(PlayerEngine eventEngine) {
		System.out.println("I AM TRANSITIONING HERE");
		String mapID = transitionSource.switchMap();
		GameMap newMap = eventEngine.getMaps(mapID);
		if(newMap.getSubscriptionBoolean()){
			String input = JOptionPane.showInputDialog(null, "Enter Subscription Key:", "LOCKED AREA", JOptionPane.QUESTION_MESSAGE);
		    if(input.equals(newMap.getCode())){
		    	newMap.turnOffSubscription();
		    }
		    else return "access not allowed";
		}
		eventEngine.moveParty(transitionSource.getNewXY());
		eventEngine.setGameStateMap(mapID);
		return "Switched to " + newMap.getName();

	}

	

}
