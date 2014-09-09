package gameengine.events;

import gameengine.GameObject;
import gameengine.NonPlayerCharacter;


public abstract class NPCGameEvent implements GameEvent {

    NonPlayerCharacter thisNPC;

   public NPCGameEvent (String eventType, GameObject thisNPC) {
    	 this.thisNPC = (NonPlayerCharacter) thisNPC;

    }
   
   
  

    
    //public abstract String execute ();

}
