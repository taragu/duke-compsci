package gameauthoring.workspace;

import java.util.*;

import net.miginfocom.swing.MigLayout;

/**
 * Tab that contains the information regarding the different data for each tile,
 * character or object. Allows the user to update the data as well. 
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class PropertiesTab extends Tab {

	public static final String ABILITY_PANEL_PATH = "AbilityPanel";
	public static final String SWING_BASE_PATH = "javax.swing.";
	private String myType;
	private Controllable myController; 
	List<String> listOfDefinedAbilities;
	
	private List<GameObjectPanel> listOfPanels;
	/**
	 * A more robust constructor for the propertiesTab
	 * @param language
	 * @param type
	 * @param controller
	 */
	public PropertiesTab(String language, String type, Controllable controller) {
		super(language);
		mainPanel.setLayout(new MigLayout("insets 0, wrap 1", "[grow,fill]"));
		myType = type;
		myController = controller;
		listOfPanels = new ArrayList<GameObjectPanel>();
	}
	
	protected void updateView(String id) {
		List<String> listOfIDs = new ArrayList<String>();
		listOfIDs.add(id);
		updateView(listOfIDs);
	}
	
	protected void updateView(List<String> listOfIDs) {
		listOfPanels.clear();
		mainPanel.removeAll();
		for (String id: listOfIDs) {
			GameObjectPanel gOP = new GameObjectProperties(myDefaultLanguage, myType, myController);
			mainPanel.add(gOP);
			gOP.updateView(id);
			listOfPanels.add(gOP);
		}
		mainPanel.revalidate();
		mainPanel.repaint();
		refreshView();
	}
	
	


}
