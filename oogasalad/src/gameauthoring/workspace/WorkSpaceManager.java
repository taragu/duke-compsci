package gameauthoring.workspace;

import gameauthoring.backend.Controller;
import gameauthoring.elements.GameObject;
import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;
import gameauthoring.util.Bundles;
import gameauthoring.util.DefineValuePopup;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.util.HTMLViewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicButtonUI;


/**
 * The main work space that creates all the different panes and other aspects
 * of the GUI that are used to make games.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class WorkSpaceManager extends Manager implements Controllable, Activable, InspectorLike {
	/**
	 * 
	 */
	//public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String BASE_TITLE = "Final Fantasy Game Maker";
	private static final String CHARACTER = "Character";
	private static final String ITEM = "Item";
	private static final String TILE = "Tile";
	private static final String METADATA = "Metadata";
	private ObjectManager tileManager;
	private ObjectManager characterManager;
	private ObjectManager itemManager;
	private EventManager eventManager;
	private Controller myController; 
	@SuppressWarnings("unused")
	private String myGameName;
	private GameObject activeTile;
	private List<MapTab> mapTabList;
	private Map<String, ObjectManager> managerMap = new HashMap<String, ObjectManager>();
	private static final int OBJECT_MANAGER_SIZE = 400;
	private ResourceBundle gameOptions;
	/**
	 * Constructor for the Work, which is the main view where the game authoring environment is run from. It contains
	 * tabs as well as the controller
	 * 
	 * @param {String} title
	 * @param {String} language
	 * @param {int} width
	 * @param {height} height
	 */
	public WorkSpaceManager (String language, int width, int height) {
		super(BASE_TITLE, language, width, height);
		myController = new Controller();
		gameOptions = Bundles.getGameOption();
		createGameOptions();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapTabList = new ArrayList<MapTab>();
		tileManager = new ObjectManager(language, OBJECT_MANAGER_SIZE, OBJECT_MANAGER_SIZE, WorkSpaceManager.this,
				TILE);
		characterManager = new ObjectManager(language, OBJECT_MANAGER_SIZE, OBJECT_MANAGER_SIZE, WorkSpaceManager.this,
				CHARACTER);
		itemManager = new ObjectManager(language, OBJECT_MANAGER_SIZE, OBJECT_MANAGER_SIZE, WorkSpaceManager.this,
				ITEM);
		eventManager = new EventManager(language, 400, 480, (Activable) WorkSpaceManager.this);
		managerMap.put(TILE, tileManager);
		managerMap.put(CHARACTER, characterManager);
		managerMap.put(ITEM, itemManager);
	}

	public void createMapTab() {
		String query = myLanguage.getString("NameMapQuery");
		try {
			String[] mapOptions = gameOptions.getString("MapOptions").split(GameObjectPanel.DELIMITER);
			DefineValuePopup createMap = new DefineValuePopup(query, new ArrayList<String>(
					Arrays.asList(mapOptions)));
			if (createMap.isDefined()) {
				String rowKey = gameOptions.getString("NumRows");
				String colKey = gameOptions.getString("NumCols");
				String nameKey = gameOptions.getString("MapName");
	 			int rowCount = Integer.parseInt(createMap.get(rowKey));
				int colCount = Integer.parseInt(createMap.get(colKey));
				createMapTab(createMap.get(nameKey), rowCount, colCount);
			}
		} catch (ValueNotDefinedException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Creates a tab within the tab container, initializing a new work space.
	 * Tabs can be created and subsequently destroyed by clicking on the cross button
	 * 
	 * @param title
	 */
	public void createMapTab (String title, int rowCount, int colCount) {
		title = title.trim();
		if (!mapTabExists(title)) {
			MapTab tab = new MapTab(language, title, rowCount, colCount, (Controllable) WorkSpaceManager.this, (Activable) WorkSpaceManager.this, (InspectorLike) WorkSpaceManager.this);
			tabbedPane.addTab(null, tab);
			mapTabList.add(tab);
			int pos = tabbedPane.indexOfComponent(tab);
			JPanel panelTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			panelTab.setOpaque(false);

			JLabel lblTitle = new JLabel(title);
			JButton close = new WorkSpaceButton(panelTab, tab);
			close.setOpaque(true);
			panelTab.add(lblTitle);
			// create border between title and close button
			lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 5));
			panelTab.add(close);
			panelTab.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			tabbedPane.setTabComponentAt(pos, panelTab);
			tabbedPane.setSelectedComponent(tab);
		} else {
			GAError.displayError("Map Exists Already!");
		}

	}

	
	private boolean mapTabExists(String mapName) {
		for (MapTab tab : mapTabList) {
			if (mapName.equals(tab.getTitle()))
				return true;
		}
		return false;
	}

	public GameObject getActiveTile () {
		return activeTile;
	}

	/**
	 * Removes a particular tab from the TabContainer
	 * 
	 * @param tabNum
	 */
	public void destroyWorkspace (int tabNum) {
		if (tabNum <= tabbedPane.getTabCount()) {
			tabbedPane.remove(tabNum);

		}
	}

	/**
	 * Removes all the tabs from the Tab Container
	 */
	public void clearWorkspaces () {
		tabbedPane.removeAll();
	}

	
	public void nameGame(String gameName) {
		setTitle(BASE_TITLE + " - " + gameName);
		myGameName = gameName;
	}
	
	public void createGameOptions() {
		String query = "Please select Game Options";
		String[] gameOptionsArray = gameOptions.getString("GameOptions").split(GameObjectPanel.DELIMITER);//general, gameoptions
		Map<String, List<String>> typeToValues = new HashMap<String, List<String>>();
		List<String> valuesToGet = new ArrayList<String>(); 
		for (int i = 0; i < gameOptionsArray.length; i++) {
			String option = gameOptionsArray[i];
			String[] gameOptionValues = gameOptions.getString(option).split(GameObjectPanel.DELIMITER);
			List<String> listOfValues = Arrays.asList(gameOptionValues);
			typeToValues.put(option, listOfValues);
			for (String value: listOfValues) {
				String[] valueDefinition = gameOptions.getString(value).split(GameObjectPanel.DELIMITER);
				valuesToGet.addAll(Arrays.asList(valueDefinition));
			}
		}
		try {
			Map<String, Map<String, String>> metaData = myController.getDefinitionsOfType(METADATA);
			if (metaData.isEmpty()) {
				for (int i = 0; i < gameOptionsArray.length; i++) {
					Map<String, String> valuesMap = new HashMap<String, String>();
					String[] defaultsArray = gameOptions.getString(gameOptionsArray[i] + "Defaults").split(GameObjectPanel.DELIMITER);
					for (int j = 0; j < defaultsArray.length; j+= 2) {
						valuesMap.put(defaultsArray[j + 1], defaultsArray[j]);
					}
					metaData.put(gameOptionsArray[i], valuesMap);
				}
			}
			DefineValuePopup defineOptions = new DefineValuePopup(query, valuesToGet, metaData);
			if (defineOptions.isDefined()) {
				Map<String, String> definedValues = defineOptions.getMap();
				for (String option : gameOptionsArray) {
					List<String> valuesNeeded = typeToValues.get(option);
					Map<String, String> mapToSave = new HashMap<String, String>();
					for (String value : valuesNeeded) {
						mapToSave.put(value, definedValues.get(value));
					}
					myController.updateDefinition(METADATA, option, mapToSave);
				}
				if (!defineOptions.get("Game Name").isEmpty()) 
					nameGame(defineOptions.get("Game Name"));
			}
			
		} catch (ValueNotDefinedException e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean checkErrors() {
		Map<String, Map<String, String>> characters = myController.getDefinitionsOfType(CHARACTER);
		System.out.println(characters);
		System.out.println("Player character: " + checkForPlayerCharacter(characters));
		System.out.println("Fightable conditions: " + checkPlayerFightable(characters));
		return checkForPlayerCharacter(characters) && checkPlayerFightable(characters);
	}

	private boolean checkForPlayerCharacter(Map<String, Map<String, String>> characters) {
		for (String c : characters.keySet()) {
			Map<String, String> character = characters.get(c);
			if (character.get(myLanguage.getString("ObjectType")).equals(myLanguage.getString("PC"))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkPlayerFightable(Map<String, Map<String, String>> characters) {
		if (checkFightability(characters)) {
			for (String c : characters.keySet()) {
				Map<String, String> character = characters.get(c);
				if (character.get(myLanguage.getString("ObjectType")).equals(myLanguage.getString("PC")) 
						&& character.containsKey(myLanguage.getString("Fightable")))
					return true;
			}
			return false;
		}
		return true;
	}
	
	private boolean checkFightability(Map<String, Map<String, String>> characters) {
		for (String c : characters.keySet()) {
			Map<String, String> character = characters.get(c);
			if (character.get(myLanguage.getString("ObjectType")).equals(myLanguage.getString("NPC")) 
					&& character.containsKey(myLanguage.getString("Fightable"))) {
				return true;
			}
		}
		return false;
	}
	
	
	protected void initMenu () {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu(myLanguage.getString("File"));
		JMenuItem publishGame =
				makeMenuItem(myLanguage.getString("PublishGame"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						if (checkErrors()) {
							JFileChooser fileChooser = GAUtilMethods.createFileChooser();
							int returnVal = fileChooser.showSaveDialog(null);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								for (MapTab mapTab: mapTabList) {
									mapTab.saveMap();
								}
								myController.publish(fileChooser.getSelectedFile().getPath());
							}
						} else {
							GAError.displayError("Error, cannot save!");
						}
					
					}
				});

		file.add(publishGame);

		JMenu createObject = new JMenu(myLanguage.getString("CreateObject"));
		JMenuItem createMap =
				makeMenuItem(myLanguage.getString("CreateMap"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						createMapTab();
						
					}

				});

		JMenuItem createTile =
				makeMenuItem(myLanguage.getString("CreateTile"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						tileManager.setVisible(true);
					}
				});
		JMenuItem createCharacter =
				makeMenuItem(myLanguage.getString("CreateCharacter"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						characterManager.setVisible(true);
					}
				});
		JMenuItem createEvent =
				makeMenuItem(myLanguage.getString("CreateEvent"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						eventManager.setVisible(true);
					}
				});
		JMenuItem createItem =
				makeMenuItem(myLanguage.getString("CreateItem"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						itemManager.setVisible(true);
					}
				});
		createObject.add(createMap);
		createObject.add(createTile);
		createObject.add(createCharacter);
		createObject.add(createEvent);
		createObject.add(createItem);
		JMenu options = new JMenu(myLanguage.getString("Options"));
		JMenuItem clearMaps =
				makeMenuItem(myLanguage.getString("ClearMaps"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						clearWorkspaces();
					}
				});
		JMenuItem gameOptions =
				makeMenuItem(myLanguage.getString("GameOptions"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						createGameOptions();
					}
					
				});
				makeMenuItem(myLanguage.getString("ClearMaps"), new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						clearWorkspaces();
					}
				});

				options.add(clearMaps);
				options.add(gameOptions);
				JMenu helpMenu = new JMenu(myLanguage.getString("Help"));
				JMenuItem commandHelp = makeMenuItem(myLanguage.getString("Help"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e){
						String page = "instructions.html";
						JEditorPane pane = new HTMLViewer(page);
						final JScrollPane scrollPane = new JScrollPane(pane);
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								scrollPane.getVerticalScrollBar().setValue(0);
							}
						});
						JFrame f = new JFrame("Command Help");
						f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						f.getContentPane().add(scrollPane);
						f.pack();
						f.setSize(1000, 600);
						f.setVisible(true);
					}
				});
				helpMenu.add(commandHelp);
				menuBar.add(file);
				menuBar.add(createObject);
				menuBar.add(options);
				menuBar.add(helpMenu);
				setJMenuBar(menuBar);
	}

	/**
	 * Returns the workspace that is currently selected within the workspace manager
	 */
	public Tab getActiveWorkspace () {

		return (Tab) tabbedPane.getSelectedComponent();
	}

	/**
	 * Sets the active workspace within the view to a particular index
	 * 
	 * @param {int} tabNum
	 */
	public void setActiveWorkspace (int index) {
		if ((index != -1) && (index <= tabbedPane.getTabCount())) {
			tabbedPane.setSelectedIndex(index);
		}
	}

	private class WorkSpaceButton extends JButton implements ActionListener {
		JPanel myTab;
		MapTab myMapTab;
		public WorkSpaceButton (JPanel tab, MapTab mapTab) {
			myTab = tab;
			myMapTab = mapTab;
			int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText(myLanguage.getString("WorkSpaceButton"));
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			addActionListener(this);
		}

		public void actionPerformed (ActionEvent e) {
			int i = WorkSpaceManager.this.tabbedPane.indexOfTabComponent(myTab);
			if (i != -1) {
				mapTabList.remove(myMapTab);
				WorkSpaceManager.this.tabbedPane.remove(i);

			}
		}
		

		// paint the cross
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.BLUE);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();
		}
	}

	private MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered (MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited (MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
	

	@Override
	public void updateDefinition(String type, String id,
			Map<String, String> attributes) {
		myController.updateDefinition(type, id, attributes);
	}



	@Override
	public Map<String, String> getDefinitionData(String id) {
		return myController.getDefinitionData(id);
	}



	@Override
	public Map<String, Map<String, String>> getDefinitionsOfType(String type) {
		return myController.getDefinitionsOfType(type);
	}


	@Override
	public String getNewLongID () {
		return myController.getNewLongID();
	}

	@Override
	public void publish (String s) {
		myController.publish(s);
	}

	@Override
	public String getNewShortID () {
		return myController.getNewShortID();
	}

	/**
	 * Sets a specific tile as the active tile
	 */
	public void setActiveTile (GameObject tile) {
		activeTile = tile;
	}

	/**
	 * Inspects a tile
	 */
	public void inspect(String type, String id) {
		ObjectManager om = managerMap.get(type);
		om.updatePropertiesView(id);
	}
	
	/**
	 * Inspects objects on a tile given the list of object ids on the tile
	 */
	public void inspect(String type, List<String> listOfIDs) {
		ObjectManager om = managerMap.get(type);
		om.updatePropertiesView(listOfIDs);
	}

}
