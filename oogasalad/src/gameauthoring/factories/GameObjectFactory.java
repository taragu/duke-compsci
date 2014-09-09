package gameauthoring.factories;
import java.util.ResourceBundle;


import gameauthoring.elements.CharItemEraser;
import gameauthoring.elements.CharItemInspector;
import gameauthoring.elements.CharItemObject;
import gameauthoring.elements.GameObject;
import gameauthoring.elements.TileEraser;
import gameauthoring.elements.TileInspector;
import gameauthoring.elements.TileObject;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.PropertyUpdatable;

/**
 * A factory that generates desired game objects.
 * @author LeeYuZhou, DanZhang
 *
 */
public class GameObjectFactory {
	
	public static final String EVENT = "Event";
	public static final String TILE = "Tile";
	public static final String CHARACTER = "Character";
	public static final String ITEM = "Item";
	public static final String ERASER = "Eraser";
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	/**
	 * Creates the game objects
	 * @param type
	 * @param size
	 * @param filePath
	 * @param id
	 * @param activable
	 * @return
	 */
	public static GameObject createGameObject(String type, int size, String filePath, String id, Activable activable) { 
		GameObject gameObject = null; 
		switch (type) {
			case TILE:
				gameObject = new TileObject(size, size, filePath, id, activable, type);
				break;
			case CHARACTER:
				gameObject = new CharItemObject(size, size, filePath, id, activable, type);
				break;
			case ITEM: 
				gameObject = new CharItemObject(size, size, filePath, id, activable, type);
				break;
		}
			
		return gameObject;
	}
	
	/**
	 * Creates the specific game erasers
	 * @param type
	 * @param size
	 * @param activable
	 * @return
	 */
	public static GameObject createGameObjectEraser(String type, int size, Activable activable) { 
		GameObject gameObject = null; 
		ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "PropertiesDirectory");
		switch (type) {
			case ITEM:
				gameObject = new CharItemEraser(size, size, activable, type);
				break;
			case CHARACTER:
				gameObject = new CharItemEraser(size, size, activable, type);
				break;
			case TILE: 
				gameObject = new TileEraser(size, size, activable, type);
				break;
		}
		gameObject.addObject(rb.getString("EraserImage"));
		return gameObject;
	}
	
	/**
	 * Creates the proper inspectors
	 * @param type
	 * @param size
	 * @param activable
	 * @param updateProperty
	 * @return
	 */
	public static GameObject createGameObjectInspector(String type, int size, Activable activable, PropertyUpdatable updateProperty) {
		GameObject gameObject = null; 
		ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "PropertiesDirectory");
		switch (type) {
			case ITEM:
				gameObject = new CharItemInspector(size, size, activable, updateProperty, type);
				break;
			case CHARACTER:
				gameObject = new CharItemInspector(size, size, activable, updateProperty, type);
				break;
			case TILE: 
				gameObject = new TileInspector(size, size, activable, updateProperty, type);
				break;
		}
		gameObject.addObject(rb.getString("InspectorImage"));
			
		return gameObject;
	}
		
	
}
