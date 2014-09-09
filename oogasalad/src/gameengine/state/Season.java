package gameengine.state;

import mastercode.MediaTableWriter;
import gameengine.gamedata.DataConstants;

/**
 * Enumerated type to allow for season change, used by TimeDecorator to decorate 
 * @author Zanele Munyikwa & Tara Gu 
 *
 */

public enum Season implements Precipitate,DataConstants {
    
    
    
    SPRING("SPRING", "SPRINGmedia") {

        @Override
        public Season next(){
            return SUMMER;
        }

        @Override
        protected void writeToMedia () {
        	//System.out.println("SEASON IMAGE IS BEING WRITTEN TO THE MEDIA TABLE AT THIS PATH_TO_SAVE = " + PATH_TO_SAVE);
            MediaTableWriter writer = new MediaTableWriter("SPRINGmedia", null,
                                                           PRECIPITATION_CID, "media/rain.png", DataConstants.MEDIA_FILEPATH);
            writer.write();
        }
    },
    SUMMER("SUMMER", null) {
        @Override
        public Season next(){
            return FALL;
        }

        @Override
        public void initPrecipitation () {
            // do nothing
        }

        @Override
        protected void writeToMedia () {
            //do nothing
        }
    },
    WINTER("WINTER", "WINTERmedia"){
        @Override
        public Season next(){
            return SPRING;
        }

        @Override
        protected void writeToMedia () {
        	//System.out.println("SEASON IMAGE IS BEING WRITTEN TO THE MEDIA TABLE AT THIS PATH_TO_SAVE = " + PATH_TO_SAVE);

            MediaTableWriter writer = new MediaTableWriter("WINTERmedia", null,
                                                           PRECIPITATION_CID, "media/snow.png", DataConstants.MEDIA_FILEPATH);
            writer.write();
        }
        
    },
    FALL("FALL", "FALLmedia"){
        @Override
        public Season next(){
            return WINTER;
        }

        @Override
        protected void writeToMedia () {
        	//System.out.println("SEASON IMAGE IS BEING WRITTEN TO THE MEDIA TABLE AT THIS PATH_TO_SAVE = " + PATH_TO_SAVE);

            MediaTableWriter writer = new MediaTableWriter("FALLmedia", null,
                                                           PRECIPITATION_CID, "media/fall.png", DataConstants.MEDIA_FILEPATH);
            writer.write();
        }
    };

    private String myDescription;
    private String myPrecipitationImageName;
    
   
    /**
     * Create a Season enum
     * @param description SPRING, SUMMER, FALL, OR WINTER
     */
	Season(String description, String precipitationImageName) {
		myDescription = description;
		myPrecipitationImageName = precipitationImageName;
		writeToMedia(); 
	}
	
	/*
	 * define media for seasons 
	 */
	protected abstract void writeToMedia ();

        /**
	 * Update the current season to the next season
	 * @return
	 */
	public abstract Season next ();

	@Override
        public void initPrecipitation () {
        	    new Precipitation(myDescription, myPrecipitationImageName);
        }
	
        public String getDescription() {
            return myDescription;
        }
}
