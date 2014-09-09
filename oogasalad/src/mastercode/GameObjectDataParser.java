package mastercode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import gameengine.events.GameEventManagerInterface;
import gameengine.gamedata.DataConstants;
import gameengine.gamedata.GameData;
import gameengine.gamedata.PlayerCharacterData;
import gameengine.party.PartyObject;

/**
 * Class that takes in a json file and return a list or arguments that 
 * are saved for future GameObject creation
 * @author Tara
 *
 */
public class GameObjectDataParser implements DataConstants{
    
    private String myFilePath;
    
    private GameEventManagerInterface myEventManager;
    
    /**
     * holds the list of all player characters in the game in the data structure
     *  PlayerCharacterData
     */
    private List<PlayerCharacterData> myPlayerCharacterDataList;
    
    
    /**
     * Create a GameObjectDataParser object
     * @param jsonFilePath
     * @param eventManager 
     */
    public GameObjectDataParser(String jsonFilePath, GameEventManagerInterface eventManager) {
        myFilePath = jsonFilePath;
        myEventManager = eventManager;
        myPlayerCharacterDataList = new ArrayList<PlayerCharacterData>();
    }

    
    /**
     * Create a PartyObject consists of PlayerCharacterData;
     * WARNING: DO NOT CALL THIS METHOD BEFORE YOU CALL CREATEDATA()!!
     * 
     * @return
     */
    public PartyObject createPartyObject() {
        return new PartyObject(myPlayerCharacterDataList, myEventManager);
    }
    
    /**
     * Create data of GameObjects and save them in GameDataManager for 
     * future GameObject creation
     * @return
     */
    public GameDataManager createData() {
        Translator translator = new Translator();
        List<Object[]> listOfArgs = translator.translateSpecifiedItem("Objects",myFilePath, PROPERTIES_FILEPATH);
        
        GameDataManager myGameDataManager = new GameDataManager(processToGameDataAndPlayerStats(listOfArgs),
                                                                myEventManager);
        return myGameDataManager;
    }
    
    /*
     * convert list of args into type GameData
     * also saves player stats to myPlayerCharacterDataList
     */
    private List<GameData> processToGameDataAndPlayerStats (List<Object[]> listOfArgs) {
        
        List<GameData> data = new ArrayList<GameData>();
        for (Object[] thisArgs: listOfArgs) {
            System.out.println("GameObjectDataParser: length of thisArgs is "+thisArgs.length);
            if (thisArgs.length == GAMEOBJECT_LENGTH) { // ENSURES THAT WE ONLY ADD GAME OBJECTS TO THE LIST
                GameData thisData = new GameData(thisArgs);
                data.add(thisData);
                System.out.println("GameObjectDataParser: thisArgs at index type is "+
                                                                (String) thisArgs[INDEX_TYPE]);
                if (((String) thisArgs[INDEX_TYPE]).equals(PLAYERCHARACTER)) {
                    System.out.println("GameObjectDataParser: playercharacter found!!");
                    @SuppressWarnings("unchecked")
                    Map<String, List<Object>> propertyInputMap = (Map<String, List<Object>>) 
                                                        thisArgs[INDEX_PROPERTYINPUT];
                    System.out.println("GameObjectDataParser: " +
                            "propertyinput map for player character is "+propertyInputMap);
                    List<Object> fightableInput = propertyInputMap.get(FIGHTABLE);
                    PlayerCharacterData playerCharacterData = new PlayerCharacterData(thisData);
                    
                    if (!fightableInput.isEmpty()) {
                        List<Object> stats = fightableInput;
                        System.out.println("GameObjectDataParser: stats = "+stats);
                        if (!stats.isEmpty()) {
                            playerCharacterData.setPlayerStats(stats);
                        }
                        
                    }
                    myPlayerCharacterDataList.add(playerCharacterData);
                } 
            }
        }
        return data;
    }
}
