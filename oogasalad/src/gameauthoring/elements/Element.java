package gameauthoring.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The basic elements of the game, including MapTiles, and Game objects (tiles,
 * items, characters)
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public abstract class Element extends ImageContainer { 

	protected String myObjectID;
	private List<Line2D> highlightLines;
	private List<Line2D> borderLines;
	public static final int HIGHLIGHT_STROKE = 3;
	public static final int BORDER_STROKE = 1;
	public static final int IMG_OFFSET = 2;
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public Element(final int width, final int height) {
		super(width, height);
		highlightLines = new ArrayList<Line2D>();
		borderLines = new ArrayList<Line2D>();
		addLines(borderLines, BORDER_STROKE);
		setSize(width, height);
		myObjectID = "";
		MouseAdapter ma = new MouseAdapter(){
	          public void mouseEntered(MouseEvent m){
	        	 addLines(highlightLines, HIGHLIGHT_STROKE);
	        	 repaint();
	          }
	          public void mouseExited(MouseEvent m){
	        	 removeHighLight();
	        	 repaint();
	          }
	          };
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param imageFilePath
	 * @param id
	 */
	public Element(final int width, final int height, String imageFilePath, String id) {
		this(width, height);
		addObject(imageFilePath);
		myObjectID = id;
	}
	

	
	/**
	 * Paints the component into the map.
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke());
		for (Line2D line: borderLines) {
			g2d.draw(line);
		}
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(HIGHLIGHT_STROKE));
		for (Line2D line: highlightLines) {
			g2d.draw(line);
		}
		
	}
	
	private void addLines(List<Line2D> listOfLines, int stroke) {
		Line2D.Double top = new Line2D.Double(0, 0 , width, 0);
		Line2D.Double bottom = new Line2D.Double(0, height - stroke/2.0, width, height - stroke/2.0);
		Line2D.Double left = new Line2D.Double(0, 0, 0, height);
		Line2D.Double right = new Line2D.Double(width - stroke/2.0, 0, width - stroke/2.0, height);
		Line2D[] line2D = {top, bottom, left, right};
		for (int i = 0; i < line2D.length; i++) {
			listOfLines.add(line2D[i]);
		}
		
	}
	
	private void removeHighLight() {
		highlightLines = new ArrayList<Line2D>();
	}
	
	protected abstract void setTile(MapTile otherTile);
	
	
}
