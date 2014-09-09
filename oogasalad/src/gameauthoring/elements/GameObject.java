package gameauthoring.elements;

import gameauthoring.workspace.Activable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import net.miginfocom.swing.MigLayout;

/**
 * The basic game objects, including tiles, characters and items.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")

public abstract class GameObject extends Element {
	
	private Activable myActivable;
	protected String myType;
	/**
	 * 
	 * @param width
	 * @param height
	 * @param filePath
	 * @param id
	 * @param activable
	 * @param type 
	 */
	public GameObject(final int width, final int height, String filePath, String id, Activable activable, String type) {
		super(width, height, filePath, id);
		setLayout(new MigLayout("insets 0.5"));
		myActivable = activable;
		myType = type;
		addActiveTileListener();
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param activable
	 * @param type 
	 */
	public GameObject(final int width, final int height, Activable activable, String type) {
		super(width, height);
		setLayout(new MigLayout("insets 0.5"));
		myActivable = activable;
		myType = type;
		addActiveTileListener();
	}
	
	protected void addActiveTileListener() {
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				myActivable.setActiveTile(GameObject.this);
			}
	      });
	}
	
	
	
}
