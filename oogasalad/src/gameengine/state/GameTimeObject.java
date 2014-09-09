package gameengine.state;

import gameengine.events.GameEventManagerInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that keeps track of the current time (including time of day and season)
 * 
 * @author Zanele and Tara
 *
 */
public class GameTimeObject {
	private double myTime; 
	private List<Double> myTimeObjectives;
	protected GameEventManagerInterface myEventManager;
	
	private Season myCurrentSeason;
	
	private TimeOfDay myCurrentTimeOfDay;
	
	/*
	 * the number of frames between season transitions
	 */
	private double mySeasonGap;
	
	/*
	 * the number of frames between day and night transitions
	 */
	private double myTimeOfDayGap;
	
	/*
	 * keeps track of whether seasons and time of day should be kept track of! 
	 */
	private boolean mySeasonOn;
	private boolean myTimeOfDayOn;
	
	
	
	/**
	 * create a GameTimeObject
	 * @param numberFramePerSeason the number of frames between each season
	 * @param numberFramePerDayNight the number of frames between day and night
	 */
	public GameTimeObject(double numberFramePerSeason, double numberFramePerDayNight,
	                      boolean seasonOn, boolean timeOfDayOn ) {
		myTime = 0.0;
		myTimeObjectives = new ArrayList<Double>();
		mySeasonGap = numberFramePerSeason;
		myTimeOfDayGap = numberFramePerDayNight;
		myCurrentSeason = Season.SPRING;
		myCurrentTimeOfDay = TimeOfDay.DAY;
		mySeasonOn = seasonOn;
		myTimeOfDayOn = timeOfDayOn;
	
	}
	
	
	public void setEventManager(GameEventManagerInterface gameEventManager) {
	    	myEventManager = gameEventManager;
	}
	
	
	public void addTimeBenchMark(double timeObjective) {
		myTimeObjectives.add(timeObjective);
		
	}
	
	
	public void reset() {
		myTime = 0.0;
	}
	
			
	public double getCurrentTime() {
		return myTime;
	}
	    
	
	public void update() {
		myTime = myTime + 1.0;
		checkAndUpdateSeason();
		checkAndUpdateTimeOfDay();
	}
	
	public void update(double updateByAmount) {
		myTime = myTime + updateByAmount;
		checkAndUpdateSeason();
                checkAndUpdateTimeOfDay();
		
	}

	/**
	 * Check the current time and update the current season if necessary
	 */
	public void checkAndUpdateSeason() {
	        if (myTime % mySeasonGap == 0) {
	            myCurrentSeason = myCurrentSeason.next();
	            System.out.println("The current season is now" + myCurrentSeason.getDescription());
	        }
	}
	
	/**
	 * Check the current time and update the current time of day if necessary
	 * 
	 */
	public void checkAndUpdateTimeOfDay() {
	    if (myTime % myTimeOfDayGap == 0) {
                myCurrentTimeOfDay = myCurrentTimeOfDay.next();
	            System.out.println("The current timeOfDay is now" + myCurrentTimeOfDay.getDescription());

                
            }
	}

	/**
	 * return the current season
	 * 
	 * @return
	 */
	public Season getSeason () {
		return myCurrentSeason;
	}


	/**
	 * return the current time of day
	 * @return
	 */
	public TimeOfDay getTimeOfDay () {
		return myCurrentTimeOfDay;
	}


	public boolean getSeasonSetting() {
		return mySeasonOn;
	}


	public boolean getTimeOfDaySetting() {
		return myTimeOfDayOn;
	}

}
