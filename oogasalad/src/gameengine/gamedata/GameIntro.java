package gameengine.gamedata;

/**
 * Class that stores information of introduction information of the game for the splash screen
 * 
 * @author Tara
 *
 */
public class GameIntro {

    private String myGameName;
    
    private String myInstructions;
    
    private String myBattleBackgroundGFX;
    
    public GameIntro(String gameName, String instructions, String battleBackgroundGFX) {
        myGameName = gameName;
        myInstructions = instructions;
        myBattleBackgroundGFX = battleBackgroundGFX;
        System.out.println("^^^^^^GameIntro: battle background is "+myBattleBackgroundGFX);
    }
    
    /**
     * return the graphics name (in the media table) of the battle state background
     * 
     * @return
     */
    public String getBattleBackgroundGFX() {
        return myBattleBackgroundGFX;
    }
    
    public String getInstruction() {
        return myInstructions;
    }
    
    public String getGameName() {
        return myGameName;
    }
    
}
