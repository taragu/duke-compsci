package gameengine.gamedata;

import java.io.File;

/**
 * Interface that holds all the property names
 * 
 * @author Tara
 * 
 */

public interface DataConstants {

    /**
     * file path of the properties file (ExpectingKeys.properties)
     */
    public final String PROPERTIES_FILEPATH = "src"+File.separator+"gameengine"+File.separator+"gamedata"+File.separator+"ExpectingKeys.properties";
    public final String MEDIA_FILEPATH = "src"+File.separator+"gameplayer"+File.separator+"media"+File.separator+"media.tbl";
    
    /* Game objects */

    public static final String CHARACTER = "Character";
    public static final String PLAYERCHARACTER = "PlayerCharacter";
    public static final String NONPLAYERCHARACTER = "NonPlayerCharacter";
    
    public static final String SINGLEUSECOMBAT = "SingleUseCombat";
    public static final String STATSBOOSTER = "StatsBoost";
    
    public static final String TRANSITIONOBJECT = "TransitionObject";
    

    /* Game object interface names */

    public static final String CHARACTERLIKE = "CharacterLike";
    public static final String ITEMLIKE = "ItemLike";
    public static final String DOORLIKE = "DoorLike";
    

    /* Property interface names */
    
    public static final String TALKABLE = "Talkable";
    public static final String FIGHTABLE = "Fightable";
    public static final String QUESTABLE = "Questable";
    
    public static final String PICKUPABLE = "Pickupable";
    public static final String EQUIPABLE = "Equipable";
    
    public static final String TRANSITIONABLE = "Transitionable";

    /* Property implementation names */
    
    public static final String TALK = "Talk";
    public static final String CANNOTTALK = "CannotTalk";
    
    public static final String FIGHT = "Fight";
    public static final String CANNOTFIGHT = "CannotFight";
    
    public static final String QUEST = "Quest";
    public static final String CANNOTQUEST = "CannotQuest";
    
    public static final String PICKUP = "Pickup";
    public static final String CANNOTPICKUP = "CannotPickup";
    
    public static final String EQUIP = "Equip";
    public static final String CANNOTEQUIP = "CannotEquip";
    
    public static final String TRANSITION = "Transition";
    public static final String CANNOTTRANSITION = "CannotTransition";
    
    /* QuestData types */
    
    public static final String ITEM_QUEST = "Item Quest";
    public static final String BATTLE_QUEST = "Battle Quest";
    public static final String TALK_QUEST = "Talk Quest";
    
    /* Input types */

    public static final String LIST_OF_STRING = "List of strings";
    public static final String LIST_OF_INT = "List of integers";

    /* Keys in the GameObject.properties file */

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String UNIQUEID = "uniqueid";
    public static final String ISUNIQUE = "isunique";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String IMAGEPATH = "imagepath";
    public static final String ABILITIES = "abilities";
    public static final String IMPLEMENTATION = "implementation";
    public static final String COLLISIONID = "collisionid";

    /* Keys to look for in the json file for key "maps" */
    
    public static final String MAP = "map";
    public static final String WALKABLE = "Walkable";
    
    /* Keys to look for in the json file for key "tiles" */


    /* Tile collision IDs */
    public static final int WALKABLE_CID = 1;
    public static final int NOTWALKABLE_CID = 2;
    
    
    /* GameObject collision id */
    public static final int PARTYOBJECT_CID = 4;
    public static final int NONPLAYERCHARACTER_CID = 8;
    public static final int OTHEROBJECTS_CID = 16;
    public static final int TRANSITION_CID = 32;
    
    /* Party Constants */
    public final double SPEED_CONSTANT = 1.5;
    public final int DIRECTION_CONSTANT = 1; 
    
    /* Constants for precipitation */
    public final int PRECIPITATION_CID = 64;
    public final int PRECIPITATION_SPEED_CONSTANT = 1;

    /* Translator class constants*/
    public final String ASSIGN_CID = "AssignCID";
    public final String TOMEDIATABLE = "ToMediaTable";
    public final String ABILITYPROCESSING = "AbilityProcessing";
    public final String TILESIZEPROCESSING = "TileSizeProcessing";
    public final String PROPERTYINPUTMAP = "propertyInputMap";
    public final String PROPERTYIMPLEMENTATIONMAP = "propertyImplementationMap";
    public final String FIXED_VALUE = "FixedVal";
    public final int INDEX_TYPE = 6;
    public final int INDEX_PROPERTYINPUT = 8;
    public final int GAMEOBJECT_LENGTH = 11;
    
    public final int BATTLE_BACKGROUND_CID = 30;
    
    public final String BATTLE_GFX = "Battlemedia";

}
