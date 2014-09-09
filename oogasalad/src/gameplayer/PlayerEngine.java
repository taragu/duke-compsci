package gameplayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import mastercode.GameDataManager;
import mastercode.GameObjectDataParser;
import mastercode.GameObjectManager;
import mastercode.GameObjectManagerInterface;
import mastercode.Translator;
import gameengine.controller.HUDController;
import util.Reflection;
import gameengine.InventoryItem;
import gameengine.NonPlayerCharacter;
import gameengine.events.GameEventManager;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameIntro;
import gameengine.gamedata.GameIntroParser;
import gameengine.gamedata.GameMap;
import gameengine.gamedata.MapsAndTilesManager;
import gameengine.gamedata.PlayerCharacterData;
import gameengine.gamedata.QuestData;
import gameengine.party.PartyObject;
import gameengine.state.BattleState;
import gameengine.state.GameState;
import gameengine.state.GameTimeObject;
import gameengine.state.MapState;
import gameengine.state.TimeDecorator;
import jgame.platform.JGEngine;

/**
 * The JGEngine for the model (back end)
 * @author Amy/Tara/Ashley/Tim
 *
 */
@SuppressWarnings("serial")
public class PlayerEngine extends JGEngine implements DataConstants {


	private static final String MAP_STATE = "MAP";
	private Map<String, GameState> myStateMap;
	private GameState myCurrentState;
	private GameState myPreviousState;
	private GameEventManager myGameEventManager;

	private GameObjectDataParser myParser;
	private GameEventManager myEventManager;
	private GameIntroParser myGameIntroParser;

	private MapsAndTilesManager myMapsAndTilesManager;
	private GameObjectManager myGameObjectManager;


	private GameDataManager myDataManager;
	public static final int DEFAULT_TILE_SIZE = 39;
	public static final int NUMBER_OF_TILES = 17;
	@SuppressWarnings("unused")
        private int myWidth;
	@SuppressWarnings("unused")
        private int myHeight;
	private HUDController myController;	
	private PartyObject myParty;	
	private TimeDecorator myTimeDecorator;
	private GameTimeObject myTime;

	@SuppressWarnings("unused")
        private final static String MEDIA_PATH = "media"+File.separator+"media.tbl";
	
	/*
	 * instructions of the game that will be displayed on the splash screen
	 */
	private String myInstructions;

	/*
	 * the graphics name is the battle state background in the media table
	 */
	private String myBattleBackgroundGFX;

	/**
	 * file path of the properties file (ExpectingKeys.properties)
	 */
	private final String PROPERTIES_FILEPATH = "src/gameengine/gamedata/ExpectingKeys.properties";

	/**
	 * Keeps track of the list of GameObjects in the game, including Characters, tiles, items, etc
	 */


	public PlayerEngine (int width, int height, HUDController controller) {
		myWidth = width;
		myHeight = height;
		myController = controller;
		initEngineComponent(width, height);
		myStateMap = new HashMap<String, GameState>();

		//                initializeGameTime();
		//                myTime = new GameTimeObject(2000.0, 500.0, true, true);
		stop();
	}

	/**
	 * create game time object throught the Translator class
	 */
	private void initializeGameTime (String jsonFilePath) {
		Translator translator = new Translator();
		Object[] timeArgs = translator.translateSpecifiedItem("GameTimeObject", 
				jsonFilePath, PROPERTIES_FILEPATH).get(0);
		for (Object o : timeArgs) {
			System.out.println("*****PlayerEngine: printing timeargs: "+o.toString()+", type is "+o.getClass().getName());
		}
		myTime = (GameTimeObject) Reflection.createInstance("gameengine.state.GameTimeObject", timeArgs);
	}

	public void setEventManager(GameEventManager newEventManager) {
		myGameEventManager = newEventManager;
	}

	public void initEngine(String filePath) {
	    
		initializeGameTime(filePath);

		myGameIntroParser = new GameIntroParser(filePath);
		GameIntro introInfo = myGameIntroParser.parserIntroInformation();
		myInstructions = introInfo.getInstruction();
		myBattleBackgroundGFX = introInfo.getBattleBackgroundGFX();

		myMapsAndTilesManager = new MapsAndTilesManager(filePath);
		myMapsAndTilesManager.createMaps();

		myEventManager = new GameEventManager(this);
		this.setEventManager(myEventManager);

		myParser = new GameObjectDataParser(filePath, myEventManager);
		myDataManager = myParser.createData();
		myGameObjectManager = myDataManager.getGameObjectManager();
		
		
	}

	@Override
	public void initCanvas () {
		setCanvasSettings(NUMBER_OF_TILES, NUMBER_OF_TILES, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, null, null, null);
	}

	@Override
	public void initGame () {
		setFrameRate(60, 2);
		removeGameState("Game");
		setGameState("Game");
	}

	//* Title Screen *//
	public void startTitle() {
		removeObjects(null,0);
	}

	public void paintFrameTitle() {
		pressSpace();
	}

