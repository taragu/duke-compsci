package gameengine;

import java.util.Map;
import util.ErrorLogger;
import util.Reflection;


/**
 * Set and store user-defined controls
 * 
 * @author Tara
 * 
 */

public class Control {

    /* user defined keys */

    private static int myKeyUp;
    private static int myKeyDown;
    private static int myKeyLeft;
    private static int myKeyRight;

    /**
     * Constructor for Control class
     * 
     * @param keyMap
     */
    public Control (Map<String, Integer> keyMap) {
        myKeyUp = jgame.impl.JGEngineInterface.KeyUp;
        myKeyDown = jgame.impl.JGEngineInterface.KeyDown;
        myKeyLeft = jgame.impl.JGEngineInterface.KeyLeft;
        myKeyRight = jgame.impl.JGEngineInterface.KeyRight;
        ErrorLogger.writeToLog("Control constructor: keys are set to " + myKeyUp + ", " +
                               myKeyDown + ", etc", "info");

        // sets the user defined keys at the same time
        if (keyMap != null) {
            setKeys(keyMap);
        }

    }

    private void setKeys (Map<String, Integer> keyMap) {
        for (String thisKey : keyMap.keySet()) {
            Reflection.callMethod(this, thisKey, keyMap.get(thisKey));

        }
    }

    public static int getKeyUp () {
        return myKeyUp;
    }

    public static int getKeyDown () {
        return myKeyDown;
    }

    public static int getKeyLeft () {
        return myKeyLeft;
    }

    public static int getKeyRight () {
        return myKeyRight;
    }


}
