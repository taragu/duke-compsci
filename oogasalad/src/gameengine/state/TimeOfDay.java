package gameengine.state;

import mastercode.MediaTableWriter;
import gameengine.gamedata.DataConstants;


/**
 * Enum object for different time of day (day and night)
 * 
 * @author Tara
 *
 */
public enum TimeOfDay implements Precipitate, DataConstants {

    DAY("DAY","DAYmedia") {
        @Override
        public TimeOfDay next() {
            return TimeOfDay.NIGHT;
        }

        @Override
        protected void writeToMedia () {
            MediaTableWriter writer = new MediaTableWriter("DAYmedia", null,
                                                           PRECIPITATION_CID, "media/sun.png", DataConstants.MEDIA_FILEPATH);
            writer.write();
        }
    },
    NIGHT("NIGHT","NIGHTmedia") {
        @Override
        public TimeOfDay next() {
            return TimeOfDay.DAY;
        }

        @Override
        protected void writeToMedia () {
            MediaTableWriter writer = new MediaTableWriter("NIGHTmedia", null,
                                                           PRECIPITATION_CID, "media/moon.png", DataConstants.MEDIA_FILEPATH);
            writer.write();
        }
    };
    
    private String myDescription;
    
    private String myPrecipitationImageName;
  
    TimeOfDay(String description, String imageName) {
    	myDescription = description;
    	myPrecipitationImageName = imageName;
    	
        
        writeToMedia(); 
    }
    
    public abstract TimeOfDay next ();
    
    /*
     * define media for day and night
     */
    protected abstract void writeToMedia ();
    
    @Override
    public void initPrecipitation () {
        new Precipitation(myDescription, myPrecipitationImageName);
    }

	public String getDescription() {
		return myDescription;
	}
    
    
}
