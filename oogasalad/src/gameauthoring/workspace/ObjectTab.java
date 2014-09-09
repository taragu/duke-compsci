package gameauthoring.workspace;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import gameauthoring.elements.GameObject;
import gameauthoring.factories.GameObjectFactory;
import gameauthoring.util.Bundles;
import gameauthoring.util.DefaultCreator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * The specific tab that refers to the different available objects, including
 * tiles, characters, and objects. Includes the gallery of different objects to
 * select and paint on the map. 
 *
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public class ObjectTab extends Tab {
	protected int tileSize;
	protected ObjectManager om;
	protected String myType;
	protected int myObjectCount;
	protected ResourceBundle myProperties;
	public static final String BASE_FILE_PATH = "src/resources/";
	public static final String ERASER_BASE_TYPE = "Eraser";
	public static final String OBJECT = "Object";
	public static final String OBJECT_PACKAGE = "gameauthoring.elements.";
	public static final String RESOURCES_KEY_BASE = "ObjectBitMap";
	public static final String RESOURCES_BITMAP_TILE_SIZE = "ObjectBitMapTileSize";
	public static final String MEDIA_FOLDER = "media/resources/";
	protected Controllable myController;
	protected PropertyUpdatable myUpdateProperty;
	protected Activable myActivable;
	protected int myTileSize;
	/**
	 * Constructor for the object tab. 
	 * 
	 * @param language
	 * @param tileSize
	 * @param controller
	 * @param updateProperty
	 * @param type
	 * @param activable 
	 */
	public ObjectTab(String language, int tileSize, Controllable controller,  Activable activable, PropertyUpdatable updateProperty, String type) {
		super(language);
		myController = controller;
		myUpdateProperty = updateProperty;
		myActivable = activable;
		myType = type;
		myObjectCount = 0;
		myTileSize = tileSize;
		ResourceBundle myPaths = Bundles.getPropertiesDirectory();
		myProperties = Bundles.getBundleOfType(myPaths.getString(myType));
		String bitMapFilePath = myLanguage.getString(type + RESOURCES_KEY_BASE);
		int bitMapTileSize = Integer.parseInt(myLanguage.getString(type + RESOURCES_BITMAP_TILE_SIZE));
		createTiles(bitMapFilePath, tileSize, bitMapTileSize);
	}
	
	/**
	 * Generates the default tiles, from different sprite sheets available. 
	 * @param bitMapFilePath
	 * @param desiredTileSize
	 * @param bitMapTileSize
	 */
	private void createTiles(String bitMapFilePath, int desiredTileSize, int bitMapTileSize) {
	
		MigLayout layout = new MigLayout(
	  	        // set the automatic wrap after columns
	  	        "insets 0, wrap " + 10, 
	  	        // hardcode fixed column width and fixed column gap 
	  	        "[" + desiredTileSize + "lp, fill]10", 
	  	        // hardcode fixed height and a zero row gap
	  	        "[" + desiredTileSize + "lp, fill]10");
		setPanelLayout(layout);
		addUtilTiles(myType, desiredTileSize);
		createObjectTile(bitMapFilePath, desiredTileSize, bitMapTileSize, mainPanel);
		
	}
	
	
	private void createObjectTile(String bitMapFilePath, int desiredTileSize, int bitMapTileSize, JPanel panel) {
		BufferedImage img = null;
        try {
            img = ImageIO.read(new File(bitMapFilePath));
        } catch (IOException e) {

        }
        int height = img.getHeight();
        int width = img.getWidth();
        Map<String, String> defaultMap = getDefaults();
        for (int i = 0; i < width; i+= bitMapTileSize) {
        	for (int j = 0; j < height; j+= bitMapTileSize) {

        		BufferedImage cutImage = img.getSubimage(i, j, bitMapTileSize - 1, bitMapTileSize - 1);
        		String id = DefaultCreator.createDefaultID(myType);
        		String fileOrder = myObjectCount + "";
        		String objectFilePath = BASE_FILE_PATH + myType + "/" + myType + fileOrder + ".png";
        		File outputFile = new File(objectFilePath);
        		if (!outputFile.exists()) {
        			try {
						ImageIO.write(cutImage, "png", outputFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        		GameObject gameObject = GameObjectFactory.createGameObject(myType, desiredTileSize, objectFilePath, id, myActivable);
        		Map<String, String> map = new HashMap<String, String>();
        		map.put(myLanguage.getString("UniqueID"), id);
        		map.put(myLanguage.getString("Name"), id);
        		panel.add(gameObject);
        		map.put(myLanguage.getString("ImagePath"), objectFilePath);
        		map.putAll(defaultMap);
        		myObjectCount++;
        		myController.updateDefinition(myType, id, map);
        	}
        }
	}

	protected void addGameObject(String id) {
		Map<String, String> gameObjectMap = myController.getDefinitionData(id);
		String filePath = gameObjectMap.get(myLanguage.getString("ImagePath"));
		GameObject gameObject = GameObjectFactory.createGameObject(myType, myTileSize, filePath, id, myActivable);
		mainPanel.add(gameObject);
	}
	
	private Map<String, String> getDefaults() {
		Map<String, String> defaults = new HashMap<String, String>();
		StringBuilder abilitySB = new StringBuilder();
		String[] implementedAbilities = myProperties.getString(myType + myLanguage.getString("ImplementedDefaults")).split(GameObjectPanel.DELIMITER);
		for (String ability : implementedAbilities) {
			if (myProperties.containsKey(ability + myLanguage.getString("Defaults"))) {
				defaults.put(ability, myProperties.getString(ability + myLanguage.getString("Defaults")));
				abilitySB.append(ability);
			}
		}
		String typeDefaultsKey = myType + myLanguage.getString("TypeDefaults");
		defaults.put(myLanguage.getString("ObjectType"), myProperties.getString(typeDefaultsKey));
		defaults.put(myLanguage.getString("Abilities"), abilitySB.toString());
		return defaults;
	}

	private void addUtilTiles(String type, int desiredTileSize) {
		GameObject gameObjectEraser = GameObjectFactory.createGameObjectEraser(type, desiredTileSize, myActivable);
		gameObjectEraser.setToolTipText("Erase your " + type + " off the map!");
		mainPanel.add(gameObjectEraser);
		GameObject gameObjectInspector = GameObjectFactory.createGameObjectInspector(type, desiredTileSize, myActivable, myUpdateProperty);
		gameObjectInspector.setToolTipText("Inspect a " + type + "'s properties on the map!");
		mainPanel.add(gameObjectInspector);
	}
	
	/**
	 * Converts an Image object to a buffered image
	 * @param img
	 * @return
	 */
	public BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
}
