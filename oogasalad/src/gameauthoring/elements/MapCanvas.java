package gameauthoring.elements;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import gameauthoring.util.Bundles;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.Controllable;
import gameauthoring.workspace.GameObjectPanel;
import gameauthoring.workspace.InspectorLike;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import net.miginfocom.swing.MigLayout;

/**
 * The canvas on which a map is located
 * @author LeeYuZhou, DanZhang
 *
 */

@SuppressWarnings("serial")
public class MapCanvas extends JPanel {

	protected boolean isDragging = false;
	protected String uniqueid = null;
    private MapTile[][] map;
    private int rows;
    private int cols;
    private Activable myActivable;
    private JPanel mapPanel;
    protected String myName;
    private Controllable myController;
    private InspectorLike myInspector;
    
    private ResourceBundle myLanguage = Bundles.getLanguageBundle();
    /**
     * 
     * @param rows
     * @param cols
     * @param tileSize
     * @param name
     * @param controller
     * @param activable
     * @param inspector
     */
    public MapCanvas (int rows, int cols, int tileSize, String name, Controllable controller, Activable activable, InspectorLike inspector)
    {
        this.rows = rows;
        this.cols = cols;
        myActivable = activable;
        myInspector = inspector;
        map = new MapTile[rows][cols];
        myController = controller;
        myName = name;
        setSize(cols * tileSize, rows * tileSize);
        
        mapPanel = new JPanel();
        JScrollPane panel = new JScrollPane(mapPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   		uniqueid = myController.getNewLongID();
        MigLayout layout = new MigLayout(
                                         // set the automatic wrap after columns
                                         "insets 0, wrap " + cols,
                                         // hardcode fixed column width and fixed column gap
                                         "[" + tileSize + "lp, fill]0",
                                         // hardcode fixed height and a zero row gap
                                         "[" + tileSize + "lp, fill]0");
        mapPanel.setLayout(layout);
        mapPanel.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        add(panel);

        createTiles(tileSize);
    }

    private void createTiles(int size) {
    	for (int i = 0; i < rows; i ++) {
        	  for (int j = 0; j < cols; j++) {
        		  MapTile tile = new MapTile(j, i, size,size, MapCanvas.this, myActivable, myInspector);
        		  mapPanel.add(tile);
        		  map[i][j] = tile;
        	  }
          }
    }
    
    /**
     * Saves the map to the back end.
     */
    public void saveMap(boolean premium, String code) {
    	StringBuilder tileMapString = new StringBuilder();
    	StringBuilder charItemMapString = new StringBuilder();
    	Map<String, String> mapOfValues = new HashMap<String, String>();
    	
    	
    	mapOfValues.put(myLanguage.getString("Name"), myName);
    	mapOfValues.put(myLanguage.getString("UniqueID"), uniqueid);
    	for (int i = 0; i < map.length; i++) {
    		for (int j = 0; j < map[0].length; j++) {
    			MapTile t = map[i][j];
    			String tileID = t.myMapTileID;
    			String objectIDs = GAUtilMethods.joinString(t.myObjectIDs, GameObjectPanel.DELIMITER);
    			//save map tile 
    			addInfo(tileID, tileMapString);
    			// save object on tile 
    			addInfo(objectIDs, charItemMapString);
    		}
    		tileMapString.append("\n");
    		charItemMapString.append("\n");
    	}
    	mapOfValues.put(myLanguage.getString("TileMap"), tileMapString.toString());
    	mapOfValues.put(myLanguage.getString("CharacterItemMap"), charItemMapString.toString());
    	mapOfValues.put(myLanguage.getString("Subscription"), Boolean.toString(premium));
    	mapOfValues.put("code", code);
    	myController.updateDefinition(myLanguage.getString("Map"), uniqueid, mapOfValues);
    }
    
    

    private void addInfo(String id, StringBuilder stringBuilder) {
    	if (id.isEmpty()) {
			stringBuilder.append(myLanguage.getString("Empty"));
		} else {
			stringBuilder.append(id);
		}
		stringBuilder.append(" ");
    }

    /**
     * Returns the name of the whole map.
     * @return
     */
	public String getMapName() {
		return myName;
	}
	
	/**
	 * Returns the map ID of the entire map.
	 * @return
	 */
	public String getMapID() {
		return uniqueid;
	}
    
}
