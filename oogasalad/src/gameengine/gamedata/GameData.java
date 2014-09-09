package gameengine.gamedata;

/**
 * Class that holds the input arguments of ONE GameObject constructor
 * @author Tara
 *
 */
public class GameData {

    private Object[] myArgs;
    
    private double myX;
    
    private double myY;
    
    private String myMapUniqueID;
    
    private String myName;
    
    private String myType;
    
    private String myGFXName;
    
    /**
     * Create a GameData object
     * @param args
     */
    public GameData(Object[] args){
        myArgs = args;
        myName = (String) args[0];
        myType = (String) args[6];
        myX = (Double) args[2];
        myY = (Double) args[3];
        myGFXName = (String) args[5];
        myMapUniqueID = (String) args[10];
        
    }
    
    /**
     * get the name of the game object
     * @return
     */
    public String getName() {
        return myName;
    }
    
    /**
     * Return the type of the game object (example: PlayerCharacter)
     * @return
     */
    public String getType () {
        return myType;
    }
    
    /**
     * return the x position of the game object
     * @return
     */
    public double getXPos() {
        return myX;
    }
    
    /**
     * return the y position of the game object
     * @return
     */
    public double getYPos() {
        return myY;
    }
    
    /**
     * Set the x position of the game object
     * WARNING: ONLY USE THIS WHEN SETTING THE POSITION OF PLAYERCHARACTER
     *  WHEN SWITCHING TO A DIFFERENT MAP
     * @param x
     */
    public void setXPos(double x) {
        myX = x;
    }
    
    /**
     * Set the y position of the game object
     * WARNING: ONLY USE THIS WHEN SETTING THE POSITION OF PLAYERCHARACTER
     *  WHEN SWITCHING TO A DIFFERENT MAP
     * @param y
     */
    public void setYPos(double y) {
        myY = y;
    }

    /**
     * Return the input arguments of the game object constructor
     * @return
     */
    public Object[] getData () {
        return myArgs;
    }

    /**
     * get the unique id of the map the game object is on
     * @return
     */
    public String getMapUniqueID () {
        return myMapUniqueID;
        
    }
    
    /**
     * get the graphic name of the game object in the media table
     * 
     * @return
     */
    public String getGFXName() {
        return myGFXName;
        
    }
    
}