	public void doFrameTitle() {
		spaceSetState("Game");
	}

	public void pressSpace() {
		drawString("***~PRESS SPACE TO START~***",pfWidth()/2,(pfHeight()/2)-50,0);
		drawString(myInstructions,pfWidth()/2,pfHeight()/2,0);
	}

	public void startGameOver() {
		removeObjects(null,0);
	}

	public void doFrameGameOver() {
		spaceSetState("Title");
	}

	public void paintFrameGameOver() {
		drawString("***GAME OVER***",pfWidth()/2,(pfHeight()/2)-50,0);
		drawString(myInstructions,pfWidth()/2,pfHeight()/2,0);
	}

	public void spaceSetState(String state) {
		if(getKey(' ')) {
			clearKey(' ');
			removeGameState(state);
			setGameState(state);
		}
	}

	
	public void initStates(GameMap firstMap) {
		MapState mapState = new MapState(this, myGameObjectManager, myDataManager);
		mapState.setMaps(firstMap);
		myStateMap.put(MAP_STATE, mapState);
		System.out.println("^^^^PlayerEngine: initState: mapstate = " +mapState);
	}


	public void setBattleState(Queue<NonPlayerCharacter> enemy) {
		myPreviousState = myCurrentState;
		myCurrentState = new BattleState(this, myParty, enemy, myBattleBackgroundGFX); 
	}

	public void startGame() {		

		myParty = myParser.createPartyObject();
		myParty.setPlayerEngine(this);
		if (myParty.getPlayerStats() != null){
			myController.updateActionPanelStats(myParty);
		}
		myController.updateActionPanelParty(myParty);

		String firstMapID = myParty.getActivePlayer().getMapUniqueID();
		GameMap map = myMapsAndTilesManager.getMapByUniqueID(firstMapID);
		
		initStates(map);
		myCurrentState = myStateMap.get(MAP_STATE);

		myTimeDecorator = new TimeDecorator(myCurrentState, myTime);
	}

	public void doFrameGame () {
	    
		myTime.update();
		if (myCurrentState instanceof BattleState) {
			myCurrentState.doFrameGameState();
		} else {
			myTimeDecorator.doFrameGameState();
		}
		myGameEventManager.runAllEvents();

		dbgShowFullStackTrace(true);
		dbgShowMessagesInPf(false);
	}

	public void paintFrameGame () {
		if (myCurrentState instanceof BattleState) {
			myCurrentState.paintFrameGameState();
		} else {
			myTimeDecorator.paintFrameGameState();
		}
	}

	// Sets the player field for a MapState
	public void setPlayerField(String uniqueID) {
		int[] dimensions = myMapsAndTilesManager.getMapDimensions(uniqueID);
		setPFSize(dimensions[0],dimensions[1]+2);
	}


	/**
	 * set JGEngine in player character
	 */
	public void setPlayerCharacterDisplay () {
		GameObjectManagerInterface manager = new GameObjectManager(null);

		manager.setPlayerCharacterDisplay(this);
	}

	public GameMap getMaps(String uniqueID) {
		return myMapsAndTilesManager.getMapByUniqueID(uniqueID);
	}


	public HUDController getController(){
		return myController;
	}

	public void setGameStateMap(String mapID) {
		myCurrentState.setGameMap(this.getMaps(mapID));
	}

	public void moveParty(int[] newXY) {
		myParty.setPos((double) newXY[0], (double) newXY[1]);
	}

	public double[] getPartyXY() {
		double[] coordinate = {myParty.x, myParty.y};
		return coordinate;
	}

	public void addPartyQuest(QuestData quest) {

		myParty.addQuest(quest);
	}

	public boolean checkQuestCompletionandClear(String questType, String goalID) {
		boolean completed = myParty.checkQuestCompletion(questType, goalID); //Checks for quest completion and clears if completed
		this.getController().updateActionPanelQuests(myParty.getQuestDescriptions());
		return completed;
	}

	public void updateQuestStatus() {
		this.getController().updateActionPanelQuests(myParty.getQuestDescriptions());
	}



	public void addToInventory(InventoryItem item) {
		myParty.addToInventory(item);
		
		this.getController().updateActionPanelStats(myParty);
		this.getController().updateActionPanelInventory(myParty.getItems());
		this.getController().updateActionPanelParty(myParty);
	}

	public void boostStats(String stat, int increment) {
		myParty.incrementStat(stat, increment);
	}

	public void returnToPreviousState() {
		myCurrentState = myPreviousState;
		if(myCurrentState instanceof MapState) {
			MapState state = (MapState) myCurrentState;
			state.setMaps(state.getActiveMap());
		}

	}

	public PlayerCharacterData getActivePlayer() {
		return myParty.getActivePlayer();
	}

	public GameEventManager getEventManager() {
		return myEventManager;
	}

	public GameObjectManager getMyGameObjectManager() {
		return myGameObjectManager;
	}

	public GameDataManager getMyDataManager() {
		return myDataManager;
	}

	public PartyObject getParty(){
		return myParty;
	}
}

