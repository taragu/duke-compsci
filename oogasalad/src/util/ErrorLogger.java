package util;

import java.lang.reflect.Method;
import java.util.logging.Logger;


/**
 * Log or display error messages for try catch or assert statements
 * 
 * @author Tara
 * 
 */

public class ErrorLogger {

    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    private static boolean myLoggerBoolean = true;

    /**
     * write error message to log
     * 
     * @param message: error to display
     * @param level: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
     */

    public static void writeToLog (String message, String level) {
        if (myLoggerBoolean) {
            Method method;
            try {
                method = LOGGER.getClass().getDeclaredMethod(level, String.class);
                method.invoke(LOGGER, message);
            }
            catch (Exception e) {
                LOGGER.severe("Error in writeToLog: reflection failed");
            }
        }
    }

    /**
     * Disable logging; has to call enableLogging() to enable logging again
     */
    public static void disableLogging () {
        myLoggerBoolean = false;

    }

    /**
     * Enable logging (after it is disabled)
     */
    public static void enableLogging () {
        myLoggerBoolean = true;

    }

    /**
     * For player: display the error message to player when playing the game
     */
    public static void display (String message) {
        

    }
}
