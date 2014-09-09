package gameengine.tests;

import util.ErrorLogger;


public class TestErrorLogging {

    @org.junit.Test
    public void test_writeToLog () {

        ErrorLogger.writeToLog("Testing writeToLog", "info");

    }

}
