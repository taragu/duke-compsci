package gameengine.events;
import gameplayer.PlayerEngine;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Aggregates all events from GameObjects, executes them in order 
 * 
 * @author Zanele
 * 
 */

public class GameEventManager implements GameEventManagerInterface {
    private Queue<GameEvent> allevents;
    private GameEventFactory eventFactory;
    private PlayerEngine myEngine;


    public GameEventManager (PlayerEngine thisEngine) {
        allevents = new LinkedList<GameEvent>();
        eventFactory = new GameEventFactory();
        myEngine = thisEngine;
    }


    public GameEvent next () {
        GameEvent nextEvent = allevents.poll();
        return nextEvent;

    }

    //execute next event in queue
    public int doNext () {
        GameEvent nextEvent = allevents.poll();
        if (nextEvent == null) {
            return 0;
        }
        else {
            String result = nextEvent.execute(myEngine);
            myEngine.getController().updateActionPanelDialogue(result);
            return 1;
        }



    }

    /* (non-Javadoc)
     * @see gameengine.events.GameEventManagerInterface#add(gameengine.events.GameEvent)
     */
    @Override
    public void add (Object[] args) {
        String eventType = (String) args[0];



        GameEvent newEvent = eventFactory.makeEvent(args);

        allevents.offer(newEvent);
    }



    //execute every event in queue
    public void runAllEvents () {

        while (!allevents.isEmpty()) {
            int checkSuccess = doNext();
            if (checkSuccess == 0) {
                System.out.println("Event was null!");
            }
        }

    }



}
