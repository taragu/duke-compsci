package gameengine.state;

import java.io.File;
import java.util.List;
import mastercode.GameDataManager;
import mastercode.GameObjectManager;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameMap;
import gameplayer.PlayerEngine;

/**
 * Concrete overview/world game state, implements GameState.
 * @author Amy/Tim
 *
 */
public class MapState extends GameState{

	private int myXofs = 0;
	private int myYofs = 0;
	
	private GameMap myActiveMap;
	private GameObjectManager myObjectManager;
	private GameDataManager myDataManager;
	private final static String MEDIA_PATH = "media"+File.separator+"media.tbl";
	
	public MapState(PlayerEngine engine, GameObjectManager objectManager, GameDataManager dataManager) {
		super(engine);
		myObjectManager = objectManager;
		myDataManager = dataManager;
	}

	@Override
	/**
	 * DoFrame for the state. Checks collisions, etc. The Engine should call this.
	 */
	public void doFrameGameState() {
		super.doFrameGameState();
		myXofs = (int) myEngine.getPartyXY()[0] * myEngine.pfWidth() / myEngine.viewWidth();
		myYofs = (int) myEngine.getPartyXY()[1] * myEngine.pfHeight() / myEngine.viewHeight();
		myEngine.setViewOffset(myXofs, myYofs, true);
		myEngine.moveObjects();
		myEngine.checkCollision(PARTYOBJECT_CID, OTHEROBJECTS_CID);
		myEngine.checkCollision(PARTYOBJECT_CID, NONPLAYERCHARACTER_CID);
		myEngine.checkCollision(TRANSITION_CID, PARTYOBJECT_CID);
		myEngine.checkBGCollision(NOTWALKABLE_CID, PARTYOBJECT_CID);
	}

	@Override
	/**
	 * paintFrame for the state. Paints the screen. The Engine should call this.
	 */
	public void paintFrameGameState() {
		String[] symbolRep = myActiveMap.toSymbolRepresentation();
		myEngine.setTiles(0,0,symbolRep); 
		List<String> tileSymbols = myActiveMap.getNotWalkableSymbols();
		for (String thisSymbol : tileSymbols) {
		    myEngine.setTileSettings(thisSymbol, DataConstants.NOTWALKABLE_CID, 0); 
		}
	}

	@Override
	public void setGameMap(GameMap map) {
		setMaps(map);
	}
	
	public void setMaps(GameMap newMap) {
		removeMap(myActiveMap);
		setMap(newMap);			
	}

	private void setMap(GameMap map) {
		myActiveMap = map;
		myEngine.setPlayerField(myActiveMap.getUniqueID());
		myDataManager.createObjectsInMap(map);
		myObjectManager.setPlayerCharacterDisplay(myEngine);
		myEngine.setPlayerCharacterDisplay();
		myEngine.defineMedia(MEDIA_PATH);
	}
	
	/**
	 * removes the active map by removing all active objects
	 * @param map
	 */
	private void removeMap(GameMap map) {
		myObjectManager.destroyActiveObjects();
	}
	
	/**
	 * returns the active map
	 * @return
	 */
	public GameMap getActiveMap() {
		return myActiveMap;
	}
	
}
