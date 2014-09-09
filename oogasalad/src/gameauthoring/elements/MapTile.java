package gameauthoring.elements;

import gameauthoring.util.Bundles;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.workspace.Activable;
import gameauthoring.workspace.InspectorLike;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


/**
 * Map Tile refers to the empty grid on a created map. Map tiles can contain
 * one tile object, and character/item objects.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class MapTile extends Element {

	protected MapCanvas myMc;
	private Activable myActivable;
	protected List<String> myObjectIDs;
	protected String myMapTileID = "";
	protected String myMapTileType = "";
	protected Image mapImage;
	private String myObjectType = "";
	protected String mapImageFilePath;
	private InspectorLike myInspector;
	private InspectorMenu myInspectorMenu;
	private int myX;
	private int myY;
	private ResourceBundle myLanguage = Bundles.getLanguageBundle();
	
	/**
	 * Constructor for a map tile
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param mc
	 * @param activable
	 * @param inspector
	 */
	public MapTile(int x, int y, final int width, final int height, MapCanvas mc, Activable activable, InspectorLike inspector) {
		super(width, height);
		myActivable = activable;
		myMc = mc;
		myInspector = inspector;
		myX = x;
		myY = y; 
		myObjectIDs = new ArrayList<String>();
		MouseAdapter ma = new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					setTile();
				}
				else if (SwingUtilities.isRightMouseButton(e) && (!myMapTileID.isEmpty() || !myObjectIDs.isEmpty())) {
					myInspectorMenu = new InspectorMenu();
					myInspectorMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			public void mouseDragged(MouseEvent e) {
				MapTile.this.myMc.isDragging = true;
				setTile();
			}
			
			public void mouseReleased(MouseEvent e) {
				MapTile.this.myMc.isDragging = false;
			}
			
			public void mouseEntered(MouseEvent e) {
				if (MapTile.this.myMc.isDragging ) {
					setTile();
				}
			}
	      };
		
	    addMouseListener(ma);
	    addMouseMotionListener(ma);
	}
	
	protected void setTile() {
		GameObject t = myActivable.getActiveTile();
		if (t != null) {
			setTile(t);
		}
	}
	
	protected void setTile(GameObject objectTile) {
		objectTile.setTile(this);
	}
	
	@Override
	protected void setTile(MapTile otherTile) {
		
	}

	protected void removeObjects() {
		imageLabel.setIcon(null);
		myObjectIDs.clear();
		imageFilePath = "";
		myObjectType = "";
	}
	
	protected void setMapImage(Image image, String id, String imageFilePath) {
		mapImage = GAUtilMethods.resizeImage(image, width-1, height-1);
		myMapTileID = id;
		mapImageFilePath = imageFilePath;
		repaint();
	}
	
	protected void removeMapImage() {
		mapImage = null;
		myMapTileID = "";
		mapImageFilePath = "";
		repaint();
	}

	/**
	 * paints the map tile component on the panel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (mapImage != null) {
			g2d.drawImage(mapImage, 1, 1, null);
		}
	}
	
	private class InspectorMenu extends JPopupMenu {
		public InspectorMenu() {
			super();
			if (hasTile()) {
				JMenuItem inspect = new JMenuItem(myLanguage.getString("Inspect") + myLanguage.getString("Tile"));
				inspect.addActionListener(new InspectMenuListener(myLanguage.getString("Tile"), myMapTileID));
				add(inspect);
			}
			if (hasObject()) {
				JMenuItem inspect = new JMenuItem(myLanguage.getString("Inspect") + myObjectType);
				inspect.addActionListener(new InspectMenuListener(myObjectType, myObjectIDs));
				add(inspect);
			}
		}
		
		private class InspectMenuListener implements ActionListener {
			String myListenerType;
			List<String> myListOfIDs = new ArrayList<String>();
			public InspectMenuListener(String type, String id) {
				myListenerType = type;
				myListOfIDs.add(id);
			}
			
			public InspectMenuListener(String type, List<String> listOfIDs) {
				myListenerType = type;
				myListOfIDs = listOfIDs;
			}
	
			@Override
			public void actionPerformed(ActionEvent e) {
				inspect(myListenerType, myListOfIDs);
			}
		}
	
	}
	private void inspect(String type, List<String> id) {
		myInspector.inspect(type,id);

	}
	protected void setObjectType(String type) {
		myObjectType = type;
	}
	
	protected String getObjectType() {
		return myObjectType;
	}

	/**
	 * Returns the idea of the character/item object displayed on the tile
	 * (On the surface)
	 * @return
	 */
	public String getSurfaceID() {
		if (!myObjectIDs.isEmpty()) {
			return myObjectIDs.get(0);
		} 
		return null;
	}
	
	/**
	 * Returns the name of the Map on which the map tile is located.
	 * @return
	 */
	public String getMapName() {
		return myMc.getMapName();
	}
	
	/**
	 * Returns the x value of a map tile.
	 * @return
	 */

	public int getTileX() {
		return myX;
	}
	

	/**
	 * Returns the y value of a map tile.
	 * @return
	 */
	public int getTileY() {
		return myY;
	}
	
	/**
	 * Returns the ID of the map tile
	 * @return
	 */
	public String getMapTileID() {
		return myMapTileID;
	}
	
	/**
	 * Gets the ID of the entire map
	 * @return
	 */
	public String getMapID() {
		return myMc.getMapID();
	}
	
	protected void addObject(Image image, String id, String type) {
		if (type.equals(myObjectType) || !hasObject()) {
			if (!hasObject()) {
				addObject(image);
			}
		} else {
			removeObjects();
			addObject(image);
			
		}
		myObjectIDs.add(id);
		myObjectType = type;
	}
	
	protected boolean hasObject() {
		return !myObjectIDs.isEmpty();
	}
	
	protected boolean hasTile() {
		return !myMapTileID.isEmpty();
	}

}


