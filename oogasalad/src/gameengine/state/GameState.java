package gameengine.state;

import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameMap;
import gameplayer.PlayerEngine;
/**
 * Holds all the GameStates and sets game state for the engine
 * @author Amy/Tim
 *
 */
public abstract class GameState implements GameStateInterface,DataConstants {

	protected static PlayerEngine myEngine;
	
	public GameState(PlayerEngine engine) {
		myEngine = engine;
	}
	
	@Override
	public void doFrameGameState() {
		// DO NOTHING
	}

	@Override
	public void paintFrameGameState() {
		
		
	}
	
	public void setGameMap(GameMap map) {

	}
	
}
