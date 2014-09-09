package gameengine.events;

import gameengine.Dialogue;

import java.util.HashMap;

public class EventOutputPackage {


		HashMap<String, Boolean> altered; 
		HashMap<String, Object> info;

		EventOutputPackage() {
			altered = new HashMap<String, Boolean>();
			info = new HashMap<String, Object>();


		}


		public void setNotification(Dialogue notification ){
			altered.put("notificationDialogue", true);
			info.put("notificationDialogue", notification);

		}










}


