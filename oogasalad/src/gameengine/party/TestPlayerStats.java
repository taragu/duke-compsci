package gameengine.party;


public class TestPlayerStats {

	private PlayerStats lousyStats = new PlayerStats(1, 1, 1, 0, 0, 0, 0, 0, 0, 0); //lousy
	private PlayerStats greatStats = new PlayerStats(10, 10, 10, 10, 10, 20, 0, 0, 0, 0); //great
	private PlayerStats okStats = new PlayerStats(5,5,5, 0, 0, 0, 0, 0, 0, 0); //ok
	private PlayerStats goodStats = new PlayerStats(8,8,8, 0, 0, 0, 0, 0, 0, 0); //good
	private PlayerStats agilityHigh = new PlayerStats(5, 5, 10, 0, 0, 0, 0, 0, 0, 0); //agility high
	private PlayerStats strengthHigh = new PlayerStats(10, 5, 5, 0, 0, 0, 0, 0, 0, 0); //strength high
	private PlayerStats defenceHigh = new PlayerStats(5, 10, 5, 0, 0, 0, 0, 0, 0, 0); //defence high
	private PlayerStats p1 = new PlayerStats(7,6,7, 10, 5, 20, 0, 0, 0, 0); //normal p1
	private PlayerStats p2 = new PlayerStats(6,7,8, 10, 1, 20, 0, 0, 0, 0); //normal p2
	private PlayerStats[] array = {lousyStats, greatStats, okStats, goodStats, agilityHigh, strengthHigh, defenceHigh, p1, p2};	
		
		
    @org.junit.Test
    public void testFight () {
		for (int i = 0; i < array.length; i++) {
//			PlayerStats one = array[i];
			for (int j = 0; j < array.length; j++) {
//				one.fightStats(array[j], 100);
			}
		}
	}
    
    
    @org.junit.Test
    public void testExperienceLevelUp () {

    	p1.experienceLevelUp(p2);
    	System.out.println("P1 Experience Points: " + p1.currentExperiencePoints +" P1 Experience Level: "+ p1.myExperienceLevel);
    	System.out.println("P2 Experience Points:" + p2.currentExperiencePoints + " P2 Experience Level: "+ p2.myExperienceLevel);

    	p1.experienceLevelUp(greatStats);
    	System.out.println("P1 Experience Points: " + p1.currentExperiencePoints +" P1 Experience Level: "+ p1.myExperienceLevel);
    	System.out.println("Opponent Experience Points:" + greatStats.currentExperiencePoints + " Opponent Experience Level: "+ greatStats.myExperienceLevel);
    }
    
    
    @org.junit.Test
    public void testStatsLevelUp(){

    	p2.setLevellingInfo(1, 1, 1, 1);
    	
    	System.out.println("Inital Stats: " + p2.toString());
    	p2.statsLevelUp();
    	System.out.println("Stats after level up: " + p2.toString());
    	
    	
    }
    
    
    
	
}
