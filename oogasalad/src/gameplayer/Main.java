package gameplayer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import util.ErrorLogger;


public class Main {

	
	public static final int DEFAULT_HEIGHT = 608+100;
	public static final int DEFAULT_WIDTH = 608*2+100;

	
	public static void main (String[] arg0) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				String title = "Final Fantasy GamePlayer";
				String language = "English";
				new GamePlayer(title, language, DEFAULT_WIDTH, DEFAULT_HEIGHT);
				// TOGGLE ERROR LOGGER
				ErrorLogger.disableLogging();
			}
		});
	}

	




}
