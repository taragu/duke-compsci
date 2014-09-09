package gameengine.gamedata;

import mastercode.Translator;
import util.Reflection;

/**
 * Class that parsers game intro informaton such as instructions and game name
 * 
 * @author Tara
 *
 */
public class GameIntroParser implements DataConstants {

    private String myFilePath;
    
    public GameIntroParser(String jsonFilePath) {
        myFilePath = jsonFilePath;
    }
    
    public GameIntro parserIntroInformation() {
        Translator translator = new Translator ();
        Object[] info =  translator.translateSpecifiedItem("General", myFilePath, PROPERTIES_FILEPATH).get(0);
        GameIntro gameIntro = (GameIntro) Reflection.createInstance("gameengine.gamedata.GameIntro", info);
        return gameIntro;
        
    }
    
}
