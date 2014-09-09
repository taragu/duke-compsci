package gameengine.state;

import gameengine.gamedata.DataConstants;
import gameplayer.Main;
import jgame.JGObject;

/**
 * class for precipitation objects for seasons such as raindrops, snowflakes, leaves
 * @author Zanele & Tara
 *
 */
public class Precipitation extends JGObject {

    private static final double initialYPosition =  -100; 

    public Precipitation (String name, String imageName) {
        super(name, true, 0, initialYPosition, DataConstants.PRECIPITATION_CID, imageName);
        this.setSpeed(DataConstants.PRECIPITATION_SPEED_CONSTANT);
    }
    
    @Override 
    public void move(){
        setDir(0,0);
    	y = y + .7;
        if (y > Main.DEFAULT_HEIGHT ) this.destroy();
    }
    
    @Override
    public void hit(JGObject object) {
        //DO NOTHING
    }
    
    
}
