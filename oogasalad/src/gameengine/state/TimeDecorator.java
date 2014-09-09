package gameengine.state;


/**
 * Implementation of StateDecorator, decorates the state to allow for different seasone and time of day
 * 
 * @author Zanele Munyikwa & Tara Gu
 *
 */

public class TimeDecorator extends StateDecorator {
	

	private GameStateInterface myGameState;
	
	private GameTimeObject myCurrentTime;
	
	private Season myCurrentSeason;
	
	private TimeOfDay myCurrentTimeOfDay;
	
	private boolean mySeasonOn;
	
	private boolean myTimeOfDayOn;
	
	private boolean firstSeason;
	
	private boolean firstTimeOfDay;
	
	
	public TimeDecorator(GameStateInterface theGameState, GameTimeObject currentTime) {
		myGameState = theGameState;
		myCurrentTime = currentTime;
		mySeasonOn = currentTime.getSeasonSetting();
		myCurrentSeason = myCurrentTime.getSeason();
		myCurrentTimeOfDay = myCurrentTime.getTimeOfDay();
		myTimeOfDayOn = currentTime.getTimeOfDaySetting();
		firstSeason=true;
		firstTimeOfDay=true;
		
	}

	
	public void updateEnclosedGameState(GameState currentGameState) {
		myGameState = currentGameState;
	}
	
	
	@Override
	public void doFrameGameState() {
		if ((mySeasonOn && !myCurrentSeason.equals(myCurrentTime.getSeason())) ||
		        (mySeasonOn && firstSeason)) {
			myCurrentSeason = myCurrentTime.getSeason();
			myCurrentSeason.initPrecipitation();
			firstSeason=false;
		}
		if ((myTimeOfDayOn && !myCurrentTimeOfDay.equals(myCurrentTime.getTimeOfDay())) ||
		        (myTimeOfDayOn && firstTimeOfDay)) {
		        myCurrentTimeOfDay = myCurrentTime.getTimeOfDay();
		        myCurrentTimeOfDay.initPrecipitation();
		        firstTimeOfDay=false;
                }
		
		myGameState.doFrameGameState();
	}

	@Override
	public void paintFrameGameState() {
		myGameState.paintFrameGameState();
	}
	
}